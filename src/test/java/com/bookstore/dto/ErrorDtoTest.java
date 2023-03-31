package com.bookstore.dto;

import com.bookstore.GlobalExceptionHandler;
import com.bookstore.controller.BookController;
import com.bookstore.exception.BookNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ErrorDtoTest {


    @Mock
    BookController bookController;

    private MockMvc mockmvc;

    @BeforeEach
    public void setup() {
        this.mockmvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void checkIllegalArgumentExceptionAndStatusCode406IsReturnedInResponse() throws Exception {

        when(bookController.health()).thenThrow(new IllegalArgumentException("Unexpected Exception"));

        mockmvc.perform(get("/api/v1/books/health"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void checkBookNotFoundExceptionAndStatusCode404IsReturnedInResponse() throws Exception {

        when(bookController.health()).thenThrow(new BookNotFoundException("Book not found"));

        mockmvc.perform(get("/api/v1/books/health"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkHttpMessageNotReadableExceptionAndStatusCode406IsReturnedInResponse() throws Exception {

        when(bookController.health()).thenThrow(new HttpMessageNotReadableException("Unexpected Exception"));

        mockmvc.perform(get("/api/v1/books/health"))
                .andExpect(status().is4xxClientError());
    }


}
