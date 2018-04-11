package com.psgv.helpdesk.api.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.enums.ProfileEnum;

public class JwtUserFactory {
	
	private JwtUserFactory() {
	}
	
	public static JwtUser build(User user) {
		return new JwtUser(user.getId(), 
				user.getEmail(), 
				user.getPassword(), 
				mapToGrantedAuthorities(user.getProfile()));
	}

	private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profile) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(profile.name()));
		return authorities;
	}

}
