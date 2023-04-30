package com.jwh.toby.ch6.ch6_5.ch6_5_3.test;

import com.jwh.toby.ch6.ch6_5.ch6_5_3.dao.UserDao;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.domain.Level;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.domain.User;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.pointcut.Bean;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.pointcut.Target;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.service.UserLevelUpgradePolicy;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.service.UserLevelUpgradePolicyGeneral;
import com.jwh.toby.ch6.ch6_5.ch6_5_3.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	ApplicationContext context;

	@Autowired
	@Qualifier(value = "userService")
	UserService userService;

	@Autowired
	@Qualifier(value = "testUserService")
	UserService testUserService;

	@Autowired
	UserDao userDao;
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

	@Test
	public void advisorAutoProxyCreator() {
		assertThat(testUserService, is(java.lang.reflect.Proxy.class));
	}

	@Test
	public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(public int " +
			"com.jwh.toby.ch6.ch6_5.ch6_5_3.pointcut.Target.minus(int, int) " +
			"throws java.lang.RuntimeException)");

		//다형성이 적용 가능 확인
		//        pointcut.setExpression("execution(public int " +
		//                "com.jwh.toby.ch6.ch6_5.ch6_5_3.pointcut.TargetInterface.minus(int, int) " +
		//                "throws java.lang.RuntimeException)");

		//Target.minus()
		assertThat(pointcut.getClassFilter().matches(Target.class) &
			pointcut.getMethodMatcher().matches(
				Target.class.getMethod("minus", int.class, int.class), null), is(true));

		//Target.plus()
		assertThat(pointcut.getClassFilter().matches(Target.class) &&
			pointcut.getMethodMatcher().matches(
				Target.class.getMethod("plus", int.class, int.class), null), is(false));

		//Bean.method()
		assertThat(pointcut.getClassFilter().matches(Bean.class) &&
			pointcut.getMethodMatcher().matches(
				Target.class.getMethod("method"), null), is(false));
	}

	@Test
	public void pointcut() throws Exception {
		targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
	}

	public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception {
		pointcutMatches(expression, expected[0], Target.class, "hello");
		pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
		pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
		pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
		pointcutMatches(expression, expected[4], Target.class, "method");
		pointcutMatches(expression, expected[5], Bean.class, "method");
	}

	public void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);

		assertThat(pointcut.getClassFilter().matches(clazz)
			&& pointcut.getMethodMatcher().matches(clazz.getMethod(methodName,
			args), null), is(expected));
	}

	public void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded)
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		else
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
	}

	static class TestUserServicePolicyGeneral extends UserLevelUpgradePolicyGeneral {
		private final String id = "madnite1";

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
