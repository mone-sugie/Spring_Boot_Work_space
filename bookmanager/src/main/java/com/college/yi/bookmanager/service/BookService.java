package com.college.yi.bookmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.college.yi.bookmanager.model.Book;
import com.college.yi.bookmanager.model.BookEntity;
import com.college.yi.bookmanager.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 全件取得（Entityのまま返す）
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    // 全件取得（DTOに変換）
    public List<Book> getBooks() {
        List<BookEntity> entities = bookRepository.findAll();

        if (entities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "書籍が見つかりませんでした。");
        }

        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    // 書籍作成
    public Book createBook(Book book) {
        BookEntity entity = toEntity(book);
        BookEntity saved = bookRepository.save(entity);
        return toModel(saved);
    }

    // 書籍更新（IDを引数として渡すスタイル）
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "更新対象の書籍が見つかりません");
        }

        BookEntity entity = toEntity(book);
        entity.setId(id);
        BookEntity updated = bookRepository.save(entity);
        return toModel(updated);
    }

    // 書籍削除
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "削除対象の書籍が見つかりません");
        }
        bookRepository.deleteById(bookId);
    }

    // Book → BookEntity の変換
    private BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPublisher(book.getPublisher());
        entity.setPublishedDate(book.getPublishedDate());
        entity.setStock(book.getStock());
        return entity;
    }

    // BookEntity → Book の変換
    private Book toModel(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublisher(),
                entity.getPublishedDate(),
                entity.getStock()
        );
    }
}
