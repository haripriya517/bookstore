package com.bookstore.service;

import com.bookstore.dto.SearchCriteria;
import com.bookstore.model.Book;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;


public interface IBookStore {

    Book addBook(Book bookDto);

    List<Book> books();

    Book updateBook(Book bookDto, UUID bookId);

    Book getBook(UUID bookId);

    void deleteBook(UUID bookId);

    List<Book> searchBooks(List<SearchCriteria> searchCriteriaList);


    List<Book> loadSampleData() throws URISyntaxException, IOException;
}
