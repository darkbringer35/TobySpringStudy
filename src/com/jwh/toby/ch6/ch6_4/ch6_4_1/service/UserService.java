package com.jwh.toby.ch6.ch6_4.ch6_4_1.service;

import com.jwh.toby.ch6.ch6_4.ch6_4_1.domain.User;

public interface UserService {
	void upgradeLevels();

	void add(User user);
}
