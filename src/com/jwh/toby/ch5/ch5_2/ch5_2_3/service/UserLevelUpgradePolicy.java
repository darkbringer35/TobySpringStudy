package com.jwh.toby.ch5.ch5_2.ch5_2_3.service;

import com.jwh.toby.ch5.ch5_2.ch5_2_3.domain.User;

public interface UserLevelUpgradePolicy {
	int MIN_LOGCOUNT_FOR_SILVER = 50;
	int MIN_RECOMMEND_FOR_GOLD = 30;

	boolean canUpgradeLevel(User user);

	void upgradeLevel(User user);
}
