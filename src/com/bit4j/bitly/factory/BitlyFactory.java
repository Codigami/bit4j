package com.bit4j.bitly.factory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.NameValuePair;

import com.bit4j.bitly.Bitly;
import com.bit4j.bitly.Client;
import com.bit4j.bitly.OAuthAccessToken;
import com.bit4j.bitly.enums.HttpClientType;
import com.bit4j.bitly.exception.BitlyException;
import com.bit4j.bitly.http.APICallerFactory;
import com.bit4j.bitly.http.APICallerInterface;
import com.bit4j.bitly.util.Constants;

/**
 * You can create a singleton instance of this class. This class is thread safe and you can have 
 * one instance for every unique client id and client secret
 * @author Nischal Shetty
 */
public class BitlyFactory extends OAuthFactory implements Serializable {

	private static final long serialVersionUID = 8997415594842693532L;
	
	private Client client;
	private HttpClientType httpClientType;
	private APICallerInterface caller; 
	
	Logger logger = Logger.getLogger(Bitly.class.getName());
	
	public BitlyFactory(String clientId, String clientSecret){
		this(new Client(clientId, clientSecret), HttpClientType.APACHE_HTTP_CLIENT);
	}
	
	public BitlyFactory(Client client){
		this(client,HttpClientType.APACHE_HTTP_CLIENT);
	}
	
	public BitlyFactory(String clientId, String clientSecret, HttpClientType clientType){
		this(new Client(clientId, clientSecret),clientType);
	}
	
	public BitlyFactory(Client client, HttpClientType clientType){
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
	public Bitly getInstance(OAuthAccessToken accessToken){
		return new Bitly(accessToken,httpClientType);
	}

	
	public String getRedirectURL(String callbackURL){
		
		String redirectURL = null;
		
		try {
			redirectURL = Constants.AUTHORIZE_URL+"?"+Constants.PARAM_CLIENT_ID+"="+client.getClientId() 
											+ "&"+Constants.PARAM_REDIRECT_URI+"="+URLEncoder.encode(callbackURL,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, "Unsupported encoding! Please use some other encoding", e);
		}
		return redirectURL;
	}
	
	/**
	 * 
	 * @param code As passed by the authenticating site (facebook)
	 * @return
	 * @throws BitlyException 
	 * @throws Exception
	 */
	public OAuthAccessToken getOAuthAccessToken(String code, String callbackURL) throws BitlyException{
		
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
			OAuthAccessToken accessToken = new OAuthAccessToken(finalSplit[1]);
			accessToken.setLogin(finalSplit[2]);
			accessToken.setApiKey(finalSplit[3]);
			
			return accessToken;
	}
	
}