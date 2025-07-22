package com.college.yi.bookmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.college.yi.bookmanager.repository.UserMapper;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;  // MyBatisのMapper（DBアクセス）

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. DBからユーザー情報を取得
        com.college.yi.bookmanager.model.User user = userMapper.findByUsername(username);

        // 2. ユーザーが見つからなければ例外を投げる
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 3. UserDetailsオブジェクトを返す
        return User.builder()
                .username(user.getUsername())    // ユーザー名
                .password(user.getPassword())    // パスワード（ハッシュ済み）
                .roles(user.getRole())            // 役割（例："USER"）
                .disabled(!user.getEnabled())      // 有効かどうか（falseなら有効）
                .build();
    }
}
