package com.college.yi.bookmanager.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookApiController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    // ダミーデータの作成
    private Book createSampleBook(Long id) {
        return new Book(
            id,
            "ノルウェイの森",
            "村上 春樹",
            "講談社",
            LocalDate.of(1987, 9, 4),
            12
        );
    }

    @Test
    void testGetBooks() throws Exception {
        Book book = createSampleBook(1L);
        Mockito.when(bookService.getBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("ノルウェイの森"))
            .andExpect(jsonPath("$[0].author").value("村上 春樹"));
    }

    @Test
    void testCreateBook() throws Exception {
        Book inputBook = new Book(null, "Java超入門", "山田 太郎", "技術評論社", LocalDate.of(2023, 9, 1), 10);
        Book savedBook = new Book(4L, "Java超入門", "山田 太郎", "技術評論社", LocalDate.of(2023, 9, 1), 10);

        Mockito.when(bookService.createBook(any())).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBook)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.title").value("Java超入門"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book updated = new Book(
            1L,
            "ノルウェイの森（改訂版）",
            "村上 春樹",
            "講談社",
            LocalDate.of(1987, 9, 4),
            15
        );

        Mockito.when(bookService.updateBook(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("ノルウェイの森（改訂版）"))
            .andExpect(jsonPath("$.stock").value(15));
    }

    @Test
    void testDeleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isNoContent());
    }
}
