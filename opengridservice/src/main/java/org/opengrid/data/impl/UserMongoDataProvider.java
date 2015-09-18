package org.opengrid.data.impl;

import java.util.ArrayList;

import org.bson.Document;
import org.opengrid.exception.ServiceException;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class UserMongoDataProvider extends UpdatableMongoDataProvider {

	public UserMongoDataProvider() {
		super(org.opengrid.constants.DB.USERS_COLLECTION_NAME);
	}

	@Override
	public String getId() {
		return org.opengrid.constants.AppObjects.USER;
	}	
	
	@Override
	public void checkDependencies(MongoDatabase db, MongoCollection<Document> c, Document doc) throws ServiceException {
		//cascade the delete
		//clean up any query with this user id
		
		String userId = (String) doc.get("userId");
		wipeUserQueries(db, userId);
	}

	private void wipeUserQueries(MongoDatabase db, String userId) {
		MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.QUERY_COLLECTION_NAME);
		
 		BasicDBObject q = (BasicDBObject) JSON.parse("{$or: [" +
 				"{\"owner\": \"" + userId + "\"}, " +
 				"{ \"sharedWith.users\": {$in : [\"" + userId + "\"] } }" +
 				"] }");
    	FindIterable<Document> cur = c.find(q);
    	MongoCursor<Document> it = cur.iterator(); 
    	
        while(it.hasNext()) {        	
        	Document doc = it.next();
        	if ( ((String) doc.get("owner")).equals(userId) ) {
        		//delete this as we own it
        		c.deleteOne(doc);
        	} else {
        		//userId is on the shared list, remove the user's id then update doc
        		BasicDBObject sh = (BasicDBObject) doc.get("sharedWith");
        		if (sh != null) {
        			ArrayList a = (ArrayList) sh.get("users");
            		if (a !=null) {
                		//loop through the users
                		for (Object o: a) {
                			if ( ((String) o).equalsIgnoreCase(userId) ) {
                				a.remove(o);
                				break;
                			}
                		}
                		sh.put("users", a);
                		doc.put("sharedWith", sh);
                		
                		BasicDBObject toUpdate = new BasicDBObject("_id", new BasicDBObject("$eq", doc.get("_id")));
                		c.updateOne(toUpdate, doc);
            		}
        		}
        	}        	
        }
  	}

}
