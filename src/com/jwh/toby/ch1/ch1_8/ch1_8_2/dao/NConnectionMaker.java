package com.jwh.toby.ch1.ch1_8.ch1_8_2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//N사용 DB connection maker
public class NConnectionMaker implements ConnectionMaker {
	public NConnectionMaker() {
	}

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver"); // jdbc driver 클래스 동적 로딩
		Connection conn = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/springbook?characterEncoding=UTF-8", "spring", "book");
		return conn;
	}
}
