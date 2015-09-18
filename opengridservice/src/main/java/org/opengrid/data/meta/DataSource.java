package org.opengrid.data.meta;

public class DataSource {
	private String sourceName;
	private String baseFilter;
	private String dbConnectionString;

	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	
	
	public String getBaseFilter() {
		return baseFilter;
	}
	
	public void setBaseFilter(String baseFilter) {
		this.baseFilter = baseFilter;
	}

	public String getDbConnectionString() {
		return dbConnectionString;
	}

	public void setDbConnectionString(String dbConnectionString) {
		this.dbConnectionString = dbConnectionString;
	}
	
		
	
}
