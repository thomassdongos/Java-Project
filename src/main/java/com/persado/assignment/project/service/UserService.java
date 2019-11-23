package com.persado.assignment.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persado.assignment.project.model.User;
import com.persado.assignment.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Create a user
	 * 
	 * @param user	The user entity
	 */
	public void createUser(User user) {
		
		userRepository.save(user);
	}
	
	/**
	 * Find all users
	 * 
	 * @return	List<User>
	 */
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}
	
}
