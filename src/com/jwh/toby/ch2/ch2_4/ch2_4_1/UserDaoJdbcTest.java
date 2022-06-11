package com.jwh.toby.ch2.ch2_4.ch2_4_1;

import com.jwh.toby.ch2.ch2_4.ch2_4_1.dao.UserDao;
import com.jwh.toby.ch2.ch2_4.ch2_4_1.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class) //스프링 테스트 컨텍스트 프레임워크의 JUnit 확장가능 지정
@ContextConfiguration(locations = "applicationContext.xml")
//@DirtiesContext //테스트 메소드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
//@ContextConfiguration(locations = "test-applicationContext.xml")
public class UserDaoJdbcTest {
    //    @Autowired
//    private ApplicationContext context; //ApplicationContext는 초기화할 때 자기 자신도 bean으로 등록하기 때문에 굳이 선언할 필요없다.
    @Autowired  //테스트 코드를 위해서는 userDao를 DI로 받지 않고 새로 생성하는게 나을 수 있다.
    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        //DataSource dataSource =new SingleConnectionDataSource(...);
        //dao.setDataSource(dataSource);

        this.user1 = new User("gyumee", "박성철", "springno1");
        this.user2 = new User("leegw700", "이길원", "springno2");
        this.user3 = new User("bumjin", "박범진", "springno3");
    }

    @Test
    public void count() throws SQLException {
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
    public void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("unknown_id");
    }

    @Test
    public void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        assertThat(userDao.getCount(), is(1));

        user2 = userDao.get(user1.getId());

        assertThat(user2.getName(), is(user1.getName()));
        assertThat(user2.getPassword(), is(user1.getPassword()));
    }

    public static void main(String[] args) {
        JUnitCore.main("com.jwh.toby.ch2.ch2_4.ch2_4_1.UserDaoTest");
    }
}
