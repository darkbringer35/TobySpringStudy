package com.jwh.toby.ch6.ch6_7.ch6_7_2.service;

import com.jwh.toby.ch6.ch6_7.ch6_7_2.dao.UserDao;
import com.jwh.toby.ch6.ch6_7.ch6_7_2.domain.Level;
import com.jwh.toby.ch6.ch6_7.ch6_7_2.domain.User;

import java.util.List;

//@Transactional    //fallback 순서가 타겟 클래스가 인터페이스보다 우선되므로 모든 메소드의 트랜잭션은 디폴트 속성을 갖게 된다. readOnlyTransactionAttribute()는 실패
public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private UserLevelUpgradePolicy userLevelUpgradePolicy;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}

	@Override
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (userLevelUpgradePolicy.canUpgradeLevel(user))
				userLevelUpgradePolicy.upgradeLevel(user);
		}
	}

	@Override
	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

	@Override
	public User get(String id) {
		return userDao.get(id);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public void deleteAll() {
		userDao.deleteAll();
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}
}
