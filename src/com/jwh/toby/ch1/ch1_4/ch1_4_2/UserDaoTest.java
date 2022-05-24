package com.jwh.toby.ch1.ch1_4.ch1_4_2;

import com.jwh.toby.ch1.ch1_4.ch1_4_2.dao.DaoFactory;
import com.jwh.toby.ch1.ch1_4.ch1_4_2.dao.MessageDao;
import com.jwh.toby.ch1.ch1_4.ch1_4_2.dao.UserDao;
import com.jwh.toby.ch1.ch1_4.ch1_4_2.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DaoFactory daoFactory = new DaoFactory();
        UserDao userDao = daoFactory.userDao();
        MessageDao messsageDao = daoFactory.messageDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

    }
}
