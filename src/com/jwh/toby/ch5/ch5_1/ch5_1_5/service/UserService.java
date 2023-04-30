package com.jwh.toby.ch5.ch5_1.ch5_1_5.service;

import com.jwh.toby.ch5.ch5_1.ch5_1_5.dao.UserDao;
import com.jwh.toby.ch5.ch5_1.ch5_1_5.domain.Level;
import com.jwh.toby.ch5.ch5_1.ch5_1_5.domain.User;

import java.util.List;

public class UserService {
	private UserDao userDao;
	private UserLevelUpgradePolicy userLevelUpgradePolicy;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}

	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (userLevelUpgradePolicy.canUpgradeLevel(user))
				userLevelUpgradePolicy.upgradeLevel(user);
		}
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
