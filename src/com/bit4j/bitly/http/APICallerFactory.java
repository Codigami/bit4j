package com.bit4j.bitly.http;

import java.util.HashMap;
import java.util.Map;

import com.bit4j.bitly.enums.HttpClientType;

public class APICallerFactory {

	private static Map<HttpClientType, APICallerInterface> callers;
	
	static {
		callers = new HashMap<HttpClientType, APICallerInterface>();
		callers.put(HttpClientType.APACHE_HTTP_CLIENT, APICaller.getInstance());
		callers.put(HttpClientType.URL_FETCH_SERVICE, new URLFetchAPICaller());
	}
	
	public static APICallerInterface getAPICallerInstance(HttpClientType clientType){
		return callers.get(clientType);
	}
	
}
