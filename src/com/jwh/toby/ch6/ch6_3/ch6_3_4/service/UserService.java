package com.jwh.toby.ch6.ch6_3.ch6_3_4.service;

import com.jwh.toby.ch6.ch6_3.ch6_3_4.domain.User;

public interface UserService {
    void upgradeLevels();

    void add(User user);
}
