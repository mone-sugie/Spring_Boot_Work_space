package com.college.yi.bookmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.model.BookEntity;
import com.college.yi.bookmanager.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // 書籍登録（POST）
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        BookEntity entity = new BookEntity();
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPublisher(book.getPublisher());
        entity.setPublishedDate(book.getPublishedDate());
        entity.setStock(book.getStock());

        BookEntity saved = bookRepository.save(entity);
        book.setId(saved.getId());

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // 書籍一覧取得（GET）
    @GetMapping
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        List<BookEntity> books = bookRepository.findAll();

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    // 書籍更新（PUT）
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        BookEntity existingEntity = bookRepository.findById(id).orElse(null);
        if (existingEntity == null) {
            return ResponseEntity.notFound().build();
        }

        existingEntity.setTitle(book.getTitle());
        existingEntity.setAuthor(book.getAuthor());
        existingEntity.setPublisher(book.getPublisher());
        existingEntity.setPublishedDate(book.getPublishedDate());
        existingEntity.setStock(book.getStock());

        BookEntity updatedEntity = bookRepository.save(existingEntity);

        book.setId(updatedEntity.getId());
        return ResponseEntity.ok(book);
    }

    // 書籍削除（DELETE）
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
