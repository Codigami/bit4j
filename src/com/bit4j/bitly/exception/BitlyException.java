package com.bit4j.bitly.exception;

public class BitlyException extends Exception {

	private static final long serialVersionUID = -3433466538055836949L;
  private BitlyError error;

  public BitlyException(String msg, Exception exception) {
      super(msg, exception);
  }

  public BitlyException(BitlyError error) {
		super();
		this.error = error;
	}

	public BitlyError getError() {
		return error;
	}

	public void setError(BitlyError error) {
		this.error = error;
	}
	
	@Override
	public String getMessage() {
		return (this.getError() == null) ? super.getMessage() : this.getError().getErrorMsg();
	}
	
	@Override
	public String getLocalizedMessage() {
		return this.getMessage();
	}

}