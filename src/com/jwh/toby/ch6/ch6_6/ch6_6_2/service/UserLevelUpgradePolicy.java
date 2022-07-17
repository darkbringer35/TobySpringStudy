package com.jwh.toby.ch6.ch6_6.ch6_6_2.service;

import com.jwh.toby.ch6.ch6_6.ch6_6_2.domain.User;

public interface UserLevelUpgradePolicy {
    int MIN_LOGCOUNT_FOR_SILVER = 50;
    int MIN_RECOMMEND_FOR_GOLD = 30;

    boolean canUpgradeLevel(User user);

    void upgradeLevel(User user);

    void sendUpgradeEmail(User user);
}
