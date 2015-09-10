package org.opengrid.exception;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.util.JSON;


public class ExceptionDetail {
    protected String errorMessage;

    protected String errorCode;

	public ExceptionDetail() {
		
	}
	
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }
    
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    public String toJsonString() throws JSONException {
    	
    	JSONObject err = new JSONObject();
    	JSONObject exceptionDetail = new JSONObject();
    	exceptionDetail.put("code", this.errorCode);
    	exceptionDetail.put("message", this.errorMessage);
    	
    	err.put("error", exceptionDetail);
    	return err.toString();
    }
	
}