package com.persado.assignment.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.persado.assignment.project.service.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class BookController {

	private final BookService bookService;

	private final UserService userService;

	private final LoanService loanService;

	public BookController(final BookService bookService, final UserService userService, final LoanService loanService) {
		this.bookService = bookService;
		this.userService = userService;
		this.loanService = loanService;
	}

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
	
	/**
	 * @return
	 */
	@ApiOperation(value = "Find all books", notes = "Find all books in the database and return them as a list.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(value = "/all-books", headers = "Accept=application/json", method = RequestMethod.GET)
    public ModelAndView findAllBooks() {

		List<Book> allBooks = bookService.findAll();
		for (Book book : allBooks) {
			List<User> userWithCurrentBook = loanService.findUsersWithBook(book.getBookId());
			book.setUsersForLoan(userWithCurrentBook);
		}
		
		ModelAndView model = new ModelAndView();
		model.addObject("allBooks", allBooks);
		model.setViewName("book/all-books");

        return model;
    }
	
	@RequestMapping(value = "/delete-book/{bookId}", method = RequestMethod.GET)
	public ModelAndView deleteBook(@PathVariable Integer bookId) {
		
		Book bookForDeletion = bookService.findByBookId(bookId);
		String bookName = bookForDeletion.getBookName();
		
		List<User> allUsers = loanService.findUsersWithBook(bookId);
		if (!allUsers.isEmpty()) {
			
			List<Book> allBooks = bookService.findAll();
			for (Book book : allBooks) {
				List<User> allLoanedUsers = loanService.findUsersWithBook(book.getBookId());
				book.setUsersForLoan(allLoanedUsers);
			}
			
			StringBuilder allUsernames = new StringBuilder();
			for (User user : allUsers) {
				allUsernames.append(user.getFirstname() + ", ");
			}
			// Delete last comma
			allUsernames.deleteCharAt(allUsernames.length() - 2);
			
			ModelAndView model = new ModelAndView();
	    	model.addObject("msgBooks", "Book " + bookName + " cannot be deleted it is loaned by user: " + allUsernames);
			model.addObject("allBooks", allBooks);
			model.setViewName("book/all-books");
			
			return model;
		}
		
		bookService.deleteBook(bookId);

		List<Book> allBooks = bookService.findAll();
		for (Book book : allBooks) {
			List<User> allLoanedUsers = loanService.findUsersWithBook(book.getBookId());
			book.setUsersForLoan(allLoanedUsers);
		}
		
		ModelAndView model = new ModelAndView();
    	model.addObject("msg", "Book " + bookName + " has been deleted successfully.");
		model.addObject("allBooks", allBooks);
		model.setViewName("book/all-books");

		return model;
	}
	
	@RequestMapping(value = "/loan-books", headers = "Accept=application/json", method = RequestMethod.GET)
	public ModelAndView findAllBooksForLoan() {

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
		
		ModelAndView model = new ModelAndView();
		model.addObject("booksForLoan", allBooks);
		model.setViewName("book/loan-books");

        return model;
    }
	
}
