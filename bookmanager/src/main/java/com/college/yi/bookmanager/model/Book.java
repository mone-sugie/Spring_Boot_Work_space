package com.college.yi.bookmanager.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;              // 書籍ID
    private String title;         // タイトル
    private String author;        // 著者
    private String publisher;     // 出版社
    private LocalDate publishedDate; // 出版日
    private int stock;            // 在庫数
}
