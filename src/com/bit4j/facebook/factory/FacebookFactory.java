package com.bit4j.facebook.factory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.NameValuePair;

import com.bit4j.facebook.Client;
import com.bit4j.facebook.Facebook;
import com.bit4j.facebook.OAuthAccessToken;
import com.bit4j.facebook.enums.Display;
import com.bit4j.facebook.enums.HttpClientType;
import com.bit4j.facebook.enums.Permission;
import com.bit4j.facebook.exception.FacebookException;
import com.bit4j.facebook.http.APICallerFactory;
import com.bit4j.facebook.http.APICallerInterface;
import com.bit4j.facebook.util.Constants;

/**
 * You can create a singleton instance of this class. This class is thread safe and you can have 
 * one instance for every unique client id and client secret
 * @author Nischal Shetty
 */
public class FacebookFactory extends OAuthFactory implements Serializable {

	private static final long serialVersionUID = 8997415594842693532L;
	
	private Client client;
	private HttpClientType httpClientType;
	private APICallerInterface caller; 
	
	Logger logger = Logger.getLogger(Facebook.class.getName());
	
	public FacebookFactory(String clientId, String clientSecret){
		this(new Client(clientId, clientSecret), HttpClientType.APACHE_HTTP_CLIENT);
	}
	
	public FacebookFactory(Client client){
		this(client,HttpClientType.APACHE_HTTP_CLIENT);
	}
	
	public FacebookFactory(String clientId, String clientSecret, HttpClientType clientType){
		this(new Client(clientId, clientSecret),clientType);
	}
	
	public FacebookFactory(Client client, HttpClientType clientType){
		this.client = client;
		this.httpClientType = clientType;
		caller = APICallerFactory.getAPICallerInstance(clientType);
	}
	
	public HttpClientType getHttpClientType() {
		return httpClientType;
	}

	/**
	 * Returns a new instance of Facebook pertaining to the authenticated user 
	 * @param accessToken
	 * @return Facebook instance 
	 */
	public Facebook getInstance(OAuthAccessToken accessToken){
		return new Facebook(accessToken,httpClientType);
	}

	
	/**
	 * This will return the redirect URL. You need to redirect the user to this URL.
	 * @return The URL to redirect the user to. If the callbackURL is null then the default URL as set is obtained
	 */
	public String getRedirectURL(String callbackURL){
		
		return getRedirectURL(callbackURL, null, new Permission[0]);
		
	}
	
	public String getRedirectURL(String callbackURL, Display display, Permission... permission){
		
		String redirectURL = null;
		
		try {
			redirectURL = Constants.AUTHORIZE_URL+"?"+Constants.PARAM_CLIENT_ID+"="+client.getClientId() 
											+ "&"+Constants.PARAM_REDIRECT_URI+"="+URLEncoder.encode(callbackURL,"UTF-8");
			
			if(permission!=null && permission.length>0){
				StringBuilder permissions = null;
				for(Permission tempPermission: permission){
					if(permissions!=null){
						permissions.append(","+tempPermission.toString());
					} else {
						permissions = new StringBuilder(tempPermission.toString());	
					}
				}
				redirectURL += "&"+Constants.PARAM_PERMISSION + "=" + permissions.toString().toLowerCase();
			}
			
			if(display!=null){
				redirectURL += "&"+Constants.PARAM_DISPLAY + "=" + display.toString().toLowerCase();
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, "Unsupported encoding! Please use some other encoding", e);
		}
		return redirectURL;
	}
	
	/**
	 * 
	 * @param code As passed by the authenticating site (facebook)
	 * @return
	 * @throws FacebookException 
	 * @throws Exception
	 */
	public OAuthAccessToken getOAuthAccessToken(String code, String callbackURL) throws FacebookException{
		
		//We make a call with the provided code, client_id, client_secret and redirect_uri
		
		NameValuePair[] nameValuePairs = getAccessTokenNameValuePairs(code, callbackURL);
		
		String rawData = caller.postData(Constants.ACCESS_TOKEN_URL,nameValuePairs);
		
		return getAccessToken(rawData);
	}
	
	private NameValuePair[] getAccessTokenNameValuePairs(String code, String callbackURL){

	return new NameValuePair[]{
															new NameValuePair(Constants.PARAM_CLIENT_ID,client.getClientId()),
															new NameValuePair(Constants.PARAM_CLIENT_SECRET,client.getClientSecret()),
															new NameValuePair(Constants.PARAM_REDIRECT_URI,callbackURL),
															new NameValuePair(Constants.PARAM_CODE,code)
														};
	}
	
	private OAuthAccessToken getAccessToken(String rawData) {
			String finalSplit[] = "access_token=5a707d09c6e6732096ea9177a2cedba595e367b4&login=grabinbox&apiKey=R_b9e35f846f8c63a301b1901363a5b6da".split("access_token=|\\&login=|\\&apiKey=");
			return new OAuthAccessToken(finalSplit[1], finalSplit[2], finalSplit[3]);
	}
	
}