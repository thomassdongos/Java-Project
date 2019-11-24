package com.persado.assignment.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.model.User;
import com.persado.assignment.project.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanRepository loanRepository;
	
	/**
	 * Find all users
	 * 
	 * @return	List<User>
	 */
	public List<Loan> findAllBooksForReturn() {
		
		return loanRepository.findAllBooksForReturn();
	}
	
	/**
	 * Save a loan entity when a user loans a book.
	 * 
	 * @param bookId	The book ID
	 * @param userId	The user ID
	 */
	public void saveLoan(Integer bookId, Integer userId) {
		
		Book bookEnt = bookService.reduceAvailableCopies(bookId);
		
		Loan loan = new Loan();
		loan.setUser(userService.findByUserId(userId));
		loan.setBook(bookEnt);
		loan.setLoanDate(LocalDate.now());
		loan.setLoaned(true);
		loan.setReturnedDate(null);
		loanRepository.saveAndFlush(loan);
	}
	
	/**
	 * 
	 * 
	 * @param bookId
	 * @return
	 */
	public List<User> findUsersWithBook(Integer bookId) {
		
		return loanRepository.findUsersWithBook(bookId);
	}
	
	
	public void returnBook(Integer bookId, Integer userId) {
		
		bookService.addAvailableCopies(bookId);
		
		Loan loan = loanRepository.findBookLoaned(bookId, userId);
		loan.setLoaned(false);
		loan.setReturnedDate(LocalDate.now());
		loanRepository.saveAndFlush(loan);
	}
	
	public List<Book> findBooksUserLoaned(Integer userId) {
		
		return loanRepository.findBooksUserLoaned(userId);
	}
	
	
}
