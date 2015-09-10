package org.opengrid.security.impl;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DBAuthenticationProvider extends  AbstractUserDetailsAuthenticationProvider implements AuthenticationManager {
	
	@Resource(name="userDetailsService")
	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder;
	
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
	private String userNotFoundEncodedPassword;
	
	@Override
	protected void additionalAuthenticationChecks(
			UserDetails userdetails,
			UsernamePasswordAuthenticationToken usernamepasswordauthenticationtoken)
			throws AuthenticationException {
	}

	public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
		System.out.println("Here");
		Authentication a = super.authenticate(authentication);
		return a;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected UserDetails retrieveUser(
			String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
	   UserDetails loadedUser;
	
	   try {
		   
		   loadedUser = this.getUserDetailsService().loadUserByUsername(username);
		   
	   } catch (UsernameNotFoundException notFound) {
	       throw notFound;
	   } catch (Exception repositoryProblem) {
		   throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
	   }
	
	   if (loadedUser == null) {
		   throw new AuthenticationServiceException(
	           "UserDetailsService returned null, which is an interface contract violation");
	   } else {
		   //validate password
		   if(authentication.getCredentials() != null) {
	           String presentedPassword = authentication.getCredentials().toString();
	           //passwordEncoder.isPasswordValid(userNotFoundEncodedPassword, presentedPassword, null);
	           
	           //simplistic check for our template implementation
	           if (!presentedPassword.equals(loadedUser.getPassword())) {
	        	   throw new AuthenticationServiceException("Invalid username or password.");
	           }
	       }
	   }
	   return loadedUser;
	}
	
	
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
}
