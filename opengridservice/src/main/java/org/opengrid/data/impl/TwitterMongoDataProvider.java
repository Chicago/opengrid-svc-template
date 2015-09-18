package org.opengrid.data.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import org.opengrid.constants.Exceptions;
import org.opengrid.data.Retrievable;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class TwitterMongoDataProvider implements Retrievable {

	@Override
	public String getData(String filter, int max, String sort) throws ServiceException {
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		try {
			MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.DATA_COLLECTION_NAME);
			
			BasicDBObject q = new BasicDBObject();
	    		    	
			if (filter !=null && filter.length() > 0) {
				BasicDBObject o = (BasicDBObject) JSON.parse(filter);
				o.append("type", new BasicDBObject("$eq", "tweet"));
				q = o;
			} else
				q = new BasicDBObject("type", new BasicDBObject("$eq", "tweet"));

	    	FindIterable<Document> cur = c.find(q);
	    	MongoCursor<Document> it = cur.iterator();
	    	
	    	//return geoJson object as part of our mock implementation
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("{ \"type\" : \"FeatureCollection\", \"features\" : [");
	    	
	    	int i=0;
	        while(it.hasNext()) {
	        	if (i==max)
	        		break;
	        	
	        	Document doc = it.next();
	        	if (i > 0)
	        		sb.append(",");
	        	sb.append(getFeature(doc));
	        	i++;
	        	
	        	//temp limit
	        	//if (i==500) 
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

	private String getFeature(Document doc) {
		String s = "{\"type\": \"Feature\", \"properties\": ";
		
		//discard 'type' attribute as it's for our internal use only, not part of the public schema
		doc.remove("type");
		
		//convert epoch to formatted date
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy h:m a");
		
		doc.put("date", f.format( new Date((Long)doc.get("date"))));
		s += doc.toJson();
		
		String lng = doc.get("long").toString();
		String lat = (String) doc.get("lat").toString();
		
		s +=", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + lng + "," + lat + "]}, \"autoPopup\": false }";
		return s;		
	}

	private String getMeta() {
		//return default descriptor, can be overridden by user preferences later
		//return "\"meta\": { \"view\": { \"id\": \"twitter\", \"displayName\": \"Twitter\", \"options\": { \"rendition\": { \"icon\":\"default\", \"color\": \"#001F7A\", \"fillColor\": \"#00FFFF\", \"opacity\":85, \"size\":6 } }, \"columns\": [ {\"id\":\"_id\", \"displayName\":\"ID\", \"dataType\":\"string\", \"filter\":false, \"popup\":false, \"list\":false}, {\"id\":\"date\", \"displayName\":\"Date\", \"dataType\":\"date\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":1}, {\"id\":\"screenName\", \"displayName\":\"Screen Name\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":2}, {\"id\":\"text\", \"displayName\":\"Text\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":3}, {\"id\":\"city\", \"displayName\":\"City\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":4}, {\"id\":\"bio\", \"displayName\":\"Bio\", \"dataType\":\"string\",\"sortOrder\":5}, {\"id\":\"hashtags\", \"displayName\":\"Hashtags\", \"dataType\":\"string\", \"sortOrder\":6}, {\"id\":\"lat\", \"displayName\":\"Latitude\", \"dataType\":\"float\", \"list\":true, \"sortOrder\":7}, {\"id\":\"long\", \"displayName\":\"Longitude\", \"dataType\":\"float\", \"list\":true, \"sortOrder\":8} ] } }";
		return "\"meta\": { \"view\": " + getDescriptor() + " }";
	}

	
	@Override
	public String getDescriptor() throws ServiceException {
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.META_COLLECTION_NAME);

		//we only have one doc in our meta collection
		FindIterable<Document> docs = c.find();
		Document doc = docs.first();
		ArrayList a = (ArrayList) doc.get("datasets");
		
		//loop through the dataset descriptors
		for (Object o: a) {
			if ( ((Document) o).get("id").toString().equalsIgnoreCase(this.getId()) ) {
				
				//this is our descriptor, return
				return ((Document)o).toJson();
			}
		}
		//wrap and bubble up
		throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "Cannot find dataset descriptor from meta store for dataset '" + this.getId() + "'.");
	}

	
	@Override
	public String getId() {
		return org.opengrid.constants.DataTypes.TWITTER;	
	}

}
