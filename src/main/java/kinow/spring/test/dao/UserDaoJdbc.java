package kinow.spring.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kinow.spring.test.exception.DuplicateUserIdException;
import kinow.spring.test.exception.UnableUpdateUserHITCountException;
import kinow.spring.test.sql.SqlService;
import kinow.spring.test.user.Level;
import kinow.spring.test.user.User;


public class UserDaoJdbc implements UserDao {
	private SqlService sqlService;
	private JdbcTemplate jdbcTemplate;
	
	
	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	public void setDataSource(DataSource dataSource) {
		// dataSource를 받는 곳에서 아예 JdbcTemplate을 생성한다.
		// JdbcTemplate 사용을 외부에 공개할 필요없는 사항이므로 굳이 DI(Interface)로 만들어서 
		// bean으로 관리 할 필요가 없을거 같다.
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void deleteAll() {
		this.jdbcTemplate.update(sqlService.getSql("userDeleteAll"));
	}
	
	public void add(User user) throws DuplicateUserIdException {
		try {
			this.jdbcTemplate.update(sqlService.getSql("userAdd"),
					user.getId(), user.getName(), user.getPassword(),
					user.getLevel().intValue(), user.getLogin(), user.getRecommend(), 
					user.getEmail());
		}
		catch (DuplicateKeyException e) {
			throw new DuplicateUserIdException(e.getMessage(), e);
		}
	}
	
	public int getCount() {
		return this.jdbcTemplate.queryForInt(sqlService.getSql("userGetCount"));
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
					rs.getString("email"),
					rs.getLong("hit")
					);
		}
	};
	
	public User get(String id) {		
		User user = this.jdbcTemplate.queryForObject(sqlService.getSql("userGet"),
				new Object[] {id},
				userMapper);
		return user;
	}
	
	public void hitPlus(String id) throws UnableUpdateUserHITCountException {
		try {
			this.jdbcTemplate.update(sqlService.getSql("userHitPlus"), id);
		} catch (DataAccessException e) {
			throw new UnableUpdateUserHITCountException(e.getMessage(), e);
		}
		
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query(sqlService.getSql("userGetAll"), userMapper);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(sqlService.getSql("userUpdate"),
				user.getName(), user.getPassword(), user.getLevel().intValue(), 
				user.getLogin(), user.getRecommend(), user.getEmail(),
				user.getId());
	}
	
}
