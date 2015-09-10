package org.opengrid.data.meta;

public class DataSource {
	private String sourceName;
	private String dbHostName;
	private String dbInstanceName;
	private int dbPort;
	private String dbUser;
	private String dbPassword;
	private String baseFilter;
	private String additionalConnectionParams;

	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	public String getDbHostName() {
		return dbHostName;
	}
	
	public void setDbHostName(String dbHostName) {
		this.dbHostName = dbHostName;
	}
	
	public String getDbInstanceName() {
		return dbInstanceName;
	}
	
	public void setDbInstanceName(String dbInstanceName) {
		this.dbInstanceName = dbInstanceName;
	}
	
	public int getDbPort() {
		return dbPort;
	}
	
	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}
	
	public String getDbUser() {
		return dbUser;
	}
	
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	public String getDbPassword() {
		return dbPassword;
	}
	
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	public String getBaseFilter() {
		return baseFilter;
	}
	
	public void setBaseFilter(String baseFilter) {
		this.baseFilter = baseFilter;
	}
	
	public String getAdditionalConnectionParams() {
		return additionalConnectionParams;
	}
	
	public void setAdditionalConnectionParams(String additionalConnectionParams) {
		this.additionalConnectionParams = additionalConnectionParams;
	}
	
	
}
