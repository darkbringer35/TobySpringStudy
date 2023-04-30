package com.jwh.toby.ch1.ch1_5.ch1_5_1.dao;

import java.sql.Connection;
import java.sql.SQLException;

//DB connection을 생성하는 인터페이스
public interface ConnectionMaker {
	Connection makeConnection() throws ClassNotFoundException, SQLException;
}
