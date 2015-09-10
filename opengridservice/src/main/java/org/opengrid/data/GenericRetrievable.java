package org.opengrid.data;

import java.io.IOException;

import org.opengrid.data.meta.OpenGridDataset;
import org.opengrid.exception.ServiceException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface GenericRetrievable {
	String getData(String dataSetId, String metaCollectionName, String filter, int max, String sort) throws ServiceException;	
	OpenGridDataset getDescriptor(String metaCollectionName, String dataSetId) throws ServiceException, JsonParseException, JsonMappingException, IOException;	
	OpenGridDataset getDescriptorInternal(String metaCollectionName, String dataSetId, boolean removePrivates) throws ServiceException, JsonParseException, JsonMappingException, IOException;
}