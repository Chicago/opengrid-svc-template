package org.opengrid.data.impl;

import org.opengrid.data.Retrievable;
import org.opengrid.data.MongoDBCollection;
import org.opengrid.data.Updatable;
import org.opengrid.service.OpenGridException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class QueryMongoDataProvider implements Retrievable, Updatable {

	@Override
	public String getId() {
		return org.opengrid.constants.AppObjects.QUERY;
	}

	@Override
	public String getData(String filter, int max, String sort) throws OpenGridException {
		//data is plain json (we'll need to standardize format across the board later)
		MongoDBCollection ds = new MongoDBCollection();
		DB db = ds.getConnection();
				
		try {
			DBCollection c = db.getCollection(org.opengrid.constants.DB.QUERY_COLLECTION_NAME);
			
			BasicDBObject q = null;
	    		    	
			if (filter !=null && filter.length() > 0) {
				q = (BasicDBObject) JSON.parse(filter);
			} else {
				q = (BasicDBObject) JSON.parse("{}"); //no filter
			}
	    	DBCursor cur = c.find(q);
	    	
	    	//return regular json object
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("[");
	    	int i=0;
	        while(cur.hasNext()) {
	        	if (i==max)
	        		break;
	        	
	        	DBObject doc = cur.next();
	        	if (i > 0)
	        		sb.append(",");
	        	sb.append(doc.toString());
	        	i++;
	        }
	        sb.append("]");
	        return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//bubble up
			//wrap and bubble up
			throw new OpenGridException(ex);
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
	}

	@Override
	public String getDescriptor() throws OpenGridException {
		// N/A for queries
		return "{}";
	}

	@Override
	public void update(String id, String entity) throws OpenGridException {
		//if id is null, this is a new entity being saved
		if (id==null) {
			addNewQuery(entity);
		} else {
			updateQuery(id, entity);
		}
		
	}

	private void updateQuery(String id, String entity) {
		MongoDBCollection ds = new MongoDBCollection();
		DB db = ds.getConnection();
				
		try {
			DBCollection c = db.getCollection(org.opengrid.constants.DB.QUERY_COLLECTION_NAME);
			BasicDBObject q = (BasicDBObject) JSON.parse("{\"_id\": {\"$eq\": " + id + "}}");
			BasicDBObject d = (BasicDBObject) JSON.parse(entity);
			
			//DBObject doc = c.findOne(q);
			//doc.append("$set", new BasicDBObject().append("clients", 110));
					
			c.update(q, d);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//bubble up
			//wrap and bubble up
			throw new OpenGridException(ex);
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
	}
	

	private void addNewQuery(String entity) {
		MongoDBCollection ds = new MongoDBCollection();
		DB db = ds.getConnection();
				
		try {
			DBCollection c = db.getCollection(org.opengrid.constants.DB.QUERY_COLLECTION_NAME);
			BasicDBObject o = (BasicDBObject) JSON.parse(entity);
			
			c.insert((DBObject)o.get("o"));
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//bubble up
			//wrap and bubble up
			throw new OpenGridException(ex);
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
	}

}
