package com.jwh.toby.ch5.ch5_2.ch5_2_3.test;

import com.jwh.toby.ch5.ch5_2.ch5_2_3.dao.UserDao;
import com.jwh.toby.ch5.ch5_2.ch5_2_3.domain.Level;
import com.jwh.toby.ch5.ch5_2.ch5_2_3.domain.User;
import com.jwh.toby.ch5.ch5_2.ch5_2_3.service.UserLevelUpgradePolicy;
import com.jwh.toby.ch5.ch5_2.ch5_2_3.service.UserLevelUpgradePolicyGeneral;
import com.jwh.toby.ch5.ch5_2.ch5_2_3.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../applicationContext.xml")
public class UserServiceTest {
	@Autowired
	DataSource dataSource;
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	List<User> users;

	@Before
	public void setUp() {
		users = Arrays.asList(
			new User("bumjin", "박범진", "p1", Level.BASIC, UserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER - 1, 0),
			new User("joytouch", "강명성", "p2", Level.BASIC, UserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER, 0),
			new User("erwins", "신승한", "p3", Level.SILVER, 60, UserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD - 1),
			new User("madnite1", "이승한", "p4", Level.SILVER, 60, UserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD),
			new User("green", "오민규", "p5", Level.GOLD, Integer.MAX_VALUE, Integer.MAX_VALUE)
		);
	}

	@Test
	public void bean() {
		assertThat(this.userService, is(notNullValue()));
	}

	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		userService.upgradeLevels();

		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
	}

	@Test
	public void add() {
		userDao.deleteAll();

		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));
	}

	@Test
	public void upgradeAllorNothing() throws Exception {
		UserLevelUpgradePolicyGeneral testUserLevelUpgradePolicy = new TestUserServicePolicyGeneral(users.get(3).getId());
		testUserLevelUpgradePolicy.setUserDao(this.userDao);

		UserService testUserService = new UserService();
		testUserService.setDataSource(dataSource);
		testUserService.setUserDao(userDao);
		testUserService.setUserLevelUpgradePolicy(testUserLevelUpgradePolicy);

		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {

		}

		checkLevelUpgraded(users.get(1), false);
	}

	public void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded)
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		else
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
	}

	static class TestUserServicePolicyGeneral extends UserLevelUpgradePolicyGeneral {
		private final String id;

		public TestUserServicePolicyGeneral(String id) {
			this.id = id;
		}

		@Override
		public void upgradeLevel(User user) {
			if (user.getId().equals(this.id))
				throw new TestUserServiceException();
			super.upgradeLevel(user);
		}
	}

	static class TestUserServiceException extends RuntimeException {
	}
}
