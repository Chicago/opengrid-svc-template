package org.opengrid.data.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.opengrid.constants.Exceptions;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.data.Retrievable;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class WeatherMongoDataProvider implements Retrievable {

	@Override
	public String getData(String filter, int max, String sort) throws ServiceException {
		MongoDBHelper ds = new MongoDBHelper();
		DB db = ds.getConnection();
				
		try {
			DBCollection c = db.getCollection(org.opengrid.constants.DB.DATA_COLLECTION_NAME);
			
			BasicDBObject q = new BasicDBObject();
	    		    	
			if (filter !=null && filter.length() > 0) {
				BasicDBObject o = (BasicDBObject) JSON.parse(filter);
				o.append("type", new BasicDBObject("$eq", "weather"));
				q = o;
			} else
				q = new BasicDBObject("type", new BasicDBObject("$eq", "weather"));

	    	DBCursor cur = c.find(q);
	    	if (sort !=null && sort.length() > 0) {
	    		BasicDBObject orderBy = (BasicDBObject) JSON.parse(sort);
	    		cur.sort(orderBy);
	    	}
	    	
	    	
	    	//return geoJson object as part of our mock implementation
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("{ \"type\" : \"FeatureCollection\", \"features\" : [");
	    	
	    	int i=0;
	        while(cur.hasNext()) {
	        	if (i==max)
	        		break;
	        	
	        	DBObject doc = cur.next();
	        	if (i > 0)
	        		sb.append(",");
	        	sb.append(getFeature(doc));
	        	i++;
	        	
	        	//temp limit
	        	//if (i==1000) 
	        	//	break;
	        }
	        sb.append("],");
	        sb.append(getMeta());
	        sb.append("}");
	        
	        return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//wrap and bubble up
			throw ExceptionUtil.getException(Exceptions.ERR_DB, ex.getMessage());
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
	}

	private String getFeature(DBObject doc) {
		String s = "{\"type\": \"Feature\", \"properties\": ";
		
		//discard 'type' attribute as it's for our internal use only, not part of the public schema
		doc.removeField("type");
		
		//convert epoch to formatted date
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy h:m a");
		
		doc.put("date", f.format( new Date((Long)doc.get("date"))));
		s += doc.toString();
		
		String lng = doc.get("long").toString();
		String lat = (String) doc.get("lat").toString();
		
		s +=", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + lng + "," + lat + "]}, \"autoPopup\": false }";
		return s;		
	}

	private String getMeta() {
		//return default descriptor, can be overridden by user preferences later
		//return "\"meta\": { \"view\": { \"id\": \"twitter\", \"displayName\": \"Twitter\", \"options\": { \"rendition\": { \"icon\":\"default\", \"color\": \"#001F7A\", \"fillColor\": \"#00FFFF\", \"opacity\":85, \"size\":6 } }, \"columns\": [ {\"id\":\"_id\", \"displayName\":\"ID\", \"dataType\":\"string\", \"filter\":false, \"popup\":false, \"list\":false}, {\"id\":\"date\", \"displayName\":\"Date\", \"dataType\":\"date\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":1}, {\"id\":\"screenName\", \"displayName\":\"Screen Name\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":2}, {\"id\":\"text\", \"displayName\":\"Text\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":3}, {\"id\":\"city\", \"displayName\":\"City\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":4}, {\"id\":\"bio\", \"displayName\":\"Bio\", \"dataType\":\"string\",\"sortOrder\":5}, {\"id\":\"hashtags\", \"displayName\":\"Hashtags\", \"dataType\":\"string\", \"sortOrder\":6}, {\"id\":\"lat\", \"displayName\":\"Latitude\", \"dataType\":\"float\", \"list\":true, \"sortOrder\":7}, {\"id\":\"long\", \"displayName\":\"Longitude\", \"dataType\":\"float\", \"list\":true, \"sortOrder\":8} ] } }";
		return "\"meta\": { \"view\": " + this.getDescriptor() + " }";
	}

	@Override
	public String getId() {
		return org.opengrid.constants.DataTypes.WEATHER;
	}

	@Override
	public String getDescriptor() throws ServiceException {
		MongoDBHelper ds = new MongoDBHelper();
		DB db = ds.getConnection();
				
		DBCollection c = db.getCollection(org.opengrid.constants.DB.META_COLLECTION_NAME);

		//we only have one doc in our meta collection
		DBObject doc = c.findOne();
		BasicDBList a = (BasicDBList) doc.get("datasets");
		
		//loop through the dataset descriptors
		for (Object o: a) {
			if ( ((DBObject) o).get("id").toString().equalsIgnoreCase(this.getId()) ) {
				
				//this is our descriptor, return
				return ((DBObject)o).toString();
			}
		}
		//wrap and bubble up
		throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "Cannot find dataset descriptor from meta store for dataset '" + this.getId() + "'.");
	}

}
