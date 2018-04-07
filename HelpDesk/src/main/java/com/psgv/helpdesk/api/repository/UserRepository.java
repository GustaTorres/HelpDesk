package com.psgv.helpdesk.api.repository;

import com.psgv.helpdesk.api.entity.User;

public interface UserRepository extends GenericRepository<User, String> {
	
	User findByEmail(String email);
}
