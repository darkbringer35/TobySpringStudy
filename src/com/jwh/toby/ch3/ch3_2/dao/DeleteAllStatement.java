package com.jwh.toby.ch3.ch3_2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {
	@Override
	public PreparedStatement makePreparedStatememt(Connection c) throws SQLException {
		return c.prepareStatement("DELETE FROM users");
	}
}
