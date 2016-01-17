package kinow.spring.test.dao;

import java.util.List;

import kinow.spring.test.user.User;


public interface UserDao {
	public void add(User user);
	public int getCount();
	public User get(String id);
	public List<User> getAll();
	public void update(User user);
	public void deleteAll();
	public void hitPlus(String id);
}
