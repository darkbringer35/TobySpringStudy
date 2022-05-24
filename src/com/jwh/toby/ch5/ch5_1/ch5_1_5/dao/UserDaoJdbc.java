package com.jwh.toby.ch5.ch5_1.ch5_1_5.dao;

import com.jwh.toby.ch5.ch5_1.ch5_1_5.domain.Level;
import com.jwh.toby.ch5.ch5_1.ch5_1_5.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setLevel(Level.valueOf(rs.getInt("level")));
                    user.setLogin(rs.getInt("login"));
                    user.setRecommend(rs.getInt("recommend"));
                    return user;
                }
            };

    public void add(final User user) {
        jdbcTemplate.update("insert into users(id,name,password,level,login,recommend) values(?,?,?,?,?,?)",user.getId(),user.getName(),user.getPassword(),user.getLevel().intValue(),user.getLogin(),user.getRecommend());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?"
                , new Object[]{id},
                userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() {
        return jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    }

    @Override
    public int update(User user) {
        return this.jdbcTemplate.update("update users set name = ?, password = ?, level = ?, login = ?, " +
                                        "recommend = ? where id = ?", user.getName(), user.getPassword(),
                                        user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                                        user.getId());
    }

    public List<User> getAll(){
        return jdbcTemplate.query("SELECT * FROM users ORDER BY id",
                userMapper);
    }
}
