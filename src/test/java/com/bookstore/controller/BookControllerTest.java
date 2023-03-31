package com.bookstore.controller;

import com.bookstore.dto.SearchCriteria;
import com.bookstore.model.Book;
import com.bookstore.service.IBookStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    IBookStore iBookStore;

    @InjectMocks
    BookController bookController;

    private MockMvc mockmvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() throws Exception {

        mockmvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testAddBook() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.post("/api/v1/books").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL).content(asJsonString(getBookDb("Book1", "eBook", 10, "Fiction", "Priya")))).andExpect(status().is(201));

    }

    @Test
    public void testAllBooks() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/books").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)).andExpect(status().is(200));

    }

    @Test
    public void testUpdateBook() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.put("/api/v1/books/"+ UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL).content(asJsonString(getBookDb("Book1", "eBook", 10, "Fiction", "Priya")))).andExpect(status().is(200));

    }

    @Test
    public void testDeleteBook() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/"+ UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)).andExpect(status().is(202));

    }

    @Test
    public void testGetBook() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/books/"+ UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)).andExpect(status().is(200));

    }

    @Test
    public void testHealth() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/books/health").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)).andExpect(status().is(200));

    }

    @Test
    public void testSearchBook() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/books/search").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL)
                        .content(asJsonString(getSearchCriteriaDto())))
                .andExpect(status().is(200));

    }


    private List<SearchCriteria> getSearchCriteriaDto() {

        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("type");
        searchCriteria.setOperation("eq");
        searchCriteria.setValue("ebook");
        searchCriteriaList.add(searchCriteria);
        return searchCriteriaList;

    }


    private Book getBookDb(String title, String type, int volumes, String genre, String author) {

        return Book.builder().title(title).type(type).volumes(volumes).genre(genre).author(author).build();
    }

    @Test
    public void testSampleData() throws Exception {
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/books/loadSampleData").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().is(200));

    }

}
