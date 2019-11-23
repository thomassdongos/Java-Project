package com.persado.assignment.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.service.BookService;

@RestController
public class HomeController implements ErrorController {

	@Autowired
	private BookService bookService;
	
	private final static String PATH = "/error";

	@Override
	@RequestMapping(PATH)
	@ResponseBody
	public String getErrorPath() {

		return "Path not found";
	}
	
	/**
	 * 
	 * 
	 * @param model
	 * @return
	 */
    @RequestMapping("/")
    ModelAndView index() {

    	ModelAndView model = new ModelAndView();
        List<Book> books = new ArrayList<>();
        try {
            books = bookService.findAll();
        } catch (Exception ex) {
            throw ex;
        }
        model.addObject("books", books);
        model.addObject("now", LocalDateTime.now());
        model.addObject("classActiveHome", "active");
        model.setViewName("home/index");
        return model;
    }
}
