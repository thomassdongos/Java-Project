package com.persado.assignment.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.User;
import com.persado.assignment.project.service.BookService;
import com.persado.assignment.project.service.LoanService;
import com.persado.assignment.project.service.UserService;


@RestController
public class UserController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanService loanService;
	
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

		List<User> allUsers = userService.findAllUsers();
		for (User user : allUsers) {
			List<Book> allLoanedBooks = loanService.findBooksUserLoaned(user.getUserId());
			user.setBookList(allLoanedBooks);
		}
		
		ModelAndView model = new ModelAndView();
		model.addObject("allUsers", allUsers);
		model.setViewName("user/all-users");

		return model;
	}
	
	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
	public ModelAndView deleteUser(@RequestParam(value = "userId") Integer userId, BindingResult bindingResult) {
		
		userService.deleteUser(userId);

		List<User> allUsers = userService.findAllUsers();
		for (User user : allUsers) {
			List<Book> allLoanedBooks = loanService.findBooksUserLoaned(user.getUserId());
			user.setBookList(allLoanedBooks);
		}
		
		ModelAndView model = new ModelAndView();
    	model.addObject("msg", "User has been deleted successfully.");
		model.addObject("allUsers", allUsers);
		model.setViewName("user/all-users");

		return model;
	}
	
}
