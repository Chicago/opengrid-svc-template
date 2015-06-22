package org.opengrid.service;

import org.opengrid.data.Retrievable;
import org.opengrid.data.Updatable;
import org.opengrid.data.impl.TwitterFileDataProvider;
import org.opengrid.pojo.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

import org.opengrid.pojo.PojoMapper;
import org.opengrid.pojo.UsersPojo;
import org.springframework.stereotype.Component;

@Component("OpenGridServiceBean")
public class OpenGridServiceImpl implements OpenGridService {

	@Resource(name="twitterDataProvider")
	private Retrievable twitterDataProvider;
	
	@Resource(name="weatherDataProvider")
	private Retrievable weatherDataProvider;
	
	@Resource(name="queryDataProvider")
	private Retrievable queryDataProvider;

	@Resource(name="queryDataUpdater")
	private Updatable queryDataUpdater;

	
	@Override
	public String getAllOpenGridLists() {
		//dummy response for now, not implemented for Sprint 1
		String myStr = "all objects here !";
		return myStr;
	}

	@Override
	public OpenGridResult postOpenGridUserToken(String users, String password) {
		OpenGridResult response = new OpenGridResult();

		if ((users != null) && (password != null)) {
			response = new OpenGridResult();
			
			//dummy response for now
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

			return response;
		} else {

			response.setStatusMessage("Couldn't find user !");

			return response;
		}
	}

