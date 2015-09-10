package org.opengrid.data.impl;

import org.opengrid.constants.Exceptions;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class GroupMongoDataProvider extends UpdatableMongoDataProvider {

	public GroupMongoDataProvider() {
		super(org.opengrid.constants.DB.GROUPS_COLLECTION_NAME);
	}

	@Override
	public String getId() {
		return org.opengrid.constants.AppObjects.GROUP;
	}	

	@Override
	public void checkDependencies(DB db, DBCollection c, DBObject doc) throws ServiceException {
		//called before a delete is done
		//an exception is thrown, if dependencies exist
		//an alternative logic is to cascade the delete
		DBCollection usersCollection = db.getCollection(org.opengrid.constants.DB.USERS_COLLECTION_NAME);
		
		String groupId = (String) doc.get("groupId");
		DBObject o = usersCollection.findOne((BasicDBObject) JSON.parse("{\"groups\": {$in: [\"" + groupId + "\"] } }"));		
		if (o != null) {
			throw new ServiceException("There are currently users under this group. Group cannot be deleted.");
		}
	}
}
