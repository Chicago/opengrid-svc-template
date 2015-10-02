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
import org.opengrid.data.meta.OpenGridDataset;
import org.opengrid.data.meta.OpenGridMeta;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MetaTest {
	
	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	//@Test
	public void t1_ReadMeta() throws JsonParseException, JsonMappingException, IOException  {
		OpenGridMeta meta;
		ObjectMapper mapper = new ObjectMapper();
		
		meta = mapper.readValue(getClass().getClassLoader().getResourceAsStream("opengrid_meta_generic.json"), OpenGridMeta.class);
		assertTrue("Dataset count must equal 2", meta.getDatasets().size() == 2);
	}
	
	@Test
	public void t1_foo() {
		Double d = 1336003040000.00;
		long d2 = 1336003040000L;
		
		Object o = d;
		//Object o = d2;
		
		Long l = null;
		if (o instanceof Double)			
			l = ((Double) o).longValue();
		else
			l = (Long) o;
	}
}