	@Override
	public String getOpenGridUserList(String filter) {
		//mock response for now, not implemented for Sprint 1
		String pojoAsString = null;
		UsersPojo pojo = new UsersPojo();
		// Populate it with some data
		pojo.setDepartment("eki");
		pojo.setDeptNumber("myNuber");
		pojo.setLastName("Fan");
		pojo.setFirstName("Wayne");
		pojo.setUserID("wfan");
		pojo.setUserPass("504bd6582c176712c214d370");
		pojo.setEmail("wfan@eki-consulting.com");

		Map<String, Date> map = new HashMap<String, Date>();
		map.put("now", new Date());
		pojo.setMap(map);

		// Map it to JSON and then back again
		try {
			pojoAsString = PojoMapper.toJson(pojo, true);
			System.out.println("POJO as string:\n" + pojoAsString + "\n");
			UsersPojo pojo2 = (UsersPojo) PojoMapper.fromJson(pojoAsString,
					UsersPojo.class);
			System.out.println("POJO toString():\n" + pojo2 + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pojoAsString;
	}

	@Override
	public String getOpenGridOneUser(String userId) {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();
		
		myObject.setId("504bd6582c176712c214d370");
		myObject.setName("db.windygrid.findOne({'user1' : 'kaslkasfa'})");

		return "{userid: " + userId + "}";
	}

	@Override
	public OpenGridResult updateOpenGridOneUser(String userid) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();
		if (userid != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Service Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public String getOpenGridGroupsList() {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();

		myObject.setId("504bd6582c176712c214d370");
		myObject.setName("db.windygrid.findOne({'team1' : 'myGroups'})");

		return myObject.getName();
	}

	@Override
	public OpenGridResult createOpenGridNewGroup() {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		response.setStatusMessage("WindyGrid Service Get Success !");
		response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		return response;
	}

	@Override
	public String getOpenGridOneGroup(String groupId) {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();

		myObject.setId("504bd6582c176712c214d370");
		myObject.setName("db.windygrid.findOne({'user1' : 'kaslkasfa'})");

		return "{groupid: " + groupId + "}";
	}

	@Override
	public OpenGridResult updateOpenGridOneGroup(String groupId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		if (groupId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public OpenGridResult deleteOpenGridOneGroup(String groupId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();
		if (groupId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Service Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public String getOpenGridOneGroupMembersList(String groupId) {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();
		if (groupId.equalsIgnoreCase("504bd6582c176712c214d370")) {
			myObject.setId("504bd6582c176712c214d370");
			myObject.setName("db.windygrid.findOne({'user1' : 'kaslkasfa'})");

			return myObject.getName();
		} else {
			myObject.setErrorMessage("groupId is not matched");

			return myObject.getErrorMessage();
		}
	}

	@Override
	public OpenGridResult addOpenGridOneGroupOneMember(String groupId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		if (groupId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public OpenGridResult deleteOpenGridOneGroupOneMember(String groupId,
			String memberId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();
		if ((groupId != null) && (memberId != null)) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Service Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public String getOpenGridOneGroupAlertsList(String groupId) {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();
		if (groupId.equalsIgnoreCase("504bd6582c176712c214d370")) {
			myObject.setId("504bd6582c176712c214d370");
			myObject.setName("db.windygrid.findOne({'user1' : 'kaslkasfa'})");

			return myObject.getName();
		} else {
			myObject.setErrorMessage("groupId is not matched");

			return myObject.getErrorMessage();
		}

	}

	@Override
	public OpenGridResult createOpenGridOneGroupNewAlert(String groupId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		if (groupId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public String getOpenGridAlertsList() {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();

		myObject.setId("504bd6582c176712c214d370");
		myObject.setName("db.windygrid.findOne({'type' : '311'})");

		return myObject.getName();
	}

	@Override
	public OpenGridResult addOpenGridNewAlert() {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		response.setStatusMessage("WindyGrid Service Get Success !");
		response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		return response;
	}

	@Override
	public String getOpenGridOneAlert(String alertId) {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();
		if (alertId.equalsIgnoreCase("504bd6582c176712c214d370")) {
			myObject.setId("504bd6582c176712c214d370");
			myObject.setName("db.windygrid.findOne({'user1' : 'kaslkasfa'})");

			return myObject.getName();
		} else {
			myObject.setErrorMessage("groupId is not matched");

			return myObject.getErrorMessage();
		}
	}

	@Override
	public OpenGridResult putOpenGridOneAlert(String alertId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		if (alertId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public OpenGridResult deleteOpenGridOneAlert(String alertId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();
		if (alertId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Service Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public String getOpenGridDatasetsList() {
		String err="";
		
		String retString = null;
		ArrayList pojo = new ArrayList();
		JsonObject pojo1 = new JsonObject();
		JsonObject pojo2 = new JsonObject();
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();

			pojo1 = (JsonObject) PojoMapper.fromJson(
						twitterDataProvider.getDescriptor(),
						JsonObject.class
					);

			pojo2 = (JsonObject) PojoMapper.fromJson(
					weatherDataProvider.getDescriptor(),
					JsonObject.class);

			pojo.add(0, pojo1);
			pojo.add(1, pojo2);

			retString = PojoMapper.toJson(pojo, true);
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();
		}
		//return error, implement in a later Sprint
		if (err.length() >0)
			return err;
		return retString;
	}

	
	@Override
	public OpenGridResult addOpenGridNewdataset() {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();

		response.setStatusMessage("WindyGrid Service Get Success !");
		response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		return response;
	}

	@Override
	public String getOpenGridOneDataset(String datasetId) {
		String err="";
		try {
			if (datasetId.equalsIgnoreCase(org.opengrid.constants.DataTypes.TWITTER)) {
				return twitterDataProvider.getDescriptor();
				
			} else if (datasetId.equalsIgnoreCase(org.opengrid.constants.DataTypes.WEATHER)) {				
				return weatherDataProvider.getDescriptor();				
			}
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();			
		}
		//return error, implement in a later Sprint
		return "{datasetId:" + datasetId  + ", error: " + err + "}";
	}
	

	@Override
	public String executeOpenGridQueryWithParams(String datasetId, String filter, int max, String sort) {
		String err="";
		try {
			if (datasetId.equalsIgnoreCase(org.opengrid.constants.DataTypes.TWITTER)) {
				return twitterDataProvider.getData(filter, max, sort);
				
			} else if (datasetId.equalsIgnoreCase(org.opengrid.constants.DataTypes.WEATHER)) {				
				return weatherDataProvider.getData(filter, max, sort);
				
			} else {
				//return error, implement in a later Sprint
				return "{datasetId:" + datasetId  + ", filter:" + filter + ", max:" + max + "}";
			}
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();			
		}
		//return error, implement in a later Sprint
		return "{datasetId:" + datasetId  + ", filter:" + filter + ", max:" + max + ", error: " + err + "}";
	}

	
	
	@Override
	public String executeOpenGridQuery(String datasetId, String queryId) {
		
		//return mock response for now, may not need this method
		return datasetId + queryId;
	}

	@Override
	public String getOpenGridQueriesList(String filter, int max, String sort) {
		String err="";
		try {
			return queryDataProvider.getData(filter, max, sort);
			
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();			
		}
		//return error, implement in a later Sprint
		return "{datasetId:query, filter:" + filter + ", max:" + max + ", error: " + err + "}";
	}

	@Override
	public String addOpenGridNewQuery(String entity) {
		String err="";
		try {
			queryDataUpdater.update(null, entity);
			
			//return success? (cleanup/standardize later)
			return "{}";						
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();			
		}
		//return error, implement in a later Sprint
		return "{datasetId: query, op: new, error: " + err + "}";
	}

	@Override
	public String getOpenGridOneQuery(String queryId) {
		String err="";
		String filter = "{\"_id\": {\"$eq\": " + queryId + "}}";
		try {
			return queryDataProvider.getData(filter, 1, null);
			
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();			
		}
		//return error, implement in a later Sprint
		return "{datasetId: query, filter:" + filter + ", max: 1, error: " + err + "}";
	}

	@Override
	public OpenGridResult updateOpenGridOneQuery(String queryId, String entity) {
		String err="";
		try {
			queryDataUpdater.update(queryId, entity);
			
			//return success? (cleanup/standardize later)
			return null;						
		} catch (Exception e) {
			err = e.getMessage();
			e.printStackTrace();			
		}
		return null;
	}

	@Override
	public OpenGridResult deleteOpenGridOneQuery(String querId) {
		//mock response for now, not implemented for Sprint 1
		OpenGridResult response = new OpenGridResult();
		if (querId != null) {
			response.setStatusMessage("WindyGrid Service Get Success !");
			response.setStatusType(OpenGridResultStatusEnum.SUCCESS);

		} else {
			response.setStatusMessage("WindyGrid Service Get Failed !");
			response.setStatusType(OpenGridResultStatusEnum.FAIL);
		}
		return response;
	}

	@Override
	public String getOpenGridGeoboundariesList() {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();

		myObject.setId("504bd6582c176712c214d370");
		myObject.setName("db.windygrid.findOne({'type' : '311'})");

		return myObject.getName();
	}

	@Override
	public String getOpenGridOneGeoboundary(String boundaryId) {
		//mock response for now, not implemented for Sprint 1
		DataObjects myObject = new DataObjects();
		if (boundaryId.equalsIgnoreCase("504bd6582c176712c214d370")) {
			myObject.setId("504bd6582c176712c214d370");
			myObject.setName("db.windygrid.findOne({'user1' : 'kaslkasfa'})");

			return myObject.getName();
		} else {
			myObject.setErrorMessage("groupId is not matched");

			return myObject.getErrorMessage();
		}
	}

	

}
