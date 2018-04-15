package com.psgv.helpdesk.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.repository.UserRepository;

@Service
public class UserService extends CrudService<User, String> {

	private UserRepository repo;
	
	@Autowired
	public UserService(UserRepository repo) {
		super(repo);
		this.repo = repo;
	}
	
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public Page<User> findAll(int page, int count) {
		Pageable pageable = new PageRequest(page, count);
		return repo.findAll(pageable);
	}
}
