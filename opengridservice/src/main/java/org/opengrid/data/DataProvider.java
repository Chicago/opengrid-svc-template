package org.opengrid.data;

import org.opengrid.service.OpenGridException;

public interface DataProvider {
	String getId();
	String getData(String filter, int max, String sort) throws OpenGridException;	
	String getDescriptor() throws OpenGridException;	
}