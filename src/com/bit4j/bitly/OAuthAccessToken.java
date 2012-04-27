package com.bit4j.bitly;

import java.io.Serializable;

public class OAuthAccessToken implements Serializable {

	private static final long serialVersionUID = 359116283414855580L;

	private String accessToken;
	private String login;
	private String apiKey;

	public OAuthAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * The access token as obtained from bitly.
	 * 
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

	public void setLogin(String login) {
		this.login = login;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
