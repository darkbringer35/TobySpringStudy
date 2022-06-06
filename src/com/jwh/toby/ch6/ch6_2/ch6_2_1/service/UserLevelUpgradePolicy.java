package com.jwh.toby.ch6.ch6_2.ch6_2_1.service;

import com.jwh.toby.ch6.ch6_2.ch6_2_1.domain.User;

public interface UserLevelUpgradePolicy {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
    void sendUpgradeEmail(User user);
}
