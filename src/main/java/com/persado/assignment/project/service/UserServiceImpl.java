package com.persado.assignment.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.persado.assignment.project.model.User;
import com.persado.assignment.project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Create a user
	 * 
	 * @param user	The user entity
	 */
	@Override
	public void createUser(User user) {
		
		userRepository.save(user);
	}
	
	/**
	 * Find all users
	 * 
	 * @return	List<User>
	 */
	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}
	
	/**
	 * Find user entity by user ID
	 * 
	 * @param userId	The user ID
	 * @return	User
	 */
	@Override
	public User findByUserId(Integer userId) {
		
		return userRepository.findByUserId(userId);
	}
	
	@Override
	public void deleteUser(Integer userId) {
		
		userRepository.deleteById(userId);
	}
	
}
