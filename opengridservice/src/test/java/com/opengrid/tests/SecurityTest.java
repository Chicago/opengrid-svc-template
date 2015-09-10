package com.opengrid.tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opengrid.security.impl.MongoUserService;
import org.opengrid.security.impl.User;

//TODO: mock 'online' Mongo calls
public class SecurityTest {

	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t1_ValidateUser()  {
		//positive test to validate a good user
		MongoUserService ms = new MongoUserService();
		
		User u = ms.loadUserByUsername("twitterUser");
		assertNotNull(u);
		
		assertTrue(u.getLastName().equalsIgnoreCase("User"));
		System.out.println(u.getAuthoritiesAsStringList());
	}

	
	
}
