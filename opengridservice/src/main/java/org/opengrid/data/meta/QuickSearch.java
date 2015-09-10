package org.opengrid.data.meta;

public class QuickSearch {
	private boolean enable;
	private String matchingRegex;
	private String validationRegEx;
	private String validationErrorMessage;
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public String getMatchingRegex() {
		return matchingRegex;
	}
	
	public void setMatchingRegex(String matchingRegex) {
		this.matchingRegex = matchingRegex;
	}
	
	public String getValidationRegEx() {
		return validationRegEx;
	}
	
	public void setValidationRegEx(String validationRegEx) {
		this.validationRegEx = validationRegEx;
	}
	
	public String getValidationErrorMessage() {
		return validationErrorMessage;
	}
	
	public void setValidationErrorMessage(String validationErrorMessage) {
		this.validationErrorMessage = validationErrorMessage;
	}	
	
}
