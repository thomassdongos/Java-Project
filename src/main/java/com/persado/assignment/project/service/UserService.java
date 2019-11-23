package com.persado.assignment.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persado.assignment.project.model.User;
import com.persado.assignment.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void createUser(User user) {
		
		userRepository.save(user);
	}
	
}
