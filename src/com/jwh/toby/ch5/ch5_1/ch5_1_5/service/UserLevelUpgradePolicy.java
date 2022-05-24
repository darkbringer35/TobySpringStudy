package com.jwh.toby.ch5.ch5_1.ch5_1_5.service;

import com.jwh.toby.ch5.ch5_1.ch5_1_5.domain.User;

public interface UserLevelUpgradePolicy {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
