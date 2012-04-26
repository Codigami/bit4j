package com.bit4j.bitly.entity.bitly;

import java.io.Serializable;

public class ShortURL implements Serializable {

	private static final long serialVersionUID = -3686610455246570530L;

	private Data data;

	private int statusCode;
	private String statusTxt;

	public Data getData() {
		return data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusTxt() {
		return statusTxt;
	}

}
