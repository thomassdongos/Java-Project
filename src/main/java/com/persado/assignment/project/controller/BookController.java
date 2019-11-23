package com.persado.assignment.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.service.BookService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class BookController {

	@Autowired
	BookService bookService;
	
	@ApiOperation(value = "Find all books", notes = "Find all books in the database and return them as a list.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/", headers = "Accept=application/json", method = RequestMethod.GET)
    public List<Book> findAllBooks() {

        List<Book> books = bookService.findAll();
        return books;
    }
	
}
