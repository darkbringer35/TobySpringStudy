package com.jwh.toby.ch4;

import com.jwh.toby.ch4.dao.UserDao;
import com.jwh.toby.ch4.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class UserDaoTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private DataSource dataSource; //DB의 에러코드 가져오기용
	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setUp() {
		this.user1 = new User("gyumee", "박성철", "springno1");
		this.user2 = new User("leegw700", "이길원", "springno2");
		this.user3 = new User("bumjin", "박범진", "springno3");
	}

	@Test
	public void count() {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.add(user1);
		assertThat(userDao.getCount(), is(1));

		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));

		userDao.add(user3);
		assertThat(userDao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.get("unknown_id");
	}

	@Test
	public void addAndGet() {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.add(user1);
		assertThat(userDao.getCount(), is(1));

		user2 = userDao.get(user1.getId());

		assertThat(user2.getName(), is(user1.getName()));
		assertThat(user2.getPassword(), is(user1.getPassword()));
	}

	@Test
	public void getAll() {
		userDao.deleteAll();

		List<User> users0 = userDao.getAll();
		assertThat(users0.size(), is(0));

		userDao.add(user1);
		List<User> users1 = userDao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));

		userDao.add(user2);
		List<User> users2 = userDao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));

		userDao.add(user3);
		List<User> users3 = userDao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user3, users3.get(0)); //orderBy 결과를 맞추기 위해
		checkSameUser(user1, users3.get(1));
		checkSameUser(user2, users3.get(2));
	}

	@Test(expected = DataAccessException.class)
	public void duplicateKey() {
		userDao.deleteAll();

		userDao.add(user1);
		userDao.add(user1);
	}

	//SQLException에서 DuplicateKeyException으로 전환하는 기능 확인
	@Test
	public void sqlExceptionTranslate() {
		userDao.deleteAll();

		try {
			userDao.add(user1);
			userDao.add(user1);
		} catch (DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException)ex.getRootCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			assertThat(set.translate(null, null, sqlEx), is(DuplicateKeyException.class));
		}
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}

	public static void main(String[] args) {
		JUnitCore.main("com.jwh.toby.ch4.UserDaoTest");
	}
}
