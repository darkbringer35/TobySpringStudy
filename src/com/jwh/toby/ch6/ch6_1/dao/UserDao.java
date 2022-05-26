package com.jwh.toby.ch6.ch6_1.dao;

import com.jwh.toby.ch6.ch6_1.domain.User;

import java.util.List;

public interface UserDao {
    public void add(User user);
    public User get(String id);
    public List<User> getAll();
    public void deleteAll();
    public int getCount();
    public int update(User user);
}
