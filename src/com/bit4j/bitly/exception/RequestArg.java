package com.bit4j.bitly.exception;

import java.io.Serializable;

public class RequestArg implements Serializable {

	private static final long serialVersionUID = -396755441578423970L;

	public String key;
	public String value;

	
	private RequestArg() {
		super();
	}


	public RequestArg(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

}
