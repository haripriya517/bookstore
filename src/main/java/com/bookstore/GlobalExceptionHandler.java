package com.bookstore;

import com.bookstore.dto.ErrorDto;
import com.bookstore.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.toString(), e.getMessage(), LocalDate.now().toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> illegalHandlerException(IllegalArgumentException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), LocalDate.now().toString()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> httpMessageNotReadableException(HttpMessageNotReadableException e) {

        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), LocalDate.now().toString()), HttpStatus.NOT_ACCEPTABLE);
    }

}
