package com.bit4j.facebook;

import com.bit4j.facebook.enums.HttpClientType;
import com.bit4j.facebook.factory.FacebookFactory;

public class Dummy {
	
	public static void main(String[] args) {
		
		FacebookFactory facebookFactory = new FacebookFactory(new Client("test", "test"), HttpClientType.APACHE_HTTP_CLIENT);
		
		//Facebook facebook = facebookFactory.getInstance(new OAuthAccessToken(""));
		
		/*try {
			facebook.comment("", "");
		} catch (FacebookException e) {
		}*/
		
	}

}
