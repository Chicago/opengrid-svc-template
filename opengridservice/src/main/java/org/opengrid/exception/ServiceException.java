package org.opengrid.exception;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8339229550635932986L;
	
	
	private ExceptionDetail exceptionDetail;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	public ServiceException(String message, ExceptionDetail xd) {
		super(message);
		this.exceptionDetail = xd;
	}
	
	public ServiceException(ExceptionDetail xd) {
		super(xd.getErrorMessage());
		this.exceptionDetail = xd;
	}
	
	public ServiceException(String message, ExceptionDetail xd, Throwable cause) {
		super(message, cause);
		this.exceptionDetail = xd;
	}
		
	
	
	public ExceptionDetail getExceptionDetail() {
		  return exceptionDetail;
	}
}