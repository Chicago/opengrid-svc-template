package org.opengrid.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.jetty.util.ajax.JSON;

@XmlRootElement
public class DataObjects {
	private String id;
	private String name = null;
	private String errorMessage = null;
	
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
