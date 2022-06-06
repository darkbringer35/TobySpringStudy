package com.jwh.toby.ch6.ch6_2.ch6_2_1.test;

import com.jwh.toby.ch6.ch6_2.ch6_2_1.dao.UserDao;
import com.jwh.toby.ch6.ch6_2.ch6_2_1.domain.Level;
import com.jwh.toby.ch6.ch6_2.ch6_2_1.domain.User;
import com.jwh.toby.ch6.ch6_2.ch6_2_1.service.UserLevelUpgradePolicy;
import com.jwh.toby.ch6.ch6_2.ch6_2_1.service.UserLevelUpgradePolicyGeneral;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../applicationContext.xml")
public class UserLevelUpgradePolicyTest {
    @Autowired
    UserLevelUpgradePolicy userLevelUpgradePolicy;

    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin","박범진","p1","user1@ksug.org", Level.BASIC, UserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER-1,0),
                new User("joytouch","강명성","p2","user2@ksug.org", Level.BASIC, UserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER,0),
                new User("erwins","신승한","p3","user3@ksug.org", Level.SILVER,60, UserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD-1),
                new User("madnite1","이승한","p4","user4@ksug.org", Level.SILVER,60, UserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD),
                new User("green","오민규","p5","user5@ksug.org", Level.GOLD,Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

    @Test
    public void bean(){
        assertThat(this.userLevelUpgradePolicy , is(notNullValue()));
    }

    @Test
    public void upgradeLevels() throws Exception {
        UserLevelUpgradePolicyGeneral policy = new UserLevelUpgradePolicyGeneral();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        policy.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        policy.setMailSender(mockMailSender);

        //UserServiceImpl.upgradeLevels() 대체
        mockUserDao.getAll().forEach(user-> {
            if (policy.canUpgradeLevel(user))
                policy.upgradeLevel(user);
        });

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size(),is(2));

        checkUserAndLevel(updated.get(0),"joytouch",Level.SILVER);
        checkUserAndLevel(updated.get(1),"madnite1",Level.GOLD);

        List<String> requests = mockMailSender.getRequests();
        assertThat(requests.size(),is(2));
        assertThat(requests.get(0),is(users.get(1).getEmail()));
        assertThat(requests.get(1),is(users.get(3).getEmail()));
    }

    public static void checkUserAndLevel(User updated, String expectedId, Level expectedLevel){
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
    }

    public static class MockMailSender implements MailSender {
        private List<String> requests =new ArrayList();

        public List<String> getRequests(){
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

    public static class MockUserDao implements UserDao {
        private List<User> users;
        private List<User> updated = new ArrayList();

        public MockUserDao(List<User> users){
            this.users = users;
        }

        public List<User> getUpdated(){
            return this.updated;
        }

        @Override
        public List<User> getAll() {
            return this.users;
        }

        @Override
        public void update(User user) {
            updated.add(user);
        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }
    }
}
