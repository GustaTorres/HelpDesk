package com.psgv.helpdesk.api.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5133446368021726449L;
	
	private final String id;
	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public JwtUser(String id, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = userName;
		this.password = password;
		this.authorities = authorities;
	}
	
	@JsonIgnore
	public String getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
