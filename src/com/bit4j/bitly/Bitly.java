package com.bit4j.bitly;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.apache.commons.httpclient.NameValuePair;

import com.bit4j.bitly.entity.bitly.ShortURL;
import com.bit4j.bitly.enums.HttpClientType;
import com.bit4j.bitly.exception.BitlyException;
import com.bit4j.bitly.http.APICallerFactory;
import com.bit4j.bitly.http.APICallerInterface;
import com.bit4j.bitly.util.Constants;
import com.bit4j.bitly.util.JSONToObjectTransformer;

/**
 * This is the main Bitly class. All the action is here!
 * 
 * @author Nischal Shetty - nischal@codigami.com
 */
public class Bitly implements Serializable {

	private static final long serialVersionUID = -7949886324407828634L;

	Logger logger = Logger.getLogger(Bitly.class.getName());

	private OAuthAccessToken authAccessToken;

	private APICallerInterface caller = null;

	/**
	 * If only the access token is passed, then the Apache Http Client library is used for making http
	 * requests
	 * 
	 * @param authAccessToken
	 */
	public Bitly(OAuthAccessToken authAccessToken) {
		// apache http client is the default client type
		this(authAccessToken, HttpClientType.APACHE_HTTP_CLIENT);
	}

	public Bitly(OAuthAccessToken authAccessToken, HttpClientType clientType) {
		this.authAccessToken = authAccessToken;
		caller = APICallerFactory.getAPICallerInstance(clientType);
	}

	public OAuthAccessToken getAuthAccessToken() {
		return authAccessToken;
	}

	private NameValuePair getNameValuePairAccessToken() {
		return new NameValuePair(Constants.PARAM_ACCESS_TOKEN, this.authAccessToken.getAccessToken());
	}
	
	public ShortURL shorten(String longUrl) throws BitlyException {
		//bitly expects all long urls to have / before the param start i.e. before ?
		//2 cases:
		//case 1: http://grabinbox.com/?param=test
		//case 2: http://grabinbox.com?param=test
		//By aplying #replace twice, we make sure that both the cases are satisfied
		longUrl = longUrl.replaceFirst("/\\?", "\\?").replaceFirst("\\?", "/?");
		
		NameValuePair[] nameValuePairs = new NameValuePair[]{getNameValuePairAccessToken(), new NameValuePair(Constants.PARAM_LONG_URL,longUrl)};
		return pullData(Constants.BITLY_URL+Constants.BITLY_LINKS_SHORTEN_URL, ShortURL.class, nameValuePairs);
	}
	
	
	/**
	 * Raw API method to pull any data in json form and transform it into the right object <br>
	 * An HTTP GET method is used here
	 * 
	 * @param <E>
	 * @param url
	 * @param e The class into which the json object returned by the url fetch needs to be cast
	 * @param nameValuePairs Pass parameters that need to accompany the call
	 * @return
	 * @throws BitlyException
	 */
	public <E> E pullData(String url, Class<E> e, NameValuePair[] nameValuePairs) throws BitlyException {
		// APICaller would retrieve the json string object from facebook by making a https call
		// Once the json string object is obtaind, it is passed to obj transformer and the right object
		// is retrieved
		return JSONToObjectTransformer.getObject(caller.getData(url, nameValuePairs), e);
	}
	
	/**
	 * This method is useful when your json contains maps (key value pairs). Send in parameterized maps.<br>
	 * Example: Type type = new TypeToken<Map<String, User>>(){}.getType();
	 * @param <E>
	 * @param url
	 * @param type
	 * @param nameValuePairs
	 * @return
	 * @throws BitlyException
	 */
	public <E> E pullData(String url, Type type, NameValuePair[] nameValuePairs) throws BitlyException {
		// APICaller would retrieve the json string object from facebook by making a https call
		// Once the json string object is obtaind, it is passed to obj transformer and the right object
		// is retrieved
		return JSONToObjectTransformer.<E>getObject(caller.getData(url, nameValuePairs), type);
	}
	

}