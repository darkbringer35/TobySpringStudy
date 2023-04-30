package com.jwh.toby.ch3.ch3_4.ch3_4_2.dao;

import com.jwh.toby.ch3.ch3_4.ch3_4_2.domain.User;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//수동 DI 전략
//클래스의 관계를 외부에 드러내지 않을 수 있다.
//다만 싱글톤으로 사용할 수 없고, 수동적인 DI 작업을 위해 부가적인 작업이 필요하다.
public class UserDao {
	private DataSource dataSource;
	//UserDao에 의해 생성, 관리, DI 제어
	private JdbcContext jdbcContext;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcContext = new JdbcContext();
		jdbcContext.setDataSource(this.dataSource);
	}

	public void add(final User user) throws SQLException {
		jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatememt(Connection c) throws SQLException {
					PreparedStatement ps = c.prepareCall(
						"insert into users(id,name,password) values(?,?,?)");
					ps.setString(1, user.getId());
					ps.setString(2, user.getName());
					ps.setString(3, user.getPassword());
					return ps;
				}
			}
		);
	}

	public User get(String id) throws SQLException {
		Connection conn = dataSource.getConnection();

		PreparedStatement ps = conn
			.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		rs.close();
		ps.close();
		conn.close();

		if (user == null)
			throw new EmptyResultDataAccessException(1);

		return user;
	}

	public void deleteAll() throws SQLException {
		jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatememt(Connection c) throws SQLException {
					return c.prepareStatement("DELETE FROM users");
				}
			}
		);
	}

	public int getCount() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			ps = conn.prepareStatement("SELECT COUNT(*) FROM users");

			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
	}
}
