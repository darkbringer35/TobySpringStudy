package com.jwh.toby.ch6.ch6_5.ch6_5_7.service;

import com.jwh.toby.ch6.ch6_5.ch6_5_7.domain.User;

public interface UserService {
	void upgradeLevels();

	void add(User user);
}
