package com.opengrid.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.opengrid.data.GenericRetrievable;
import org.opengrid.data.impl.OmniMongoDataProvider;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//TODO: mock 'online' Mongo calls
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OmniSearchTest {
	
	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t1_Search() {
		
		GenericRetrievable gr = new OmniMongoDataProvider();
		String rs = null;
				
		try {
			rs = gr.getData("twitter", 
				"opengrid_badzzz", 
				"{}", //filter
				20,
				null);
			assertTrue("An exception on non-existent collection is expected", false);
		} catch (Exception ex) {
			assertTrue("Unexpected exception message on bad meta collection", ex.getMessage().indexOf("Cannot find 'datasets' document") > -1);
		}
		
		try {
			rs = gr.getData("twitter", 
				"opengrid_data", 
				"{}", //filter
				20,
				null);
			assertTrue("An exception on wrong collection is expected", false);
		} catch (Exception ex) {
			assertTrue("Unexpected exception message on bad meta collection", ex.getMessage().indexOf("Cannot find 'datasets' document") > -1);
		}
		
		rs = gr.getData("twitter", 
				"opengrid_meta", 
				"{}", //filter
				20,
				null);
		System.out.println(rs);
		assertTrue("Result cannot be null", rs !=null);
	}
}

