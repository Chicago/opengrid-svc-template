package org.opengrid.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import org.opengrid.constants.Exceptions;
import org.opengrid.exception.ExceptionDetail;
import org.opengrid.exception.ServiceException;

public class ExceptionUtil {
	private static ResourceBundle RESOURCE_BUNDLE = null;
	
	private ExceptionUtil() {
	}


	public static ServiceException getException(String exceptionCode, String additionalDetails) {
		//get full prop value for error code
		String value = getPropertyValue(exceptionCode);
		
		//parse messages
		String[] a = value.split(Exceptions.ERR_MESSAGE_DELIM);

		if (a == null || a.length == 0) {
			throw getServiceException("Malformed 'exceptionList.properties' file.");
		}
	
		ExceptionDetail f = getExceptionDetail(exceptionCode, a, additionalDetails); 
						
		return new ServiceException(f.getErrorMessage(), f);		
	}
	
	
	private static ExceptionDetail getExceptionDetail(String exceptionCode, String[] a,	String additionalDetails) {
		ExceptionDetail f = new ExceptionDetail();		
		
		String faultString = "";
	
		if (a.length > 1)
			faultString = a[0] + " " + a[1] + " " + (additionalDetails != null ? additionalDetails : "");
		else
			faultString = (additionalDetails != null ? a[0] + " " + additionalDetails : a[0]);
		f.setErrorMessage(faultString);
		f.setErrorCode(exceptionCode);
		return f;
	}
	

	private static String getPropertyValue(String exceptionCode) {
		String value=null;
		
		//read value for exception code
		try {
			//better to do it this way to catch exceptions cleanly
			if (RESOURCE_BUNDLE == null)
				RESOURCE_BUNDLE = ResourceBundle.getBundle(org.opengrid.constants.Exceptions.RESOURCE_BUNDLE);
			
			value = RESOURCE_BUNDLE.getString(exceptionCode);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to load 'exceptionList.properties' file or settings missing from properties file. Native error: " + e.getMessage(), e);
		}
		return value;
	}
	

	//used as a last resort (forced exception) w/o dependence on exceptionList.properties file
	//normal way to invoke exception is to pass an exception code to getException
	public static ServiceException getServiceException(String message) {
		String s = "A general service exception has occurred. ";
		
		ExceptionDetail f = new ExceptionDetail();
		f.setErrorMessage(s + message);
		f.setErrorCode(org.opengrid.constants.Exceptions.ERR_SERVICE);
		
		return new ServiceException(f);
	}

	
	public static ServiceException wrapException(Exception ex) {
		//TODO: log original exception
		
		//determine if it is not one of our custom exception
		if ( !(ex instanceof org.opengrid.exception.ServiceException) ) {
			//map to pur own exceptions, if needed
			/*if (ex instanceof SomeExceptionType) {
				return getException(Constants.ERR_INCEVAL0102, se.getMessage());				
			} else {*/
				String m="";
				if (ex.getMessage() == null)
					m = ex.getClass().getName();
				else
					m = ex.getMessage();
				//non-service exception, wrap as one with system error code
				return getException(Exceptions.ERR_SYSTEM, m);
			//}
		}
		return (ServiceException) ex;
	}
	
	
	
	
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString(); 		
	}
}
