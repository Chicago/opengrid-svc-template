package org.opengrid.security;

import javax.servlet.http.HttpServletRequest;

//we will be leveraging custom security validator instead of relying on Spring framework's for more flexibility
public interface RoleAccessValidator {
	
	boolean hasAccess(String token, HttpServletRequest request);
	boolean lookupAccessMap(String resourcePath, String method, String allowedList);
}
