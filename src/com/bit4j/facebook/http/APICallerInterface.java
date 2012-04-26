package com.bit4j.facebook.http;

import org.apache.commons.httpclient.NameValuePair;

import com.bit4j.facebook.exception.FacebookException;

public interface APICallerInterface {
	
	public String getData(String url, NameValuePair[] nameValuePairs) throws FacebookException;

  public String postData(String url, NameValuePair[] nameValuePairs) throws FacebookException;
  
  public String deleteData(String url, NameValuePair[] nameValuePairs) throws FacebookException;
}
