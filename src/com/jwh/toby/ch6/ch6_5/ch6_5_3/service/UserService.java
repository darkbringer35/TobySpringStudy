package com.jwh.toby.ch6.ch6_5.ch6_5_3.service;

import com.jwh.toby.ch6.ch6_5.ch6_5_3.domain.User;

public interface UserService {
	void upgradeLevels();

	void add(User user);
}
