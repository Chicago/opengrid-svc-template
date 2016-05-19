package org.opengrid.data;

public class ServiceCapabilities {
	//only one attribute supported right now
	private boolean geoSpatialFiltering;

	public boolean isGeoSpatialFiltering() {
		return geoSpatialFiltering;
	}

	public void setGeoSpatialFiltering(boolean geoSpatialFiltering) {
		this.geoSpatialFiltering = geoSpatialFiltering;
	}
	
	
}
