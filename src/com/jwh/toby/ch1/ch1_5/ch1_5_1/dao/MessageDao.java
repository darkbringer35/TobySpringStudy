package com.jwh.toby.ch1.ch1_5.ch1_5_1.dao;

import com.jwh.toby.ch1.ch1_5.ch1_5_1.domain.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDao {
    private final ConnectionMaker connectionMaker;

    public MessageDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void add(Message message) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps = conn.prepareCall(
                "insert into users(id,title,content) values(?,?,?)");
        ps.setString(1, message.getId());
        ps.setString(2, message.getTitle());
        ps.setString(3, message.getContent());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public Message get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn
                .prepareStatement("select * from messages where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        Message message = new Message();
        message.setId(rs.getString("id"));
        message.setTitle(rs.getString("title"));
        message.setContent(rs.getString("content"));

        rs.close();
        ps.close();
        conn.close();

        return message;
    }
}
