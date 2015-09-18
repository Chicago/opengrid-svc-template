package org.opengrid.data.impl;

import org.bson.Document;
import org.opengrid.constants.Exceptions;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
	public void checkDependencies(MongoDatabase db, MongoCollection<Document> c, Document doc) throws ServiceException {
		//called before a delete is done
		//an exception is thrown, if dependencies exist
		//an alternative logic is to cascade the delete
		MongoCollection<Document> usersCollection = db.getCollection(org.opengrid.constants.DB.USERS_COLLECTION_NAME);
		
		String groupId = (String) doc.get("groupId");
		FindIterable<Document> o = usersCollection.find((BasicDBObject) JSON.parse("{\"groups\": {$in: [\"" + groupId + "\"] } }"));		
		if (o.first() != null) {
			throw new ServiceException("There are currently users under this group. Group cannot be deleted.");
		}
	}
}
