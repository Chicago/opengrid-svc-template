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
import org.opengrid.data.impl.GroupMongoDataProvider;

//TODO: mock 'online' Mongo calls
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupOpsTest {
	private static List<String> groupIds = new ArrayList<String>();  
	
	@BeforeClass  
	public static void initTest() throws Exception {
	}
	
	
	@AfterClass  
	public static void cleanupTest() {
	}
	
	@Test
	public void t0_DeleteGroupWithUsers()  {
		//delete an existing group
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		//find by groupId
		//find by groupId
		JSONObject u = findGroup("opengrid_users");
			
		try {
			o.delete(u.get("_id").toString());
		} catch (Exception x) {
			//should get
			System.out.println("Delete exception is expected");
		}
}
	
	
	@Test
	public void t1_addGroup()  {
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		JSONObject grp = makeGroup();
		
		//call upset method and force insert by not sending an id
		String groupString = o.update(null, "{ \"o\":" + grp.toString() + "}");
		
		JSONObject u = new JSONObject(groupString);
		assertTrue("Error group json object returned by addGroup is not properly formed.", u.get("groupId") !=null);
		
		assertTrue("groupId from user json object returned by addGroup does not match saved group's.", ( (String)u.get("groupId")).equals(groupIds.get(0)) );
	}
	
	
	private JSONObject makeGroup() {
		String guid = UUID.randomUUID().toString();
		
		JSONObject o = new JSONObject();
		o.put("groupId", guid);
		o.put("name", guid);
		o.put("description", guid);
		
		groupIds.add(guid);
		return o;
		
	}


	@Test
	public void t2_getOneGroup()  {
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		//find by groupId
		String s = o.getData("{\"groupId\": \"" + groupIds.get(0) + "\"}", 1, null);
		
		JSONArray a = new JSONArray(s);
		
		assertTrue("Group count is not as expected", a.length() == 1);
		
		JSONObject u = (JSONObject)a.get(0);
		assertTrue("Error group json object returned by getOneGroup is not properly formed.", u.get("groupId") !=null);
		
		assertTrue("groupId from group json object returned by getOneGroup does not match saved group's.", ( (String)u.get("groupId")).equals(groupIds.get(0)) );
	}
	
	@Test
	public void t3_listAllTestGroups()  {
		//add one more user
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		JSONObject grp = makeGroup();
		
		//call upset method and force insert by not sending an id
		o.update(null, "{ \"o\":" + grp.toString() + "}");
		
		//get all
		JSONObject filter = new JSONObject();
		JSONArray conditions = new JSONArray();
		
		JSONObject filter1 = new JSONObject();
		filter1.put("groupId", groupIds.get(0));
		
		JSONObject filter2 = new JSONObject();
		filter2.put("groupId", groupIds.get(1));
		
		conditions.put(filter1);
		conditions.put(filter2);
		
		filter.put("$or", conditions);
		
		String s = o.getData(filter.toString(), 10, null);
		JSONArray a = new JSONArray(s);
		
		assertTrue("Group count is not the same as expected count", a.length() == 2 );
	}
	
	@Test
	public void t4_updateGroup()  {
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		//find by groupid
		JSONObject u = findGroup(groupIds.get(0));
		
		u.put("name", "newName");
		o.update(u.get("_id").toString(), "{ \"o\":" + u.toString() + "}");
		
		//retrieve again and check that the last name was updated
		String s = o.getData("{\"groupId\": \"" + groupIds.get(0) + "\"}", 1, null);
		JSONArray a = new JSONArray(s);
		u = (JSONObject)a.get(0);
		assertTrue("Updated group name does not equal expected value", ( (String)u.get("name") ).equals("newName" ));
	}
	
	@Test
	public void t5_deleteTestGroups()  {
		//delete our test groups
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		//find by groupId
		//find by groupId
		JSONObject u = findGroup(groupIds.get(0));
				
		o.delete(u.get("_id").toString());
		
		u = findGroup(groupIds.get(1));
		o.delete(u.get("_id").toString());
		groupIds.clear();
	}
	
	
	private JSONObject findGroup(String groupId) {
		GroupMongoDataProvider o = new GroupMongoDataProvider();
		
		//find by groupId
		String s = o.getData("{\"groupId\": \"" + groupId + "\"}", 1, null);
		
		JSONArray a = new JSONArray(s);
		JSONObject u = (JSONObject)a.get(0);
		return u;
	}
	
	
}
