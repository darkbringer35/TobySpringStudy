package com.jwh.toby.ch1.ch1_2.ch1_2_1.dao;

import com.jwh.toby.ch1.ch1_2.ch1_2_1.domain.User;

import java.sql.*;

//1.connection 객체의 생성 부분의 함수화
public class UserDao {
    private Connection getConntection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); // jdbc driver 클래스 동적 로딩
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook?characterEncoding=UTF-8","spring","book");
        return conn;
    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = getConntection();
        PreparedStatement ps = conn.prepareCall(
                "insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = getConntection();

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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

    }
}
