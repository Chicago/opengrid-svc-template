package com.opengrid.tests;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opengrid.exception.ServiceException;
import org.opengrid.security.RoleAccessValidator;
import org.opengrid.security.impl.JwtRoleAccessValidator;
import org.opengrid.util.ExceptionUtil;
import org.opengrid.constants.Exceptions;

//TODO: mock 'online' Mongo calls
public class RoleAccessTest {

	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t1_CheckAccess()  {
		RoleAccessValidator av = new JwtRoleAccessValidator();
		
		//String s = "/rest/datasets/twitter/query";
		//System.out.println(s.matches("(.*)/datasets(.*)"));
		
		boolean ret = av.lookupAccessMap("/rest/datasets/twitter/query", "GET", "twitter,weather");
		assertTrue("Security look up returned false when it should be true.", ret);

		ret = av.lookupAccessMap("/rest/datasets/twitter/query", "GET", "weather");
		assertTrue("Security look up returned true when it should be false.", ret == false);

		ret = av.lookupAccessMap("/rest/datasets/weather/query", "GET", "twitter,weather");
		assertTrue("Security look up returned false when it should be true.", ret);

		ret = av.lookupAccessMap("/rest/groups", "PUT", "$admin");
		assertTrue("Security look up returned false when it should be true.", ret);

		ret = av.lookupAccessMap("/rest/groups", "GET", "twitter");
		assertTrue("Security look up returned true when it should be false.", ret == true);

		ret = av.lookupAccessMap("/rest/groups", "POST", "twitter");
		assertTrue("Security look up returned true when it should be false.", ret == false);

		ret = av.lookupAccessMap("/rest/queries", "POST", "twitter");
		assertTrue("Security look up returned false when it should be true.", ret);
		
		ret = av.lookupAccessMap("/rest/queries", "POST", "weather");
		assertTrue("Security look up returned false when it should be true.", ret);
		
		ret = av.lookupAccessMap("/rest/queries", "GET", "");
		assertTrue("Security look up returned false when it should be true.", ret);

		//test case when user does not have any access to any resources
		ret = av.lookupAccessMap("/rest/datasets/twitter/query", "GET", "");
		assertTrue("Security look up returned true when it should be false.", ret == false);

	}

	
	
}
