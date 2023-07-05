package com.persado.assignment.project.service;


import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void CreateBookSavesBookWithCorrectAvailableCopies() throws DatabaseOperationException {
        Book book = new Book();
        book.setTotalCopies(5);
        Integer five =5;

        bookService.createBook(book);

       assertEquals(five, book.getAvailableCopies());
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    void CreateBookThrowsDatabaseOperationExceptionWhenRepositoryThrowsException() {

        Book book = new Book();
        book.setTotalCopies(5);

        doThrow(RuntimeException.class).when(bookRepository).save(book);

        assertThrows(DatabaseOperationException.class, () -> bookService.createBook(book));
    }
    @Test
    void FindAllReturnsListOfBooks() throws DatabaseOperationException {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book());
        expectedBooks.add(new Book());

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.findAll();

        assertEquals(expectedBooks, actualBooks);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findBooksForLoanReturnsListOfBooks() throws DatabaseOperationException {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book());
        expectedBooks.add(new Book());

        when(bookRepository.findBooksForLoan()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.findBooksForLoan();

        assertEquals(expectedBooks, actualBooks);
        verify(bookRepository, times(1)).findBooksForLoan();
    }

    @Test
    void findByBookIdReturnsBookWithMatchingId() throws DatabaseOperationException {
        int bookId = 1;
        Book expectedBook = new Book();
        expectedBook.setBookId(bookId);

        when(bookRepository.findByBookId(bookId)).thenReturn(expectedBook);

        Book actualBook = bookService.findByBookId(bookId);

        assertEquals(expectedBook, actualBook);
        verify(bookRepository, times(1)).findByBookId(bookId);
    }


    @Test
    void reduceAvailableCopiesReturnsNullWhenAvailableCopiesAreZero() throws DatabaseOperationException {
        int bookId = 1;
        Book book = new Book();
        book.setBookId(bookId);
        book.setAvailableCopies(0);

        when(bookRepository.findByBookId(bookId)).thenReturn(book);

        Book result = bookService.reduceAvailableCopies(bookId);

        assertEquals(null, result);
        verify(bookRepository, never()).saveAndFlush(book);
    }



    @Test
    void deleteBookShouldCallDeleteById() throws DatabaseOperationException {
        int bookId = 1;

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}

