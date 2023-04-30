package com.jwh.toby.ch1.ch1_7.ch1_7_4.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	int counter = 0;
	private final ConnectionMaker realConnectionMaker;

	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}

	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		this.counter++;
		return realConnectionMaker.makeConnection();
	}

	public int getCounter() {
		return this.counter;
	}
}
