package org.opengrid.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.json.JSONObject;
import org.opengrid.constants.Exceptions;
import org.opengrid.util.ServiceProperties;
 
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
    	return Response.status(  
				Integer.parseInt( ServiceProperties.getProperties().getStringProperty("exception.statuscode") ) )
				.header("Content-Type", "application/json")
				.entity(getErrorJson(e))
				.build();
    	
        //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").entity(new String("DATA ERROR")).build();
    }

	private String getErrorJson(Throwable e) {
		JSONObject o = new JSONObject();
		JSONObject err = new JSONObject();
		
		err.put("code", Exceptions.ERR_SYSTEM);
		err.put("message", e.getMessage());
		o.put("error", err);
		return o.toString();
	}
}