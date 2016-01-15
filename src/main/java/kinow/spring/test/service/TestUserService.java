package kinow.spring.test.service;

import kinow.spring.test.exception.TestUserServiceException;
import kinow.spring.test.user.User;

public class TestUserService extends UserServiceImpl {
	private String id;
	
	public TestUserService(String id) {
		this.id = id;
	}
	
	@Override
	protected void upgradeLevel(User user) {
		if (user.getId().equals(id)) {
			throw new TestUserServiceException();
		}
		super.upgradeLevel(user);
	}

}
