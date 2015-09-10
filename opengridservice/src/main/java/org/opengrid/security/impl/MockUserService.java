package org.opengrid.security.impl;

import java.util.HashMap;

import javax.ws.rs.WebApplicationException;

import org.opengrid.security.OpenGridUserRole;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class MockUserService implements org.springframework.security.core.userdetails.UserDetailsService {
	 
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    private final HashMap<String, User> userMap = new HashMap<String, User>();
 
    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
    	//look up from our DB later
        //final User user = userMap.get(username);
    	final User user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        	//throw new WebApplicationException(401);
        	//throw new AccessDeniedException("You are not an authorized user");
        }
        detailsChecker.check(user);
        return user;
    }
 
    private User getUser(String username) {
    	//return null;
    	//stubbed, read from repo later
    	User u = new User();
    	u.setUsername(username);
    	u.setFirstName("Peter");
    	u.setLastName("Jenkins");
    	u.grantRole(new OpenGridUserRole("opengrid_users"));
    	u.grantRole(new OpenGridUserRole("opengrid_admins"));    	
    	return u;
	}
    

	public void addUser(User user) {
        userMap.put(user.getUsername(), user);
    }
}