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

    public List<Book> getBooks() {
        List<BookEntity> entities = bookRepository.findAll();

        if (entities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "書籍が見つかりませんでした。");
        }

        return entities.stream()
                .map(e -> new Book(
                        e.getId(),
                        e.getTitle(),
                        e.getAuthor(),
                        e.getPublisher(),
                        e.getPublishedDate(),
                        e.getStock()
                ))
                .collect(Collectors.toList());
    }

public List<BookEntity> findAllBooks() {
    return bookRepository.findAll(); // 全件取得
}

}
