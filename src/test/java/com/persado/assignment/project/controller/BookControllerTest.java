package com.persado.assignment.project.controller;

import com.persado.assignment.project.service.BookService;
import com.persado.assignment.project.service.DatabaseOperationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import com.persado.assignment.project.model.Book;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ExceptionThrownWhenCreateUser() {
        Book book = new Book();
        BindingResult bindingResult = mock(BindingResult.class);
        doThrow(DatabaseOperationException.class).when(bookService).createBook(book);
        ModelAndView expectedModel = new ModelAndView("error");

        ModelAndView resultModel = bookController.createUser(book, bindingResult);

        verify(bookService).createBook(book);
        assertSame(expectedModel.getViewName(), resultModel.getViewName());
    }
}
