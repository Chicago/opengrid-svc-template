package org.opengrid.security;

import org.springframework.security.core.GrantedAuthority;

//TODO: make role class easily more expandable
public class OpenGridUserRole {
	private String id;
	
	public OpenGridUserRole(String id) {
		this.id = id;
	}
	
	public String getValue() {
        return this.id;
    }
	
	public String toString() {
		return this.id;
	}

	
	public GrantedAuthority asAuthority() {
		GrantedAuthority authority = new org.opengrid.security.impl.UserAuthority();
		//= new UserAuthority();
		((org.opengrid.security.impl.UserAuthority) authority).setAuthority(toString());
		return authority;
	}
	
	public static OpenGridUserRole valueOf(final GrantedAuthority authority) {
		return new OpenGridUserRole(authority.getAuthority().toString());
	}
	
}
