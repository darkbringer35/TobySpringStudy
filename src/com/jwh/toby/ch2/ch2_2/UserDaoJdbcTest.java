package com.jwh.toby.ch2.ch2_2;

import com.jwh.toby.ch2.ch2_2.dao.UserDao;
import com.jwh.toby.ch2.ch2_2.domain.User;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoJdbcTest {
    @Test
    public void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("com/jwh/toby/ch2/ch2_2/applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("gyumee");
        user.setName("박성철");
        user.setPassword("springno1");

        userDao.add(user);

        User user2 = userDao.get(user.getId());

        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));
    }

    public static void main(String[] args){
        JUnitCore.main("com.jwh.toby.ch2.ch2_2.UserDaoTest");
    }
}
