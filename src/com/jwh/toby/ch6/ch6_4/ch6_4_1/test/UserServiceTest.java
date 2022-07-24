package com.jwh.toby.ch6.ch6_4.ch6_4_1.test;

import com.jwh.toby.ch6.ch6_4.ch6_4_1.dao.UserDao;
import com.jwh.toby.ch6.ch6_4.ch6_4_1.domain.Level;
import com.jwh.toby.ch6.ch6_4.ch6_4_1.domain.User;
import com.jwh.toby.ch6.ch6_4.ch6_4_1.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

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
    @DirtiesContext
    public void upgradeAllorNothing() throws Exception {
        UserLevelUpgradePolicyGeneral testUserLevelUpgradePolicy = new TestUserServicePolicyGeneral(users.get(3).getId());
        testUserLevelUpgradePolicy.setUserDao(this.userDao);
        testUserLevelUpgradePolicy.setMailSender(new DummyMailSender());

        UserServiceImpl testUserService = new UserServiceImpl();
        testUserService.setUserDao(userDao);
        testUserService.setUserLevelUpgradePolicy(testUserLevelUpgradePolicy);

        TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserService);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();

        userDao.deleteAll();
        for (User user : users)
            userDao.add(user);

        try {
            txUserService.upgradeLevels();
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
