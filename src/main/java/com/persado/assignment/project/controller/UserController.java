package com.persado.assignment.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.User;
import com.persado.assignment.project.service.BookService;
import com.persado.assignment.project.service.UserService;


@RestController
public class UserController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/create-user" }, method = RequestMethod.GET)
	public ModelAndView createUser() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("user", new User());
		model.setViewName("user/create-user");

		return model;
	}
	
	@RequestMapping(value = { "/create-user" }, method = RequestMethod.POST)
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		
		ModelAndView model = new ModelAndView();
		if (bindingResult.hasErrors()) {
			model.setViewName("user/create-user");
			return model;
		}
		
		userService.createUser(user);
    	model.addObject("msg", "User has been registered successfully.");
    	model.addObject("user", new User());
		model.setViewName("user/create-user");

		return model;
	}
    
	
	@RequestMapping(value = "/all-users", method = RequestMethod.GET)
	public ModelAndView getAllUsers() {

		ModelAndView model = new ModelAndView();
		model.addObject("allUsers", userService.findAllUsers());
		model.setViewName("user/all-users");

		return model;
	}
	
}
