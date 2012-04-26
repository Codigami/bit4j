package com.bit4j.bitly.http;

import org.apache.commons.httpclient.NameValuePair;

import com.bit4j.bitly.exception.BitlyException;

public interface APICallerInterface {
	
	public String getData(String url, NameValuePair[] nameValuePairs) throws BitlyException;

  public String postData(String url, NameValuePair[] nameValuePairs) throws BitlyException;
  
  public String deleteData(String url, NameValuePair[] nameValuePairs) throws BitlyException;
}
