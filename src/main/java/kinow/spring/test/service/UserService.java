package kinow.spring.test.service;

import kinow.spring.test.user.User;

public interface UserService {	
	public static final int MIN_LOGCOUNT_FOR_SIVER = 50;
	public static final int MIN_RECOMMEND_FRO_GOLD = 30;
	
	void add(User user);
	void upgradeLevels();
}
