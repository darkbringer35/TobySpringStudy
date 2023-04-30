package com.jwh.toby.ch1.ch1_3.ch1_3_1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB 연결 기능을 담당하는 독립적인 클래스
public class SimpleConnectionMaker {
	public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver"); // jdbc driver 클래스 동적 로딩
		Connection conn = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/springbook?characterEncoding=UTF-8", "spring", "book");
		return conn;
	}
}
