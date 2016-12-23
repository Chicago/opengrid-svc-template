package org.opengrid.data.meta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetOptions {
	private Rendition rendition;
	
	//comma-delimited string of column names designating what columns to use for Latitude and Longitude
	private String latLong; 
	
	//property that represents creation timestamp of document
	private String creationTimestamp;
	
	//property to contain default sort info
	private String defaultSort;
	
	//field name of the location field to use with $geoWithin operator, if supported 
	private String locationField;
	
	private List<SupportedOperators> supportedOperators;

	public Rendition getRendition() {
		return rendition;
	}

	public void setRendition(Rendition rendition) {
		this.rendition = rendition;
	}

	public String getLatLong() {
		return latLong;
	}

	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}
	
	public String getLat() {
		return (this.latLong == null) ? "lat" : this.latLong.split(",")[0];
	}
		
	
	public String getLong() {
		return (this.latLong == null) ? "long" : this.latLong.split(",")[1];
	}

	public String getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(String creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getDefaultSort() {
		return defaultSort;
	}

	public void setDefaultSort(String defaultSort) {
		this.defaultSort = defaultSort;
	}

	public String getLocationField() {
		return locationField;
	}

	public void setLocationField(String locationField) {
		this.locationField = locationField;
	}

	public List<SupportedOperators> getSupportedOperators() {
		return supportedOperators;
	}

	public void setSupportedOperators(List<SupportedOperators> supportedOperators) {
		this.supportedOperators = supportedOperators;
	}
}
