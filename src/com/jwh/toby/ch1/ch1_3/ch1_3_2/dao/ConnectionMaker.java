package com.jwh.toby.ch1.ch1_3.ch1_3_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

//DB connection을 생성하는 인터페이스
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
