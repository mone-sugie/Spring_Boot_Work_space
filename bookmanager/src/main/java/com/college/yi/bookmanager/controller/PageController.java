package com.college.yi.bookmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.college.yi.bookmanager.model.BookEntity;
import com.college.yi.bookmanager.repository.BookRepository;

@Controller
public class PageController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String showBookPage(Model model, Authentication authentication) {
        // 書籍リストを取得
        List<BookEntity> books = bookRepository.findAll();
        model.addAttribute("books", books);

        // ログインユーザーのロールを取得して渡す（Thymeleafで切り替え表示に使える）
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());
        }

        return "index"; // templates/index.html を返す
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html を表示（必要なら作成）
    }
}
