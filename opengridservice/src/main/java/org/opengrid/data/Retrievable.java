package org.opengrid.data;

import org.opengrid.exception.ServiceException;


public interface Retrievable {
	String getId();
	String getData(String filter, int max, String sort) throws ServiceException;	
	String getDescriptor() throws ServiceException;	
}