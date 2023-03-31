package com.bookstore.service;

import com.bookstore.dto.SearchCriteria;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookStoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookStoreImplTest {


    @Autowired
    BookStoreRepository bookStoreRepository;

    @Autowired
    BookStoreImpl bookStoreImpl;

    @Test
    public void addBookTest() {
        Book savedBook = bookStoreImpl.addBook(getBookDb());

        bookStoreRepository.deleteAll();

        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    public void allBooksTest() {

        bookStoreImpl.addBook(getBookDb());
        bookStoreImpl.addBook(getBookDb());

        List<Book> booksListDb = bookStoreImpl.books();
        assertThat(booksListDb.size()).isEqualTo(2);

        bookStoreRepository.deleteAll();
    }

    @Test
    public void updateBookTest() {

        var book = bookStoreImpl.addBook(getBookDb());

        var savedBook = bookStoreImpl.getBook(book.getId());

        savedBook.setTitle("newNameForTest");

        var newSavedBook = bookStoreImpl.updateBook(savedBook, savedBook.getId());


        assertThat(newSavedBook.getTitle()).isEqualTo("newNameForTest");

        bookStoreRepository.deleteAll();
    }


    @Test
    public void updateBookTestFail_When_book_notFound() {
        Assertions.assertThrows(BookNotFoundException.class, () -> bookStoreImpl.updateBook(getBookDb(), UUID.randomUUID()));
    }

    @Test
    public void deleteBookTest() {
        var book = bookStoreImpl.addBook(getBookDb());


        bookStoreImpl.deleteBook(book.getId());

        Assertions.assertThrows(BookNotFoundException.class, () -> bookStoreImpl.getBook(book.getId()));

    }

    @Test
    public void deleteBookTestFail_When_book_notFound() {
        Assertions.assertThrows(BookNotFoundException.class, () -> bookStoreImpl.deleteBook(UUID.randomUUID()));
    }


    @Test
    public void searchBook() {


        bookStoreImpl.addBook(getBookDb("Book1", "eBook", 10, "Fiction", "Priya"));
        bookStoreImpl.addBook(getBookDb("Book2", "Paper_Back", 5, "Thriller", "Hari"));

        List<SearchCriteria> searchCriteriaList = getSearchCriteriaLists();

        List<Book> booksListDb = bookStoreImpl.searchBooks(searchCriteriaList);
        assertThat(booksListDb.size()).isEqualTo(1);

        bookStoreRepository.deleteAll();

    }


    private Book getBookDb() {

        return Book.builder().title("book1").type("ebook").volumes(3).genre("fiction").author("Priya").build();
    }

    private Book getBookDb(String title, String type, int volumes, String genre, String author) {

        return Book.builder().title(title).type(type).volumes(volumes).genre(genre).author(author).build();
    }


    private List<SearchCriteria> getSearchCriteriaLists() {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("type");
        searchCriteria.setOperation("eq");
        searchCriteria.setValue("eBook");

        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setKey("volumes");
        searchCriteria1.setOperation(">");
        searchCriteria1.setValue("1");

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setKey("author");
        searchCriteria2.setOperation("like");
        searchCriteria2.setValue("Priya");

        searchCriteriaList.add(searchCriteria);
        searchCriteriaList.add(searchCriteria1);
        searchCriteriaList.add(searchCriteria2);
        return searchCriteriaList;
    }

    @Test
    public void sampleDataLoadTest() throws URISyntaxException, IOException {
        var sampleDataList = bookStoreImpl.loadSampleData();
        assertThat(sampleDataList.size()).isEqualTo(4);
        bookStoreRepository.deleteAll();
    }


}
