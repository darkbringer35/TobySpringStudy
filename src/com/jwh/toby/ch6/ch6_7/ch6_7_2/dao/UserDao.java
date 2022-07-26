package com.jwh.toby.ch6.ch6_7.ch6_7_2.dao;

import com.jwh.toby.ch6.ch6_7.ch6_7_2.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(String id);

    List<User> getAll();

    void deleteAll();

    int getCount();

    void update(User user);
}
