package com.jwh.toby.ch1.ch1_3.ch1_3_3;

import com.jwh.toby.ch1.ch1_3.ch1_3_3.dao.NConnectionMaker;
import com.jwh.toby.ch1.ch1_3.ch1_3_3.dao.UserDao;
import com.jwh.toby.ch1.ch1_3.ch1_3_3.domain.User;

import java.sql.SQLException;

//UserDao와 ConnectionMaker 사이의 의존관계를 설정해주는 UserDao의 클라이언트
public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao(new NConnectionMaker());

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

    }
}
