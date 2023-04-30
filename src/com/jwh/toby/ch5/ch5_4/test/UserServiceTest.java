package com.jwh.toby.ch5.ch5_4.test;

import com.jwh.toby.ch5.ch5_4.dao.UserDao;
import com.jwh.toby.ch5.ch5_4.domain.Level;
import com.jwh.toby.ch5.ch5_4.domain.User;
import com.jwh.toby.ch5.ch5_4.service.DummyMailSender;
import com.jwh.toby.ch5.ch5_4.service.UserLevelUpgradePolicy;
import com.jwh.toby.ch5.ch5_4.service.UserLevelUpgradePolicyGeneral;
import com.jwh.toby.ch5.ch5_4.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
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
	UserLevelUpgradePolicy userLevelUpgradePolicy;
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	@Autowired
	PlatformTransactionManager transactionManager;
	List<User> users;

	@Before
	public void setUp() {
		users = Arrays.asList(
			new User("bumjin", "박범진", "p1", "user1@ksug.org", Level.BASIC, UserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER - 1, 0),
			new User("joytouch", "강명성", "p2", "user2@ksug.org", Level.BASIC, UserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER, 0),
			new User("erwins", "신승한", "p3", "user3@ksug.org", Level.SILVER, 60, UserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD - 1),
			new User("madnite1", "이승한", "p4", "user4@ksug.org", Level.SILVER, 60, UserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD),
			new User("green", "오민규", "p5", "user5@ksug.org", Level.GOLD, Integer.MAX_VALUE, Integer.MAX_VALUE)
		);
	}

	@Test
	public void bean() {
		assertThat(this.userService, is(notNullValue()));
	}

	@Test
	@DirtiesContext //컨텍스트의 DI 설정을 변경하는 테스트
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		MockMailSender mockMailSender = new MockMailSender();
		UserLevelUpgradePolicyGeneral policy = (UserLevelUpgradePolicyGeneral)userLevelUpgradePolicy;
		policy.setMailSender(mockMailSender);

		userService.upgradeLevels();

		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);

		List<String> requests = mockMailSender.getRequests();
		assertThat(requests.size(), is(2));
		assertThat(requests.get(0), is(users.get(1).getEmail()));
		assertThat(requests.get(1), is(users.get(3).getEmail()));
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
		testUserLevelUpgradePolicy.setMailSender(new DummyMailSender());

		UserService testUserService = new UserService();
		testUserService.setUserDao(userDao);
		testUserService.setTransactionManager(transactionManager);
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

	static class MockMailSender implements MailSender {
		private final List<String> requests = new ArrayList();

		public List<String> getRequests() {
			return requests;
		}

		@Override
		public void send(SimpleMailMessage mailMessage) throws MailException {
			requests.add(mailMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {

		}
	}
}
