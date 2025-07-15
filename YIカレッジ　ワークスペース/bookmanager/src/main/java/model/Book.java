package model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 書籍情報を表すモデルクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;              // 書籍ID
    private String title;         // タイトル
    private String author;        // 著者
    private String publisher;     // 出版社
    private LocalDate publishDate; // 出版日
    private int stock;            // 在庫数

}