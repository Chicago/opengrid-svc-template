package org.opengrid.security.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.opengrid.security.RoleAccessValidator;
import org.opengrid.security.TokenAuthenticationService;
import org.opengrid.security.TokenHandler;
import org.opengrid.security.impl.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class MongoTokenAuthenticationService implements TokenAuthenticationService {
	private static final Logger log = Logger.getLogger(MongoTokenAuthenticationService.class);
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
 
    private final TokenHandler tokenHandler;
    
    private RoleAccessValidator roleAccessValidator = new JwtRoleAccessValidator();
    
    public MongoTokenAuthenticationService() {
        tokenHandler = new TokenHandler();
    }


	public void addAuthentication(HttpServletResponse response, Authentication authentication) {
        final org.opengrid.security.impl.User user = (org.opengrid.security.impl.User) authentication.getDetails();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
    }
 
    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
        	try {
                final UserDetails user = tokenHandler.parseUserFromToken(token);
                if (user != null) {
                	
                	//use our custom resource access validator
                	if (!roleAccessValidator.hasAccess(token, request) ) {
                		//by returning NULL, a 403 is returned to the client
                		return null; 
                	} else {
                		return new UserAuthentication(user);
                	}                
                }        		
        	} catch (Exception ex) {
        		log.error(ex);
        		        		
        		//we need to catch this to prevent generating an HTTP 500
        		//returning a NULL will trigger an authentication failure
        		return null;
        	}
        }
        return null;
    }
    
    public void renewAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	String token = request.getHeader(AUTH_HEADER_NAME);
    	 if (token != null) {
             String newToken = tokenHandler.renewExpirationTime(token);
             
             //replace old token with new one
             response.setHeader(AUTH_HEADER_NAME, newToken);
         }
    }


	@Override
	public void setKey(String key) {
		tokenHandler.setSecret(key);		
	}
	
	
	public String getToken(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER_NAME);
	}
}
