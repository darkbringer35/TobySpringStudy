package com.jwh.toby.ch3.ch3_4.ch3_4_2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	PreparedStatement makePreparedStatememt(Connection c) throws SQLException;
}
