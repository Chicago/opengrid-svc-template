package org.opengrid.security.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.opengrid.security.OpenGridUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4698826823137032377L;

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@Size(min = 4, max = 30)
	private String username;

	@NotNull
	@Size(min = 4, max = 100)
	private String password;

	@Transient
	private long expires;

	@NotNull
	private boolean accountExpired;

	@NotNull
	private boolean accountLocked;

	@NotNull
	private boolean credentialsExpired;

	@NotNull
	private boolean accountEnabled;

	@Transient
	private String newPassword;

	//additional info we need
	private String firstName;
	private String lastName;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<GrantedAuthority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	@JsonIgnore
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	// Use Roles as external API
	/*public Set<OpenGridUserRole> getRoles() {
		Set<OpenGridUserRole> roles = EnumSet.noneOf(OpenGridUserRole.class);
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				roles.add(new OpenGridUserRole(authority.toString()));
			}
		}
		return roles;
	}*/
	
	
	public List<String> getAuthoritiesAsStringList() {
		List<String> roles = new ArrayList<String>();
		if (authorities != null) {
			for (GrantedAuthority authority : authorities) {
				roles.add(OpenGridUserRole.valueOf(authority).getValue());
			}
		}
		return roles;
	}

	public void setRoles(Set<OpenGridUserRole> roles) {
		for (OpenGridUserRole role : roles) {
			grantRole(role);
		}
	}
	
	
	public List<String> getAccessibleResources() {
		return null;
	}

	public void grantRole(OpenGridUserRole role) {
		if (authorities == null) {
			authorities = new HashSet<GrantedAuthority>();
		}
		authorities.add(role.asAuthority());
	}

	public void revokeRole(OpenGridUserRole role) {
		if (authorities != null) {
			authorities.remove(role.toString());
		}
	}

	public boolean hasRole(OpenGridUserRole role) {
		return authorities.contains(role.toString());
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return !accountEnabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}
	
	@JsonIgnore
	public String getFirstName() {
		return this.firstName;
	}

	@JsonProperty
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonIgnore
	public String getLastName() {
		return this.lastName;
	}

	@JsonProperty
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUsername();
	}
}
