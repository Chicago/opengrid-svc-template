package org.opengrid.data.impl;

import org.bson.Document;
import org.opengrid.constants.Exceptions;
import org.opengrid.data.Retrievable;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.data.Updatable;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;


public class UpdatableMongoDataProvider implements Retrievable, Updatable {
	private String collectionName = null;
	

	public UpdatableMongoDataProvider(String collectionName) {
		this.collectionName = collectionName;
	}
	
	 public String getId() {
		return "";
	}

	public String getData(String filter, int max, String sort) throws ServiceException {
		//data is plain json (we'll need to standardize format across the board later)
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		try {
			MongoCollection<Document> c = db.getCollection(this.collectionName);
			
			BasicDBObject q = null;
	    		    	
			if (filter !=null && filter.length() > 0) {
				q = (BasicDBObject) JSON.parse(filter);
			} else {
				q = (BasicDBObject) JSON.parse("{}"); //no filter
			}
	    	FindIterable<Document> cur = c.find(q);
	    	MongoCursor<Document> it = cur.iterator();
	    	
	    	//return regular json object, not geoJson
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("[");
	    	int i=0;
	        while(it.hasNext()) {
	        	if (i==max)
	        		break;
	        	
	        	Document doc = it.next();
	        	if (i > 0)
	        		sb.append(",");
	        	sb.append(doc.toJson());
	        	i++;
	        }
	        sb.append("]");
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


	public String getDescriptor() {
		// N/A for updatable objects
		return org.opengrid.constants.DB.EMPTY_OBJECT;
	}


	public String update(String id, String entity) throws ServiceException {
		//for testing
		//throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, null);
		
		//if id is null, this is a new entity being saved
		if (id==null) {
			return addNewObject(entity);
		} else {
			return updateObject(id, entity);
		}		
	}

	//entity => {id:<id>, o:<object>}
	private String updateObject(String id, String entity) throws ServiceException  {
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		try {
			MongoCollection<Document> c = db.getCollection(this.collectionName);
			BasicDBObject q = (BasicDBObject) JSON.parse("{\"_id\": {\"$eq\": " + id + "}}");
			Document d = Document.parse(entity);
						
			Document doc = d.get("o", Document.class);
			c.replaceOne(q, doc);
			
			//return object on success, to be consistent with add method
			return doc.toJson();
			
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
	

	//entity => {id:<id>, o:<object>}
	private String addNewObject(String entity) throws ServiceException  {
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		try {
			MongoCollection<Document> c = db.getCollection(this.collectionName);
			Document o = Document.parse(entity);
			
			Document doc = o.get("o", Document.class); 			
			c.insertOne(doc);
			
			//return object with _id already populated
			return doc.toJson();
			
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


	public void delete(String id) throws ServiceException {
		
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		try {
			MongoCollection<Document> c = db.getCollection(this.collectionName);
			String filter = "{\"_id\": {\"$eq\": " + id + "}}";
			BasicDBObject q = (BasicDBObject) JSON.parse(filter);			
			FindIterable<Document> docs = c.find(q);
			Document doc = docs.first();
			
			if (doc == null) {
				throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "No object exists with id '" + id + "'");
			}
			checkDependencies(db, c, doc);
			c.deleteOne(doc);			
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
	
	public void checkDependencies(MongoDatabase db, MongoCollection<Document> c, Document doc) throws ServiceException {
		//do nothing, defer to inherited classes if any checks need to be made
	}
}
