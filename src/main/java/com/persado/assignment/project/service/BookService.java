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

	/**
	 * Create a book entity to the database.
	 * 
	 * @param book	The book entity
	 */
	public void createBook(Book book) {

		book.setAvailableCopies(book.getTotalCopies());
		bookRepository.save(book);
	}

	/**
	 * Find all books
	 * 
	 * @return List<Book>
	 */
	public List<Book> findAll() {

		return bookRepository.findAll();
	}
	
	/**
	 * Find all books available for loan
	 * 
	 * @return List<Book>
	 */
	public List<Book> findBooksForLoan() {

		return bookRepository.findBooksForLoan();
	}
	
	/**
	 * Find book entity by book ID
	 * 
	 * @param bookId	The book ID
	 * @return	Book
	 */
	public Book findByBookId(Integer bookId) {
		
		return bookRepository.findByBookId(bookId);
	}
	
	/**
	 * Reduce available copies.
	 * 
	 * @param bookId	The book ID
	 */
	public void reduceAvailableCopies(Integer bookId) {

		Book bookEnt = bookRepository.findByBookId(bookId);
		bookEnt.setAvailableCopies(bookEnt.getTotalCopies() - 1);
		bookRepository.saveAndFlush(bookEnt);
	}
	
	/**
	 * Delete book by ID.
	 * 
	 * @param bookId	The book ID
	 */
	public void deleteBook(Integer bookId) {
		
		bookRepository.deleteById(bookId);
	}

}
