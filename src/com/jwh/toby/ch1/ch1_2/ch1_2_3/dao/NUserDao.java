package com.jwh.toby.ch1.ch1_2.ch1_2_3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//N사용 Dao 클래스
public class NUserDao extends UserDao {
    //템플릿 메서드, 팩토리 메서드
    @Override
    public Connection getConntection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver"); // jdbc driver 클래스 동적 로딩
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook?characterEncoding=UTF-8", "spring", "book");
        return conn;
    }
}
