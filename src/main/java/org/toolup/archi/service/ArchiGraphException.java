package org.toolup.archi.service;

public class ArchiGraphException extends Exception {

	private static final long serialVersionUID = 2942181844034655306L;

	private int httpStatus;
	
	public ArchiGraphException(String msg, Throwable t, int httpStatus) {
		super(msg, t);
		this.httpStatus = httpStatus;
	}
	
	public ArchiGraphException(String msg, int httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
	
	public ArchiGraphException(Throwable t, int httpStatus) {
		super(t);
		this.httpStatus = httpStatus;
	}

	public int getHttpStatus() {
		return httpStatus;
	}
	
}
