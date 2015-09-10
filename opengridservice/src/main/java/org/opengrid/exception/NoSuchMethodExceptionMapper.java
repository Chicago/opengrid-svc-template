package org.opengrid.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.json.JSONObject;
import org.opengrid.constants.Exceptions;
import org.opengrid.util.ServiceProperties;


public class NoSuchMethodExceptionMapper implements ExceptionMapper<NoSuchMethodError> {
	@Override
    public Response toResponse(NoSuchMethodError e) {
		return Response.status(  
				Integer.parseInt( ServiceProperties.getProperties().getStringProperty("exception.statuscode") ) )
				.header("Content-Type", "application/json")
				.entity(getErrorJson(e))
				.build();
    }
	
	
	private String getErrorJson(NoSuchMethodError e) {
		JSONObject o = new JSONObject();
		JSONObject err = new JSONObject();
		
		err.put("code", Exceptions.ERR_CLIENT);
		err.put("message", "A valid resource cannot be found. " + e.getMessage());
		o.put("error", err);
		return o.toString();
	}
}