package com.psgv.helpdesk.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.repository.UserRepository;

@Service
public class UserService extends CrudService<User, String> {

	UserRepository repo;
	
	@Autowired
	public UserService(UserRepository repo) {
		super(repo);
		this.repo = repo;
	}
	
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}
}
