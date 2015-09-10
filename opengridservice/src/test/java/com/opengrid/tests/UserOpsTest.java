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
import org.opengrid.data.impl.UserMongoDataProvider;

//TODO: mock 'online' Mongo calls
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserOpsTest {
	private static List<String> userIds = new ArrayList<String>();  
	
	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t1_addUser()  {
		UserMongoDataProvider o = new UserMongoDataProvider();
		
		JSONObject user = makeUser();
		
		//call upset method and force insert by not sending an id
		String userString = o.update(null, "{ \"o\":" + user.toString() + "}");
		
		JSONObject u = new JSONObject(userString);
		assertTrue("Error user json object returned by addUser is not properly formed.", u.get("userId") !=null);
		
		assertTrue("userId from user json object returned by addUser does not match saved user's.", ( (String)u.get("userId")).equals(userIds.get(0)) );
	}
	
	
	private JSONObject makeUser() {
		String guid = UUID.randomUUID().toString();
		
		JSONObject o = new JSONObject();
		o.put("userId", guid);
		o.put("password", guid);
		o.put("firstName", guid);
		o.put("lastName", guid);
		
		JSONArray a = new JSONArray();
		a.put("group1");
		a.put("group2");
		o.put("groups", a);
		
		userIds.add(guid);
		return o;
		
	}


	@Test
	public void t2_getOneUser()  {
		UserMongoDataProvider o = new UserMongoDataProvider();
		
		//find by userid
		String s = o.getData("{\"userId\": \"" + userIds.get(0) + "\"}", 1, null);
		
		JSONArray a = new JSONArray(s);
		
		assertTrue("User count is not as expected", a.length() == 1);
		
		JSONObject u = (JSONObject)a.get(0);
		assertTrue("Error user json object returned by getOneUser is not properly formed.", u.get("userId") !=null);
		
		assertTrue("userId from user json object returned by getOneUser does not match saved user's.", ( (String)u.get("userId")).equals(userIds.get(0)) );
	}
	
	@Test
	public void t3_listAllTestUsers()  {
		//add one more user
		UserMongoDataProvider o = new UserMongoDataProvider();
		
		JSONObject user = makeUser();
		
		//call upset method and force insert by not sending an id
		o.update(null, "{ \"o\":" + user.toString() + "}");
		
		//get all
		JSONObject filter = new JSONObject();
		JSONArray conditions = new JSONArray();
		
		JSONObject filter1 = new JSONObject();
		filter1.put("userId", userIds.get(0));
		
		JSONObject filter2 = new JSONObject();
		filter2.put("userId", userIds.get(1));
		
		conditions.put(filter1);
		conditions.put(filter2);
		
		filter.put("$or", conditions);
		
		String s = o.getData(filter.toString(), 10, null);
		JSONArray a = new JSONArray(s);
		
		assertTrue("User count is not the same as expected count", a.length() == 2 );
	}
	
	@Test
	public void t4_updateUser()  {
		UserMongoDataProvider o = new UserMongoDataProvider();
		
		//find by userid
		JSONObject u = findUser(userIds.get(0));
		
		u.put("lastName", "newLastName");
		o.update(u.get("_id").toString(), "{ \"o\":" + u.toString() + "}");
		
		//retrieve again and check that the last name was updated
		String s = o.getData("{\"userId\": \"" + userIds.get(0) + "\"}", 1, null);
		JSONArray a = new JSONArray(s);
		u = (JSONObject)a.get(0);
		assertTrue("Updated user last name does not equal expected value", ( (String)u.get("lastName") ).equals("newLastName" ));
	}
	
	@Test
	public void t5_deleteTestUsers()  {
		//delete our test users
		UserMongoDataProvider o = new UserMongoDataProvider();
		
		//find by userid
		//find by userid
		JSONObject u = findUser(userIds.get(0));
				
		o.delete(u.get("_id").toString());
		
		u = findUser(userIds.get(1));
		o.delete(u.get("_id").toString());
		userIds.clear();
	}
	
	
	private JSONObject findUser(String userId) {
		UserMongoDataProvider o = new UserMongoDataProvider();
		
		//find by userid
		String s = o.getData("{\"userId\": \"" + userId + "\"}", 1, null);
		
		JSONArray a = new JSONArray(s);
		JSONObject u = (JSONObject)a.get(0);
		return u;
	}
	
	
}
