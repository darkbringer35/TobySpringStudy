package com.jwh.toby.ch6.ch6_8.ch6_8_2.service;

import com.jwh.toby.ch6.ch6_8.ch6_8_2.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional  //<tx:method name="*"/>과 같은 설정
public interface UserService {
    void add(User user);

    void deleteAll();

    void update(User user);

    void upgradeLevels();

    @Transactional(readOnly = true)
    User get(String id);

    @Transactional(readOnly = true)
    List<User> getAll();
}
