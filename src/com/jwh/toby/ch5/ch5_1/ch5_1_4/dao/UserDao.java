package com.jwh.toby.ch5.ch5_1.ch5_1_4.dao;

import com.jwh.toby.ch5.ch5_1.ch5_1_4.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(String id);

    List<User> getAll();

    void deleteAll();

    int getCount();

    int update(User user);
}
