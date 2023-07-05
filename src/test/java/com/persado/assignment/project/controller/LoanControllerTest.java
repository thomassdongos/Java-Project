package com.persado.assignment.project.controller;


import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.model.User;
import com.persado.assignment.project.service.BookService;
import com.persado.assignment.project.service.LoanService;
import com.persado.assignment.project.service.UserService;
import com.persado.assignment.project.util.PdfReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void LoanBookWithValidUserId() {
        int bookId = 1;
        int userId = 1;
        List<Book> allBooks = new ArrayList<>();
        List<User> userWithCurrentBook = new ArrayList<>();
        List<User> allUsers = new ArrayList<>();

        when(bookService.findAll()).thenReturn(allBooks);
        when(loanService.checkUserTotalLoans(userId)).thenReturn(0);
        when(loanService.saveLoan(bookId, userId)).thenReturn(true);
        when(userService.findAllUsers()).thenReturn(allUsers);
        when(loanService.findUsersWithBook(anyInt())).thenReturn(userWithCurrentBook);

        ModelAndView result = loanController.loanBook(bookId, userId);

        assertEquals("book/loan-books", result.getViewName());
        assertEquals(allBooks, result.getModel().get("booksForLoan"));
        verify(bookService).findAll();
        verify(loanService).checkUserTotalLoans(userId);
        verify(loanService).saveLoan(bookId, userId);
    }

    @Test
    void LoanBookWithNullUserId() {
        int bookId = 1;
        Integer userId = null;
        List<Book> allBooks = new ArrayList<>();
        List<User> userWithCurrentBook = new ArrayList<>();
        List<User> allUsers = new ArrayList<>();

        when(bookService.findAll()).thenReturn(allBooks);
        when(userService.findAllUsers()).thenReturn(allUsers);
        when(loanService.findUsersWithBook(anyInt())).thenReturn(userWithCurrentBook);

        ModelAndView result = loanController.loanBook(bookId, userId);

        assertEquals("book/loan-books", result.getViewName());
        assertEquals("Please select a user to loan the requested book.", result.getModel().get("msgError"));
        assertEquals(allBooks, result.getModel().get("booksForLoan"));
        verify(bookService).findAll();

    }

    @Test
    void ReturnBooksWithValidUserId() {
        int bookId = 1;
        int userId = 1;
        List<Loan> allLoaned = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();
        List<User> userWithCurrentBook = new ArrayList<>();

        when(loanService.findAllBooksForReturn()).thenReturn(allLoaned);
        when(loanService.findUsersWithBook(anyInt())).thenReturn(userWithCurrentBook);

        // Act
        ModelAndView result = loanController.returnBooks(bookId, userId);

        // Assert
        assertEquals("book/return-books", result.getViewName());
        assertEquals(bookList, result.getModel().get("booksForReturn"));
        verify(loanService).findAllBooksForReturn();

    }

    @Test
    void ReturnBooksWithNullUserId() {
        // Arrange
        int bookId = 1;
        Integer userId = null;
        List<Loan> allLoaned = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();
        List<User> userWithCurrentBook = new ArrayList<>();

        when(loanService.findAllBooksForReturn()).thenReturn(allLoaned);
        when(loanService.findUsersWithBook(anyInt())).thenReturn(userWithCurrentBook);

        ModelAndView result = loanController.returnBooks(bookId, userId);

        assertEquals("book/return-books", result.getViewName());
        assertEquals("Please select a user to return the requested book.", result.getModel().get("msgError"));
        assertEquals(bookList, result.getModel().get("booksForReturn"));
        verify(loanService).findAllBooksForReturn();
    }

    @Test
    void GenerateLoanReport() {
        List<Loan> loans = new ArrayList<>();

        when(loanService.getLoansLastWeek()).thenReturn(loans);

        PdfReportGenerator.generateReport(loans);

        String result = loanController.generateLoanReport();

        assertEquals("Loan report generated successfully.", result);
        verify(loanService).getLoansLastWeek();
        PdfReportGenerator.generateReport(loans);
    }

}

