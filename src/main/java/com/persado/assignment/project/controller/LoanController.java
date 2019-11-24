package com.persado.assignment.project.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.Book;
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

		// I keep the book entities as unique
		Set<Book> allBooks = new HashSet<>();
		List<Book> bookList = new ArrayList<>();
 		
		List<Loan> allLoaned = loanService.findAllBooksForReturn();
		for (Loan loan : allLoaned) {
			allBooks.add(loan.getBook());
		}
		bookList.addAll(allBooks);
		
		for (Book book : bookList) {
			book.setUsersForLoan(loanService.findUsersWithBook(book.getBookId()));
		}

		ModelAndView model = new ModelAndView();
		model.addObject("booksForReturn", bookList);
		model.setViewName("book/return-books");

		return model;
	}
}
