package org.opengrid.service;

import java.util.List;


public class OpenGridResult {
	
    private int statusCode;
    private String type;
    private String statusMessage;
    private List<?> results;
    
    public OpenGridResult() {
		super();
	}
    public OpenGridResult(OpenGridResultStatusEnum x) {
		super();
		setStatusType(x);
	}
	public void setStatusType(OpenGridResultStatusEnum x) {
    	this.statusCode = x.getCode();
    	this.statusMessage = x.getStatus();
    	this.type = x.name();
    }
	public String getType() {
		return type;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public List<?> getResults() {
		return results;
	}
	public void setResults(List<?> results) {
		this.results = results;
	}
	
    
}
