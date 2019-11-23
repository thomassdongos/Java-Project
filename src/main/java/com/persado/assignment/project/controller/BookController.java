package com.persado.assignment.project.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.service.BookService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class BookController {

	@Autowired
	BookService bookService;
	
	/**
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/create-book" }, method = RequestMethod.GET)
	public ModelAndView createUser() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("book", new Book());
		model.setViewName("book/create-book");

		return model;
	}
	
	@RequestMapping(value = { "/create-book" }, method = RequestMethod.POST)
	public ModelAndView createUser(@Valid Book book, BindingResult bindingResult) {
		
		ModelAndView model = new ModelAndView();
		if (bindingResult.hasErrors()) {
			model.setViewName("book/create-book");
			return model;
		}
		
		bookService.createBook(book);
    	model.addObject("msg", "Book has been added to the library successfully.");
    	model.addObject("book", new Book());
		model.setViewName("book/create-book");

		return model;
	}
	
	@ApiOperation(value = "Find all books", notes = "Find all books in the database and return them as a list.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/all-books", headers = "Accept=application/json", method = RequestMethod.GET)
    public ModelAndView findAllBooks() {

		ModelAndView model = new ModelAndView();
		model.addObject("allBooks", bookService.findAll());
		model.setViewName("book/all-books");

        return model;
    }
	
	@RequestMapping(value = "/loan-books", headers = "Accept=application/json", method = RequestMethod.GET)
	public ModelAndView findAllBooksForLoan() {

		ModelAndView model = new ModelAndView();
		model.addObject("booksForLoan", bookService.findBooksForLoan());
		model.setViewName("book/loan-books");

        return model;
    }
	
}
