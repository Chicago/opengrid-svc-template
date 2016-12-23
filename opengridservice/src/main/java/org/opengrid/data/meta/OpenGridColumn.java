package org.opengrid.data.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenGridColumn {
	private String id;
	private String displayName;
	private String dataType;
	private String dataSource;
	
	private boolean filter;
	private boolean popup;
	private boolean list;
	private boolean groupBy;
	private boolean quickSearch;
	private String listOfValuesId;
	
	private int sortOrder;

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public boolean isFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public boolean isList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public boolean isGroupBy() {
		return groupBy;
	}

	public void setGroupBy(boolean groupBy) {
		this.groupBy = groupBy;
	}

	public boolean isQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(boolean quickSearch) {
		this.quickSearch = quickSearch;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getListOfValuesId() {
		return listOfValuesId;
	}

	public void setListOfValuesId(String listOfValuesId) {
		this.listOfValuesId = listOfValuesId;
	}
	
}
