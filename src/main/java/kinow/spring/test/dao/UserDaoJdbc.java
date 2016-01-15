package kinow.spring.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kinow.spring.test.exception.DuplicateUserIdException;
import kinow.spring.test.user.Level;
import kinow.spring.test.user.User;


public class UserDaoJdbc implements UserDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		// dataSource를 받는 곳에서 아예 JdbcTemplate을 생성한다.
		// JdbcTemplate 사용을 외부에 공개할 필요없는 사항이므로 굳이 DI(Interface)로 만들어서 
		// bean으로 관리 할 필요가 없을거 같다.
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void deleteAll() {
		this.jdbcTemplate.update("DELETE FROM users");
	}
	
	public void add(User user) throws DuplicateUserIdException {
		try {
			this.jdbcTemplate.update(
					"INSERT INTO users(id, name, password, level, login, recommend, email) " + 
					"VALUES (?,?,?,?,?,?,?)",
					user.getId(), user.getName(), user.getPassword(),
					user.getLevel().intValue(), user.getLogin(), user.getRecommend(), 
					user.getEmail());
		}
		catch (DuplicateKeyException e) {
			throw new DuplicateUserIdException(e.getMessage(), e);
		}
	}
	
	public int getCount() {
		return this.jdbcTemplate.queryForInt("SELECT count(*) FROM users");
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(
					rs.getString("id"),
					rs.getString("name"),
					rs.getString("password"),
					Level.valueOf(rs.getInt("level")),
					rs.getInt("login"),
					rs.getInt("recommend"),
					rs.getString("email")
					);
		}
	};
	
	public User get(String id) {		
		return this.jdbcTemplate.queryForObject(
				"SELECT * FROM users WHERE id = ?", 
				new Object[] {id},
				userMapper);
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query(
				"SELECT * FROM users order by id",
				userMapper);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
				"update users set name=?, password=?, level=?, login=?, recommend=?, "+
				"email = ? "+
				"where id=?",
				user.getName(), user.getPassword(), user.getLevel().intValue(), 
				user.getLogin(), user.getRecommend(), user.getEmail(),
				user.getId());
	}
	
}
