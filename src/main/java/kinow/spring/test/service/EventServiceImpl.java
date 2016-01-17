package kinow.spring.test.service;

import java.util.List;

import kinow.spring.test.user.User;

public class EventServiceImpl implements EventService {

	UserService userService;
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void processDailyEventRegistration(List<User> users) {
		for (User user : users) {
			userService.add(user);
		}
	}
}
