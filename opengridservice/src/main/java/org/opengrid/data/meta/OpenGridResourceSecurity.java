package org.opengrid.data.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenGridResourceSecurity {
	private String urlPattern;
	private String httpMethod;
	private String requiredAuthorities;
	
	
	public String getUrlPattern() {
		return urlPattern;
	}
	
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	
	public String getHttpMethod() {
		return httpMethod;
	}
	
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	public String getRequiredAuthorities() {
		return requiredAuthorities;
	}
	
	public void setRequiredAuthorities(String requiredAuthorities) {
		this.requiredAuthorities = requiredAuthorities;
	}
}
