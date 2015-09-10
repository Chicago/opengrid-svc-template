package org.opengrid.security;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.opengrid.security.impl.User;
import org.opengrid.util.ServiceProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public final class TokenHandler {
	 
    private String secret;
    
    //@Resource(name="userDetailsService")
    private UserDetailsService userService;
 
    public TokenHandler() {
        //this.secret = secret;
        
        //TODO: look at why this is not being injected fomr app context
        this.userService = new org.opengrid.security.impl.MongoUserService();
    }
    
    public TokenHandler(String secret) {
        this.secret = secret;
        
        //TODO: look at why this is not being injected fomr app context
        this.userService = new org.opengrid.security.impl.MongoUserService();
    }
    
    public void setSecret(String secret) {
    	this.secret = secret;
    }
    
    public String getSecret() {
    	return this.secret;
    }
 
    public UserDetails parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }
 
 
    
    private String getAllRoles(String prefix, List<String> list) {
    	String s="";
    	for (String v : list) {
    		if (v.startsWith(prefix)) {
        		if (s.length() > 0) {
        			s += ",";
        		}
        		
        		//value of authority string is GROUP!<groupName> or RESOURCE!<resourceName>
        		String a[] = v.split("!");
        		s += a[1];    			
    		}
    	}
    	return s;
    }
    
    
    
    
    public String createTokenForUser(User user) {
    	List<String> l = user.getAuthoritiesAsStringList();
        return Jwts.builder()
                .setSubject(user.getUsername())               
                
                //expire in 4 hours by default (we can make this configurable), then renew expiration everytime there's new activity
                //TODO: make token expiration (in hours) configurable
                .setExpiration(getDatePlusHour(new Date(), 4))
                
                //jti the same as username
                .setId(user.getUsername())
                .claim("roles", getAllRoles( org.opengrid.constants.Security.AUTH_PREFIX_GROUP, l))
                
                //consolidated list of data types this user has access to
                //.claim("resources", getAllRoles(user.getAccessibleResources()))
                .claim("resources", getAllRoles( org.opengrid.constants.Security.AUTH_PREFIX_RESOURCE, l))
                .claim("fname", user.getFirstName())
                .claim("lname", user.getLastName())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    private Date getDatePlusHour(Date dt, int addHours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.HOUR_OF_DAY, addHours);
		return cal.getTime();
    }
    
    
    public Claims getClaims(String token) { 
    	return Jwts.parser()
    	        .setSigningKey(secret)
    	        .parseClaimsJws(token).getBody();
    }
    
    
    public String renewExpirationTime(String token) {
    	Claims c = this.getClaims(token);
        
    	//create clone with updated expiration time
    	return Jwts.builder()
                .setSubject(c.getSubject())               
                
                //expire in 4 hours by default (we can make this configurable), then renew expiration everytime there's new activity
                //TODO: make token expiration (in hours) configurable
                .setExpiration(getDatePlusHour(new Date(), Integer.parseInt(ServiceProperties.getProperties().getStringProperty("auth.expiration"))))
                
                //jti the same as username
                .setId(c.getSubject())
                .claim("roles", c.get("roles"))
                .claim("resources", c.get("resources"))
                .claim("fname", c.get("fname"))
                .claim("lname", c.get("lname"))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
