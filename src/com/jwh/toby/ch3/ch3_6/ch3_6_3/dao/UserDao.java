package com.jwh.toby.ch3.ch3_6.ch3_6_3.dao;

import com.jwh.toby.ch3.ch3_6.ch3_6_3.domain.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(final User user) {
		jdbcTemplate.update("insert into users(id,name,password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?"
			, new Object[] {id},
			new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet resultSet, int i) throws SQLException {
					User user = new User();
					user.setId(resultSet.getString("id"));
					user.setName(resultSet.getString("name"));
					user.setPassword(resultSet.getString("password"));
					return user;
				}
			});
	}

	public void deleteAll() {
		//        jdbcTemplate.update(new PreparedStatementCreator() {
		//            @Override
		//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		//                return connection.prepareStatement("DELETE FROM users");
		//            }
		//        });
		jdbcTemplate.update("DELETE FROM users");
	}

	public int getCount() {
		//        return jdbcTemplate.query(new PreparedStatementCreator() {
		//                              @Override
		//                              public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		//                                  return connection.prepareStatement("SELECT COUNT(*) FROM users");
		//                              }
		//                          },
		//               new ResultSetExtractor<Integer>() {
		//                   @Override
		//                   public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		//                       resultSet.next();
		//                       return resultSet.getInt(1);
		//                   }
		//               }
		//       );
		return jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
	}
}
