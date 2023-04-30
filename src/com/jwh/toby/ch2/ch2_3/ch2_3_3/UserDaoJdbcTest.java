package com.jwh.toby.ch2.ch2_3.ch2_3_3;

import com.jwh.toby.ch2.ch2_3.ch2_3_3.dao.UserDao;
import com.jwh.toby.ch2.ch2_3.ch2_3_3.domain.User;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoJdbcTest {
	@Test
	public void count() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("com/jwh/toby/ch2/ch2_3/ch2_3_3/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);

		User user1 = new User("gyumee", "박성철", "springno1");
		User user2 = new User("leegw700", "이길원", "springno2");
		User user3 = new User("bumjin", "박범진", "springno3");

		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.add(user1);
		assertThat(userDao.getCount(), is(1));

		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));

		userDao.add(user3);
		assertThat(userDao.getCount(), is(3));
	}

	//일부러 특정 예외를 일으키는 test
	@Test(expected = EmptyResultDataAccessException.class) //테스트 중에 발생할 것으로 기대하는 예외 클래스 지정
	public void getUserFailure() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("com/jwh/toby/ch2/ch2_3/ch2_3_3/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);

		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.get("unknown_id");
	}

	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("com/jwh/toby/ch2/ch2_3/ch2_3_3/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);

		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		User user = new User();
		user.setId("gyumee");
		user.setName("박성철");
		user.setPassword("springno1");

		userDao.add(user);
		assertThat(userDao.getCount(), is(1));

		User user2 = userDao.get(user.getId());

		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}

	public static void main(String[] args) {
		JUnitCore.main("com.jwh.toby.ch2.ch2_3.ch2_3_3.UserDaoTest");
	}
}
