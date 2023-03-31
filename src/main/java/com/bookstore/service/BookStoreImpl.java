package com.bookstore.service;

import com.bookstore.dto.SearchCriteria;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookStoreImpl implements IBookStore {

    @Autowired
    BookStoreRepository bookStoreRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Book addBook(Book book) {
        return bookStoreRepository.save(book);
    }

    @Override
    public List<Book> books() {
        return bookStoreRepository.findAll();
    }

    @Override
    public Book updateBook(Book book, UUID bookId) {

        var bookDb = bookStoreRepository.findById(bookId);
        if (bookDb.isEmpty()) {
            throw new BookNotFoundException("The respected book is not found");
        }
        Book bookFromDb = bookDb.get();

        bookFromDb.updateBook(book);

        return bookStoreRepository.save(bookFromDb);

    }

    @Override
    public Book getBook(UUID bookId) {
        var bookDb = bookStoreRepository.findById(bookId);
        if (bookDb.isEmpty()) {
            throw new BookNotFoundException("The respected book is not found");
        }
        return bookDb.get();
    }

    @Override
    public void deleteBook(UUID bookId) {
        var bookDb = bookStoreRepository.findById(bookId);
        if (bookDb.isEmpty()) {
            throw new BookNotFoundException("The respected book is not found");
        }
        bookStoreRepository.deleteById(bookId);

    }

    @Override
    public List<Book> searchBooks(List<SearchCriteria> searchCriteriaList) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> bookCriteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = bookCriteriaQuery.from(Book.class);

        //Constructing list of parameters
        List<Predicate> predicates = searchCriteriaList.stream().map(searchCriteria -> {
            //Adding predicates in case of parameter not being null
            if (searchCriteria.getValue() != null) {
                if (searchCriteria.getOperation().equalsIgnoreCase("like")) {

                    return criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
                }

                if (searchCriteria.getOperation().equalsIgnoreCase("eq")) {

                    return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
                }

                if (searchCriteria.getOperation().equalsIgnoreCase(">")) {

                    return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
                }
            }
            return null;
        }).filter(p->p!=null).collect(Collectors.toList());

        //query itself
        bookCriteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        //execute query and do something with result
        return entityManager.createQuery(bookCriteriaQuery).getResultList();
    }

    @Override
    public List<Book> loadSampleData() throws URISyntaxException, IOException {

        InputStream resource = getClass().getClassLoader().getResourceAsStream("books.txt");

        List<String> lines = new BufferedReader(new InputStreamReader(resource))
                    .lines().collect(Collectors.toList());

            return lines.stream().map(book->{
                return processSampleBookData(book);
            }).filter(b->b!=null).collect(Collectors.toList());
    }

    private Book processSampleBookData(String book) {
        if(book.isBlank())
            return null;
        String bookValues[]=book.split("\\|");
        if(bookValues==null || bookValues.length!=5)
            return null;
        Book bookObject=Book.builder()
                .author(bookValues[0])
                .genre(bookValues[1])
                .title(bookValues[2])
                .type(bookValues[3])
                .volumes(Integer.parseInt(bookValues[4]))
                .build();
        return bookStoreRepository.save(bookObject);
    }
}
