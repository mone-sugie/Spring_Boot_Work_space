package com.college.yi.bookmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.yi.bookmanager.model.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    // JPAが自動でSQLを作ってくれるので、何も書かなくてOK
}
