package com.jwh.toby.ch5.ch5_1.ch5_1_5.dao;

import com.jwh.toby.ch5.ch5_1.ch5_1_5.domain.User;

import java.util.List;

public interface UserDao {
    public void add(User user);
    public User get(String id);
    public List<User> getAll();
    public void deleteAll();
    public int getCount();
    public int update(User user);
}
