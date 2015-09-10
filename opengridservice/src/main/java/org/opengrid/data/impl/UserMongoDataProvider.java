package org.opengrid.data.impl;

import org.opengrid.exception.ServiceException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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
	public void checkDependencies(DB db, DBCollection c, DBObject doc) throws ServiceException {
		//cascade the delete
		//clean up any query with this user id
		
		String userId = (String) doc.get("userId");
		wipeUserQueries(db, userId);
	}

	private void wipeUserQueries(DB db, String userId) {
		DBCollection c = db.getCollection(org.opengrid.constants.DB.QUERY_COLLECTION_NAME);
		
 		BasicDBObject q = (BasicDBObject) JSON.parse("{$or: [" +
 				"{\"owner\": \"" + userId + "\"}, " +
 				"{ \"sharedWith.users\": {$in : [\"" + userId + "\"] } }" +
 				"] }");
    	DBCursor cur = c.find(q);
    	
        while(cur.hasNext()) {        	
        	DBObject doc = cur.next();
        	if ( ((String) doc.get("owner")).equals(userId) ) {
        		//delete this as we own it
        		c.remove(doc);
        	} else {
        		//userId is on the shared list, remove the user's id then update doc
        		BasicDBObject sh = (BasicDBObject) doc.get("sharedWith");
        		if (sh != null) {
        			BasicDBList a = (BasicDBList) sh.get("users");
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
                		c.update(toUpdate, doc);
            		}
        		}
        	}        	
        }
  	}

}
