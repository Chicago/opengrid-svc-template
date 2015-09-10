package com.opengrid.tests;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;
import org.opengrid.constants.Exceptions;

public class ServiceExceptionTest {

	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t1_RaiseServiceException()  {
		try {
			ServiceException e = ExceptionUtil.getException(Exceptions.ERR_DB, "More detail about our database exception");
			//ServiceException e = ExceptionUtil.getException(Exceptions.ERR_DB, null);
			System.out.println(e.getExceptionDetail().toJsonString());
			
			JSONObject o = new JSONObject(e.getExceptionDetail().toJsonString());
			assertTrue("Error json object is not properly formed.", o.get("error") !=null);
			
			throw e;
		} catch (ServiceException ex) {
			System.out.println(ex.getExceptionDetail().getErrorCode());
			System.out.println(ex.getExceptionDetail().getErrorMessage());
			System.out.println(ex.getMessage());
		}
	}

	
	
}
