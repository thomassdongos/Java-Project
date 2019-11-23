package com.persado.assignment.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.repository.BookRepository;


@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> findAll() {
		
		return bookRepository.findAll();
	}
	
}
