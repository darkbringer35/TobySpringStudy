package com.jwh.toby.ch1.ch1_8.ch1_8_4.dao;

import com.jwh.toby.ch1.ch1_8.ch1_8_4.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    //직접 구현한 ConnectionMaker 대신 스프링에서 제공하는 DataSource 인터페이스를 사용한다.
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //dataSource의 getConnection은 ClassNotFoundException을 throw하지 않기 때문에 삭제
    public void add(User user) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareCall(
                "insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public User get(String id) throws SQLException {
        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn
                .prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();

        return user;
    }
}
