package com.persado.assignment.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.service.LoanService;

@RestController
public class LoanController {

	@Autowired
	private LoanService loanService;
	
	@RequestMapping(value = "/return-books", headers = "Accept=application/json", method = RequestMethod.GET)
	public ModelAndView findAllBooksForReturn() {

		// TODO fill the usersmap with id and user firstname for the dropdown from the find all books result
		Map usersMap = new HashMap<>();
		List<Loan> allLoaned = loanService.findAllBooksForReturn();
		// TODO group books by book id and fill the dropdown with the users firstname
		
		ModelAndView model = new ModelAndView();
		model.addObject("booksForReturn", loanService.findAllBooksForReturn());
		model.addObject("usersMap", usersMap);
		model.setViewName("book/return-books");

        return model;
    }
}
