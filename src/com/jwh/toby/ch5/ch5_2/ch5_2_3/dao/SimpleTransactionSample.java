package com.jwh.toby.ch5.ch5_2.ch5_2_3.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleTransactionSample {
    private DataSource dataSource;


    public void doTransaction() throws SQLException {
        Connection c = dataSource.getConnection(); //1. DBConnection 생성

        c.setAutoCommit(false); //2.트랜잭션 시작
        try {
            //3. DAO 메소드 호출
            PreparedStatement st1 = c.prepareStatement("UPDATE users ...");
            st1.executeUpdate();

            PreparedStatement st2 = c.prepareStatement("DELETE users ...");
            st2.executeUpdate();

            c.commit(); //4. 트랜잭션 커밋
        } catch (Exception e) {
            c.rollback(); //5. 트랜잭션 롤백
        }

        c.close(); //6. DB Connection 종료
    }
}
