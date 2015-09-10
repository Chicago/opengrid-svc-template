package org.opengrid.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;


public interface TokenAuthenticationService {
	 
	void addAuthentication(HttpServletResponse response, Authentication authentication);
    Authentication getAuthentication(HttpServletRequest request);    
    void renewAuthentication(HttpServletRequest request, HttpServletResponse response);
    void setKey(String key);
    String getToken(HttpServletRequest request);
}
