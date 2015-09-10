package org.opengrid.data.meta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenGridMeta  {
	private List<OpenGridDataset> datasets;
	private List<OpenGridResourceSecurity> resourceSecurity;	
	
	public List<OpenGridDataset> getDatasets() {
		return datasets;
	}
	
	
	public void setDatasets(List<OpenGridDataset> datasets) {
		this.datasets = datasets;
	}
	
	
	public List<OpenGridResourceSecurity> getResourceSecurity() {
		return resourceSecurity;
	}
	
	
	public void setResourceSecurity(List<OpenGridResourceSecurity> resourceSecurity) {
		this.resourceSecurity = resourceSecurity;
	}
	
	
	//helper methods
	
	//returns dataset descriptor given ds id
	public OpenGridDataset getDataset(String dsId) {
		if (datasets != null) {
			for (OpenGridDataset o: datasets) {
				if (o.getId().equalsIgnoreCase(dsId)) {
					return o;
				}
			}
		}
		return null;		
	}

}