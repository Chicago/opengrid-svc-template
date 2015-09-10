package org.opengrid.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.opengrid.constants.Exceptions;
import org.opengrid.util.ServiceProperties;


public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
	@Override
    public Response toResponse(ServiceException e) {
		return Response.status(  
				Integer.parseInt( ServiceProperties.getProperties().getStringProperty("exception.statuscode") ) )
				.header("Content-Type", "application/json")
				.entity(e.getExceptionDetail().toJsonString())
				.build();
    }
	
}