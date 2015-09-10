package org.opengrid.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.opengrid.constants.Exceptions;
import org.opengrid.util.ServiceProperties;


public class ClientExceptionMapper implements ExceptionMapper<ClientErrorException> {
	@Override
    public Response toResponse(ClientErrorException e) {
		return Response.status(  
				Integer.parseInt( ServiceProperties.getProperties().getStringProperty("exception.statuscode") ) )
				.header("Content-Type", "application/json")
				.entity(getErrorJson(e))
				.build();
    }
	
	
	private String getErrorJson(ClientErrorException e) {
		JSONObject o = new JSONObject();
		JSONObject err = new JSONObject();
		
		err.put("code", Exceptions.ERR_CLIENT);
		err.put("message", e.getMessage());
		o.put("error", err);
		return o.toString();
	}
}