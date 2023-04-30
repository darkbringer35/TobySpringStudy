package com.jwh.toby.ch6.ch6_5.ch6_5_7.test;

import com.jwh.toby.ch6.ch6_5.ch6_5_7.domain.Level;
import com.jwh.toby.ch6.ch6_5.ch6_5_7.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../applicationContext.xml")
public class UserTest {
	User user;

	@Before
	public void setUp() {
		user = new User();
	}

	@Test
	public void upgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() == null)
				continue;
			user.setLevel(level);
			user.upgradeLevel();
			assertThat(user.getLevel(), is(level.nextLevel()));
		}
	}

	@Test(expected = IllegalStateException.class)
	public void cannotUpgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() != null)
				continue;
			user.setLevel(level);
			user.upgradeLevel();
		}
	}
}
