package org.opengrid.data;

import org.opengrid.service.OpenGridException;

public interface Updatable {
	void update(String id, String entity) throws OpenGridException;
}
