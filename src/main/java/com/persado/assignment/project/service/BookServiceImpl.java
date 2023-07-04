package com.persado.assignment.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.repository.BookRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional(rollbackFor = DatabaseOperationException.class)
public class BookServiceImpl implements BookService {
	private static final Logger logger = Logger.getLogger(BookServiceImpl.class.getName());

	private final BookRepository bookRepository;

	public BookServiceImpl(final BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * Create a book entity in the database.
	 *
	 * @param book The book entity
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public void createBook(Book book) throws DatabaseOperationException {
		try {
			book.setAvailableCopies(book.getTotalCopies());
			bookRepository.save(book);
		} catch (Exception e) {
			logger.severe("An exception occurred: " + e);
			throw new DatabaseOperationException("Error creating book");
		}
	}

	/**
	 * Find all books.
	 *
	 * @return List<Book>
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public List<Book> findAll() throws DatabaseOperationException {
			return bookRepository.findAll();
	}

	/**
	 * Find all books available for loan.
	 *
	 * @return List<Book>
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public List<Book> findBooksForLoan() throws DatabaseOperationException {

			return bookRepository.findBooksForLoan();

	}

	/**
	 * Find book entity by book ID.
	 *
	 * @param bookId The book ID
	 * @return Book
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public Book findByBookId(Integer bookId) throws DatabaseOperationException {

			return bookRepository.findByBookId(bookId);

	}

	/**
	 * Reduce available copies.
	 *
	 * @param bookId The book ID
	 * @return Book
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public Book reduceAvailableCopies(Integer bookId) throws DatabaseOperationException {

			Book bookEnt = bookRepository.findByBookId(bookId);
			if (bookEnt.getAvailableCopies() - 1 < 0) {
				return null;
			}
			bookEnt.setAvailableCopies(bookEnt.getAvailableCopies() - 1);
			return bookRepository.saveAndFlush(bookEnt);

	}

	/**
	 * Add available copies.
	 *
	 * @param bookId The book ID
	 * @return Book
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public Book addAvailableCopies(Integer bookId) throws DatabaseOperationException {

			Book bookEnt = bookRepository.findByBookId(bookId);
			bookEnt.setAvailableCopies(bookEnt.getAvailableCopies() + 1);
			return bookRepository.saveAndFlush(bookEnt);

	}

	/**
	 * Delete book by ID.
	 *
	 * @param bookId The book ID
	 * @throws DatabaseOperationException if an error occurs during the database operation
	 */
	@Override
	public void deleteBook(Integer bookId) throws DatabaseOperationException {

			bookRepository.deleteById(bookId);

	}
}
