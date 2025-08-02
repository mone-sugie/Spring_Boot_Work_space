package com.college.yi.bookmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.model.BookEntity;
import com.college.yi.bookmanager.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testFindAllBooks() {
        List<BookEntity> books = List.of(
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<BookEntity> result = bookService.findAllBooks();

        assertEquals(2, result.size());
        assertEquals("ノルウェイの森", result.get(0).getTitle());
        verify(bookRepository).findAll();
    }

    @Test
    void testCreateBook() {
        Book book = new Book(null, "新しい本", "著者A", "出版社A", LocalDate.of(2025, 1, 1), 3);
        BookEntity savedEntity = new BookEntity();

        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedEntity);

        Book result = bookService.createBook(book);

        assertEquals("新しい本", result.getTitle());
        assertEquals(10L, result.getId());
        verify(bookRepository).save(any(BookEntity.class));
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(1L, "更新された本", "著者B", "出版社B", LocalDate.of(2022, 5, 5), 5);
        BookEntity updatedEntity = new BookEntity();

        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(updatedEntity);

        Book result = bookService.createBook(book);

        assertEquals("更新された本", result.getTitle());
        verify(bookRepository).save(any(BookEntity.class));
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository).deleteById(bookId);
    }
}
