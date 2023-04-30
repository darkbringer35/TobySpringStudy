package com.jwh.toby.ch5.ch5_2.ch5_2_4.dao;

import com.jwh.toby.ch5.ch5_2.ch5_2_4.domain.User;

import java.util.List;

public interface UserDao {
	void add(User user);

	User get(String id);

	List<User> getAll();

	void deleteAll();

	int getCount();

	int update(User user);
}
