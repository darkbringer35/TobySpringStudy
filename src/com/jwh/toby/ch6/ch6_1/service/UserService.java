package com.jwh.toby.ch6.ch6_1.service;

import com.jwh.toby.ch6.ch6_1.domain.User;

public interface UserService {
	void upgradeLevels();

	void add(User user);
}
