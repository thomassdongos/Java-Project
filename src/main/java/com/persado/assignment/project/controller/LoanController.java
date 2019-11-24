package com.persado.assignment.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.service.BookService;
import com.persado.assignment.project.service.LoanService;

@RestController
public class LoanController {

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/loan-book", headers = "Accept=application/json", method = RequestMethod.POST)
	public ModelAndView loanBook(@Valid Loan loan, BindingResult bindingResult) {

		ModelAndView model = new ModelAndView();
		if (bindingResult.hasErrors()) {
			model.addObject("allBooks", bookService.findAll());
			model.setViewName("book/all-books");
			return model;
		}

		loanService.saveLoan(loan.getBook().getBookId(), loan.getUser().getUserId()); 

		model.addObject("msg", "Loan has been completed successfully.");
		model.addObject("allBooks", bookService.findAll());
		model.setViewName("book/all-books");

		return model;
	}
	
	@RequestMapping(value = "/return-books", headers = "Accept=application/json", method = RequestMethod.GET)
	public ModelAndView findAllBooksForReturn() {

		// TODO fill the usersmap with id and user firstname for the dropdown from the
		// find all books result
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
