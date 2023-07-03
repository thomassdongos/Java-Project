package com.persado.assignment.project.service;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.model.User;

import java.util.List;

public interface LoanService {
    List<Loan> findAllBooksForReturn();

    boolean saveLoan(Integer bookId, Integer userId);

    List<User> findUsersWithBook(Integer bookId);

    void returnBook(Integer bookId, Integer userId);

    List<Book> findBooksUserLoaned(Integer userId);

    int checkUserTotalLoans(Integer userId);
}
