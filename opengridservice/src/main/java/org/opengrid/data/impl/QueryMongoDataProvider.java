package org.opengrid.data.impl;


public class QueryMongoDataProvider extends UpdatableMongoDataProvider {

	public QueryMongoDataProvider() {
		super(org.opengrid.constants.DB.QUERY_COLLECTION_NAME);
	}

	@Override
	public String getId() {
		return org.opengrid.constants.AppObjects.QUERY;
	}	

}