package com.jwh.toby.ch6.ch6_3.ch6_3_4.test;

import com.jwh.toby.ch6.ch6_3.ch6_3_4.dao.UserDao;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.domain.Level;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.domain.User;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.service.UserLevelUpgradePolicy;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.service.UserLevelUpgradePolicyGeneral;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../applicationContext.xml")
public class UserLevelUpgradePolicyTest {
	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;

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
		assertThat(this.userLevelUpgradePolicy, is(notNullValue()));
	}

	@Test
	public void upgradeLevels() throws Exception {
		UserLevelUpgradePolicyGeneral policy = new UserLevelUpgradePolicyGeneral();

		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		policy.setUserDao(mockUserDao);

		MailSender mockMailSender = mock(MailSender.class);
		policy.setMailSender(mockMailSender);

		//UserServiceImpl.upgradeLevels() 대체
		mockUserDao.getAll().forEach(user -> {
			if (policy.canUpgradeLevel(user))
				policy.upgradeLevel(user);
		});

		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.GOLD));

		ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
	}
}
