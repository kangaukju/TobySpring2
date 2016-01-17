package kinow.spring.test.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kinow.spring.test.user.User;

@Transactional(value="transactionManager")
public interface UserService {	
	public static final int MIN_LOGCOUNT_FOR_SIVER = 50;
	public static final int MIN_RECOMMEND_FRO_GOLD = 30;
	
	void add(User user);
	void delelteAll();
	void update(User user);
	void upgradeLevels();
	
	@Transactional(readOnly=true)
	User get(String id);
	
	@Transactional(readOnly=true)
	List<User> getAll();
	
}
