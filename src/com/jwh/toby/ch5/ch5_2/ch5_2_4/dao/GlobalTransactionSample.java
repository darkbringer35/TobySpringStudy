package com.jwh.toby.ch5.ch5_2.ch5_2_4.dao;

import org.springframework.transaction.jta.UserTransactionAdapter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GlobalTransactionSample {
    private DataSource dataSource;

    public void doTransaction() throws SQLException, NamingException {
        /*JNDI를 이용해 서버의 UserTransaction 오브젝트를 가져온다.*/
        InitialContext ctx = new InitialContext();
        //UserTransaction tx = (UserTransaction)ctx.lookup(USER_TX_JNDI_NAME);

        //tx.begin();   /*2.트랜잭션 시작*/
        Connection c = dataSource.getConnection();  /*JNDI로 가져온 dataSource를 사용해야 한다.*/
        try{
            /*3. DAO 메소드 호출*/
            //tx.commit(); /*4. 트랜잭션 커밋*/
        }catch(Exception e) {
            //tx.rollback(); /*5. 트랜잭션 롤백*/
        }finally {
            c.close(); /*6. DB Connection 종료*/
        }
    }
}
