package com.jwh.toby.ch6.ch6_3.ch6_3_3.service;

import com.jwh.toby.ch6.ch6_3.ch6_3_3.domain.User;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {
	private UserService userService;
	private PlatformTransactionManager transactionManager;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

		try {
			userService.upgradeLevels();

			this.transactionManager.commit(status);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}

	}

	@Override
	public void add(User user) {
		userService.add(user);
	}
}
