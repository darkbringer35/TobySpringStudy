package com.jwh.toby.ch5.ch5_2.ch5_2_4.service;

import com.jwh.toby.ch5.ch5_2.ch5_2_4.dao.UserDao;
import com.jwh.toby.ch5.ch5_2.ch5_2_4.domain.Level;
import com.jwh.toby.ch5.ch5_2.ch5_2_4.domain.User;

public class UserLevelUpgradePolicyGeneral implements UserLevelUpgradePolicy {
    private UserDao userDao;

    public void setUserDao(UserDao userDao){ this.userDao = userDao;}

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel){
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
