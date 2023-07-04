package com.persado.assignment.project.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.model.User;
import com.persado.assignment.project.repository.LoanRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanServiceImpl implements LoanService {

	private  final BookServiceImpl bookService;

	private final UserServiceImpl userService;
	
	private final LoanRepository loanRepository;

	public LoanServiceImpl(BookServiceImpl bookService, UserServiceImpl userService, LoanRepository loanRepository) {
		this.bookService = bookService;
		this.userService = userService;
		this.loanRepository = loanRepository;
	}

	/**
	 * Find all users
	 * 
	 * @return	List<User>
	 */
	@Override
	public List<Loan> findAllBooksForReturn() {

		return loanRepository.findAllBooksForReturn();
	}
	
	/**
	 * Save a loan entity when a user loans a book.
	 * 
	 * @param bookId	The book ID
	 * @param userId	The user ID
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public boolean saveLoan(Integer bookId, Integer userId) {

		boolean userCanLoan = false;
		
		Book bookEnt = bookService.reduceAvailableCopies(bookId);
		
		if (bookEnt != null) {
			Loan loan = new Loan();
			loan.setUser(userService.findByUserId(userId));
			loan.setBook(bookEnt);
			loan.setLoanDate(LocalDate.now());
			loan.setLoaned(true);
			loan.setReturnedDate(null);
			loanRepository.saveAndFlush(loan);
			userCanLoan = true;
		} 
		return userCanLoan;
	}
	
	/**
	 * 
	 * 
	 * @param bookId
	 * @return
	 */
	@Override
	public List<User> findUsersWithBook(Integer bookId) {

		return loanRepository.findUsersWithBook(bookId);
	}
	
	
	@Override
	@Transactional
	public void returnBook(Integer bookId, Integer userId) {

		bookService.addAvailableCopies(bookId);
		
		Loan loan = loanRepository.findBookLoaned(bookId, userId);
		loan.setLoaned(false);
		loan.setReturnedDate(LocalDate.now());
		loanRepository.saveAndFlush(loan);
	}
	
	@Override
	public List<Book> findBooksUserLoaned(Integer userId) {

		return loanRepository.findBooksUserLoaned(userId);
	}
	
	@Override
	public int checkUserTotalLoans(Integer userId) {

		return loanRepository.findBooksUserLoaned(userId).size();
	}
	
}
