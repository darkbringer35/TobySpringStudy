package com.jwh.toby.ch1.ch1_7.ch1_7_4;

import com.jwh.toby.ch1.ch1_7.ch1_7_4.dao.CountingConnectionMaker;
import com.jwh.toby.ch1.ch1_7.ch1_7_4.dao.DaoFactory;
import com.jwh.toby.ch1.ch1_7.ch1_7_4.dao.MessageDao;
import com.jwh.toby.ch1.ch1_7.ch1_7_4.dao.UserDao;
import com.jwh.toby.ch1.ch1_7.ch1_7_4.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //애플리케이션 컨텍스트에 설정 정보 등록후 빈 오브젝트 생성
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //단순하게 bean 이름만 넣으면 Object 타입이 반환되기 때문에 반환된 클래스 타입을 인자로 넣어준다.
        UserDao userDao = context.getBean("userDao", UserDao.class);
        MessageDao messsageDao = context.getBean("messageDao", MessageDao.class);

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

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter :" + ccm.getCounter());
    }
}
