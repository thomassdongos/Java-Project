package com.persado.assignment.project.service;

import com.persado.assignment.project.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    List<User> findAllUsers();

    User findByUserId(Integer userId);

    void deleteUser(Integer userId);
}
