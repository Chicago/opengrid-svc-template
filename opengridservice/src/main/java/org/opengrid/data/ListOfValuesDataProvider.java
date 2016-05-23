package org.opengrid.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.opengrid.constants.DB;
import org.opengrid.constants.Exceptions;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;


public class ListOfValuesDataProvider {
	
	
	public List<KeyValuePair> getList(String listId) {
		//basic schema is 
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
				
		try {
			MongoCollection<Document> c = db.getCollection(DB.LOV_COLLECTION_NAME);
			
			BasicDBObject q = (BasicDBObject) JSON.parse("{ \"listId\": \"" + listId  + "\"}");
			
			//now use limit to limit result sets
	    	FindIterable<Document> cur = c.find(q).limit(1);
	    	if (cur.iterator().hasNext()) {
	    		Document d = cur.first();
	    		@SuppressWarnings("unchecked")
				List<Document> a = (List<Document>) d.get("keyValues");
	    		return getKeyValuePairs(a);
	    	} else {
	    		throw new ServiceException("Cannot find list of values with List Id '" + listId + "'.");
	    	}						
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//wrap and bubble up
			throw ExceptionUtil.getException(Exceptions.ERR_DB, ex.getMessage());
		} finally {
			ds.closeConnection();
		}
	}

	private List<KeyValuePair> getKeyValuePairs(List<Document> a) {
		List<KeyValuePair> l = new ArrayList<KeyValuePair>();
		
		//perform transformation
		for (Document d: a) {
			l.add(
				new KeyValuePair (
					d.getString("key"),
					d.getString("value")
				)
			);
		}
		return l;
	}
}