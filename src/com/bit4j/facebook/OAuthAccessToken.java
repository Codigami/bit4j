package com.bit4j.facebook;

import java.io.Serializable;

public class OAuthAccessToken implements Serializable {
	
	private static final long serialVersionUID = 359116283414855580L;
	
	private String accessToken;
	private String login;
	private String apiKey;
	
	public OAuthAccessToken(String accessToken, String login, String apiKey){
		this.accessToken = accessToken;
		this.login = login;
		this.apiKey = apiKey;
	}

	/**
	 * The access token as obtained from bitly. 
	 * @return
	 */
	public String getAccessToken() {
		return accessToken;
	}

	public String getLogin() {
		return login;
	}

	public String getApiKey() {
		return apiKey;
	}
	
	

}
