package org.opengrid.data;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class QueryOptions {
	@JsonRawValue
	private String geoFilter;
	
	public String getGeoFilter() {
		return geoFilter;
	}

	public void setGeoFilter( String geoFilter) {
		this.geoFilter = geoFilter;
	}
	
	
}
