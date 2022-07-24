package com.jwh.toby.ch6.ch6_2.ch6_2_4.service;

import com.jwh.toby.ch6.ch6_2.ch6_2_4.dao.UserDao;
import com.jwh.toby.ch6.ch6_2.ch6_2_4.domain.Level;
import com.jwh.toby.ch6.ch6_2.ch6_2_4.domain.User;

import java.util.List;

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
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
