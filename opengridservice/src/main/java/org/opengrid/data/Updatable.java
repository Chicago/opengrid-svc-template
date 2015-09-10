package org.opengrid.data;

import org.opengrid.exception.ServiceException;

public interface Updatable {
	String update(String id, String entity) throws ServiceException;
	void delete(String id) throws ServiceException;
}