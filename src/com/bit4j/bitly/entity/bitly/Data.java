package com.bit4j.bitly.entity.bitly;

import java.io.Serializable;

public class Data implements Serializable {

	private static final long serialVersionUID = -418298335839738052L;

	private String globalHash;
	private String hash;
	private String longUrl;
	private String newHash;
	private String url;

	public String getGlobalHash() {
		return globalHash;
	}

	public String getHash() {
		return hash;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public String getNewHash() {
		return newHash;
	}

	public String getUrl() {
		return url;
	}

}
