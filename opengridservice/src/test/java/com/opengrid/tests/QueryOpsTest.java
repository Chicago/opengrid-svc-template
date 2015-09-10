package com.opengrid.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.opengrid.data.impl.QueryMongoDataProvider;

//TODO: mock 'online' Mongo calls
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryOpsTest {
	private static List<String> queryIds = new ArrayList<String>();  
	
	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t1_addQuery()  {
		QueryMongoDataProvider o = new QueryMongoDataProvider();
		
		JSONObject q = makeQuery();
		
		//call upset method and force insert by not sending an id
		String queryString = o.update(null, "{ \"o\":" + q.toString() + "}");
		
		JSONObject u = new JSONObject(queryString);
		assertTrue("Error query json object returned by addGroup is not properly formed.", u.get("name") !=null);
		
		assertTrue("name from query json object returned by addQuery does not match saved query's.", ( (String)u.get("name")).equals(queryIds.get(0)) );
	}
	
	
	private JSONObject makeQuery() {
		String guid = UUID.randomUUID().toString();
		
		JSONObject o = new JSONObject("{ \"name\" : \"Weather Records for 60601\", \"owner\" : \"jsmith\", \"spec\" : [{ \"dataSetId\" : \"weather\", \"filters\" : { \"condition\" : \"AND\", \"rules\" : [{ \"id\" : \"zipcode\", \"field\" : \"zipcode\", \"type\" : \"string\", \"input\" : \"text\", \"operator\" : \"equal\", \"value\" : \"60601\" }] }, \"rendition\" : { \"color\" : \"#DC143C\", \"opacity\" : 85, \"size\" : 6 } }], \"sharedWith\" : { \"users\" : [], \"groups\" : [] }, \"isCommon\" : false }");;
		//we need to make owner, name combination unique
		o.put("owner", UUID.randomUUID().toString());
		o.put("name", guid);
		
		queryIds.add(guid);
		return o;
		
	}


	@Test
	public void t2_getOneQuery()  {
		QueryMongoDataProvider o = new QueryMongoDataProvider();
		
		//find by queryId
		String s = o.getData("{\"name\": \"" + queryIds.get(0) + "\"}", 1, null);
		
		JSONArray a = new JSONArray(s);
		
		assertTrue("Group count is not as expected", a.length() == 1);
		
		JSONObject u = (JSONObject)a.get(0);
		assertTrue("Error query json object returned by getOneGroup is not properly formed.", u.get("name") !=null);
		
		assertTrue("name from query json object returned by getOneGroup does not match saved query's.", ( (String)u.get("name")).equals(queryIds.get(0)) );
	}
	
	@Test
	public void t3_listAllTestQueries()  {
		//add one more user
		QueryMongoDataProvider o = new QueryMongoDataProvider();
		
		JSONObject q = makeQuery();
		
		//call upset method and force insert by not sending an id
		o.update(null, "{ \"o\":" + q.toString() + "}");
		
		//get all
		JSONObject filter = new JSONObject();
		JSONArray conditions = new JSONArray();
		
		JSONObject filter1 = new JSONObject();
		filter1.put("name", queryIds.get(0));
		
		JSONObject filter2 = new JSONObject();
		filter2.put("name", queryIds.get(1));
		
		conditions.put(filter1);
		conditions.put(filter2);
		
		filter.put("$or", conditions);
		
		String s = o.getData(filter.toString(), 10, null);
		JSONArray a = new JSONArray(s);
		
		assertTrue("Query count is not the same as expected count", a.length() == 2 );
	}
	
	@Test
	public void t4_updateQuery()  {
		QueryMongoDataProvider o = new QueryMongoDataProvider();
		
		//find by queryid
		JSONObject u = findQuery(queryIds.get(0));
		
		u.put("owner", "newName");
		o.update(u.get("_id").toString(), "{ \"o\":" + u.toString() + "}");
		
		//retrieve again and check that the last name was updated
		String s = o.getData("{\"name\": \"" + queryIds.get(0) + "\"}", 1, null);
		JSONArray a = new JSONArray(s);
		u = (JSONObject)a.get(0);
		assertTrue("Updated query name does not equal expected value", ( (String)u.get("owner") ).equals("newName" ));
	}
	
	@Test
	public void t5_deleteTestQueries()  {
		//delete our test querys
		QueryMongoDataProvider o = new QueryMongoDataProvider();
		
		//find by queryId
		//find by queryId
		JSONObject u = findQuery(queryIds.get(0));
				
		o.delete(u.get("_id").toString());
		
		u = findQuery(queryIds.get(1));
		o.delete(u.get("_id").toString());
		queryIds.clear();
	}
	
	
	private JSONObject findQuery(String queryId) {
		QueryMongoDataProvider o = new QueryMongoDataProvider();
		
		//find by queryId
		String s = o.getData("{\"name\": \"" + queryId + "\"}", 1, null);
		
		JSONArray a = new JSONArray(s);
		JSONObject u = (JSONObject)a.get(0);
		return u;
	}
	
	
}
