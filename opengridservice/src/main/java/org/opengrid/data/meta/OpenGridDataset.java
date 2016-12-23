package org.opengrid.data.meta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenGridDataset {
	private String id;
	private String displayName;
	private DatasetOptions options; 
	private DataSource dataSource;
	private QuickSearch quickSearch;
	private List<OpenGridColumn> columns;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public DatasetOptions getOptions() {
		return options;
	}

	public void setOptions(DatasetOptions options) {
		this.options = options;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public QuickSearch getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(QuickSearch quickSearch) {
		this.quickSearch = quickSearch;
	}

	public List<OpenGridColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<OpenGridColumn> columns) {
		this.columns = columns;
	}

	
	
	public String toString() {
		ObjectMapper om = new ObjectMapper();
		
		try {
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"error\": \"Unable to convert object to JSON\" }";
	}
}
