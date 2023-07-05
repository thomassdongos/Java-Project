package com.persado.assignment.project.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.persado.assignment.project.service.*;
import com.persado.assignment.project.util.PdfReportGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.model.User;

@RestController
@Scope("singleton")
public class LoanController {

	private final LoanService loanService;
	
	private final BookService bookService;
	
	private final UserService userService;

	private final Object lock = new Object(); // Object used for synchronization


	public LoanController( final LoanService loanService, final BookService bookService, final UserService userService) {
		this.loanService = loanService;
		this.bookService = bookService;
		this.userService = userService;
	}

	@RequestMapping(value = "/loan-books", headers = "Accept=application/json", method = RequestMethod.POST)
	public ModelAndView loanBook(@RequestParam(value = "bookId", required = true) Integer bookId, @RequestParam(value = "userId", required = false) Integer userId) {
		synchronized (lock) {
			ModelAndView model = new ModelAndView();

			if (userId == null) {

				List<Book> allBooks = bookService.findAll();
				for (Book book : allBooks) {
					List<User> userWithCurrentBook = loanService.findUsersWithBook(book.getBookId());
					List<User> allUsers = userService.findAllUsers();
					List<User> uniqueElementsFromBothList = new ArrayList<>();
					// Unique element from allUsers list to fill the dropdown
					uniqueElementsFromBothList.addAll(
							allUsers.stream()
									.filter(user -> !userWithCurrentBook.contains(user))
									.collect(Collectors.toList()));
					book.setUsersForLoan(uniqueElementsFromBothList);
				}

				model.addObject("msgError", "Please select a user to loan the requested book.");
				model.addObject("booksForLoan", allBooks);
				model.setViewName("book/loan-books");

				return model;
			}

			int userTotalLoans = loanService.checkUserTotalLoans(userId);
			if (userTotalLoans == 3) {
				model.addObject("msgError", "User cannot loan the requested book he has already loaned 3 books.");
			} else {
				boolean userCanLoan = loanService.saveLoan(bookId, userId);
				if (userCanLoan) {
					model.addObject("msg", "The loan has been completed successfully.");
				} else {
					model.addObject("msgError", " User cannot loan the book there are no available copies.");
				}

			}

			List<Book> allBooks = bookService.findAll();
			for (Book book : allBooks) {
				List<User> userWithCurrentBook = loanService.findUsersWithBook(book.getBookId());
				List<User> allUsers = userService.findAllUsers();
				List<User> uniqueElementsFromBothList = new ArrayList<>();
				// Unique element from allUsers list to fill the dropdown
				uniqueElementsFromBothList.addAll(
						allUsers.stream()
								.filter(user -> !userWithCurrentBook.contains(user))
								.collect(Collectors.toList()));
				book.setUsersForLoan(uniqueElementsFromBothList);
			}

			model.addObject("booksForLoan", allBooks);
			model.setViewName("book/loan-books");

			return model;
		}
	}
	
	@RequestMapping(value = "/return-books", headers = "Accept=application/json", method = RequestMethod.GET)
	public ModelAndView findAllBooksForReturn() {
		synchronized (lock) {
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
	
	@RequestMapping(value = "/return-books", headers = "Accept=application/json", method = RequestMethod.POST)
	public ModelAndView returnBooks(@RequestParam(value = "bookId", required = true) Integer bookId, @RequestParam(value = "userId", required = false) Integer userId) {
		synchronized (lock) {
			if (userId == null) {

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
				model.addObject("msgError", "Please select a user to return the requested book.");
				model.addObject("booksForReturn", bookList);
				model.setViewName("book/return-books");

				return model;

			}


			loanService.returnBook(bookId, userId);

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
			model.addObject("msg", "The return has been completed successfully.");
			model.addObject("booksForReturn", bookList);
			model.setViewName("book/return-books");

			return model;
		}


	}
	@GetMapping("/loans/report")
	@Scheduled(cron = "0 0 12 * * MON")
	public String generateLoanReport() {
		List<Loan> loans = loanService.getLoansLastWeek();
		PdfReportGenerator.generateReport(loans);
		return "Loan report generated successfully.";
	}
}
