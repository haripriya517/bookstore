package com.bookstore.controller;

import com.bookstore.dto.ErrorDto;
import com.bookstore.dto.SearchCriteria;
import com.bookstore.model.Book;
import com.bookstore.service.IBookStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    IBookStore iBookStore;

    @Operation(summary = "Its default api to check whether the application is up or not")
    @ApiResponse(responseCode = "200", description = "Application health",
            content = {@Content(mediaType = "application/text", schema = @Schema(implementation = String.class))})
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Application is running", HttpStatus.OK);

    }

    @Operation(summary = "Generate the book for a BookStore")
    @ApiResponse(responseCode = "201", description = "Returns the generated Book Object",
            content = {@Content(mediaType = "application/text", schema = @Schema(implementation = Integer.class))})
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book bookRequest) {
        return new ResponseEntity<>(iBookStore.addBook(bookRequest), HttpStatus.CREATED);

    }


    @Operation(summary = "Get all books from BookStore")
    @ApiResponse(responseCode = "200", description = "Return all the books from BookStore",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Book.class)))})
    @GetMapping
    public ResponseEntity<List<Book>> allBooks() {
        return new ResponseEntity<>(iBookStore.books(), HttpStatus.OK);
    }


    @Operation(summary = "Update the specific book in bookstore based on the bookId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the updated book",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "404", description = "Returns the respected book is not found message",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDto.class)))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") UUID id) {
        return new ResponseEntity<>(iBookStore.updateBook(book, id), HttpStatus.OK);
    }

    @Operation(summary = "Get the Book for a given Id")
    @ApiResponse(responseCode = "200", description = "Retrieves a book")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable UUID id) {
        return new ResponseEntity<>(iBookStore.getBook(id), HttpStatus.OK);
    }

    @Operation(summary = "Remove the book from BookStore")
    @ApiResponse(responseCode = "202", description = "Successfully removed")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        iBookStore.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @Operation(summary = "Search the book from BookStore")
    @ApiResponse(responseCode = "200", description = "Return all the books based on search criteria",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Book.class)))})
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestBody List<SearchCriteria> searchCriteriaList) {

        return new ResponseEntity<>(iBookStore.searchBooks(searchCriteriaList), HttpStatus.OK);
    }

    @Operation(summary = "Import sample data")
    @ApiResponse(responseCode = "200", description = "Return all the books imported from file, which is part of the application",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Book.class)))})
    @GetMapping("/loadSampleData")
    public ResponseEntity<List<Book>> populate() throws URISyntaxException, IOException {
        return new ResponseEntity<>(iBookStore.loadSampleData(), HttpStatus.OK);
    }
}
