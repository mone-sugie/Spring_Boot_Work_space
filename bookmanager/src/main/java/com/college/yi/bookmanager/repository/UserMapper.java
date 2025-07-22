package com.college.yi.bookmanager.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.college.yi.bookmanager.model.User;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
}
