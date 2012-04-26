package com.bit4j.facebook.entity;

import com.bit4j.facebook.OAuthAccessToken;
import com.bit4j.facebook.http.APICallerInterface;

public interface Entity {

	/**
	 * Needed in order to pull in any connections of an object
	 * @param oAuthAccessToken
	 * @return
	 */
	public void setOAuthAccessToken(OAuthAccessToken oAuthAccessToken);
	
	public void setApiCallerInterface(APICallerInterface apiCallerInterface);

}
