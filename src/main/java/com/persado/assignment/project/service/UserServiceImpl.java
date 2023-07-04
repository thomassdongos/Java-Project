package com.persado.assignment.project.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.persado.assignment.project.model.User;
import com.persado.assignment.project.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Create a user
	 * 
	 * @param user	The user entity
	 */
	@Override
	@Transactional
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
	@Transactional
	public void deleteUser(Integer userId) {

		userRepository.deleteById(userId);
	}
	
}
