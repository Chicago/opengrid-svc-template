package org.opengrid.service;

public class OpenGridException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5969482678578194677L;

	public OpenGridException() {
		super();
	}
	
	public OpenGridException(String message) {
		super(message);
	}
	
	public OpenGridException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OpenGridException(Throwable cause) {
		super(cause);
	}
}
