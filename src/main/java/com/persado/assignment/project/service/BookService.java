package com.persado.assignment.project.service;

import com.persado.assignment.project.model.Book;

import java.util.List;

public interface BookService {
    void createBook(Book book);

    List<Book> findAll();

    List<Book> findBooksForLoan();

    Book findByBookId(Integer bookId);

    Book reduceAvailableCopies(Integer bookId);

    Book addAvailableCopies(Integer bookId);

    void deleteBook(Integer bookId);
}
