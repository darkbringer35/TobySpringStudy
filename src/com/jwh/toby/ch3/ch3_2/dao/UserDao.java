package com.jwh.toby.ch3.ch3_2.dao;

import com.jwh.toby.ch3.ch3_2.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = stmt.makePreparedStatememt(conn);
            ps.executeUpdate();
        }catch(SQLException e){
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch(SQLException e){

                }
            }

            if(conn != null){
                try {
                    conn.close();
                } catch(SQLException e){

                }
            }
        }
    }

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

        User user = null;
        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        conn.close();

        if(user == null)
            throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void deleteAll() throws SQLException{
        StatementStrategy strategy = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(strategy);
    }

    public int getCount() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = dataSource.getConnection();

            ps = conn.prepareStatement("SELECT COUNT(*) FROM users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            throw e;
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){

                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch(SQLException e){

                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch(SQLException e){

                }
            }
        }
    }
}
