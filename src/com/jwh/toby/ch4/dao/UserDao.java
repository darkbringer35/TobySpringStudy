package com.jwh.toby.ch4.dao;

import com.jwh.toby.ch4.domain.User;

import java.util.List;

public interface UserDao {
	void add(User user);

	User get(String id);

	List<User> getAll();

	void deleteAll();

	int getCount();
}
