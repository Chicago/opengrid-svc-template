package org.opengrid.data.impl;

import org.opengrid.data.Retrievable;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.FileUtil;

public class TwitterFileDataProvider implements Retrievable {

	@Override
	public String getData(String filter, int max, String sort) throws ServiceException {
		//not supporting filtering right now on file-based impl
		return FileUtil.getJsonFileContents("json/twitter_data.json");
	}


	@Override
	public String getDescriptor() throws ServiceException {
		return FileUtil.getJsonFileContents("json/twitter_descriptor.json");
	}
	

	@Override
	public String getId() {
		return org.opengrid.constants.DataTypes.TWITTER;
	}

	
	
	
}
