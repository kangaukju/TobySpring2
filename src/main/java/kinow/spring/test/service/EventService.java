package kinow.spring.test.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kinow.spring.test.user.User;

@Transactional(value="transactionManager")
public interface EventService {
	void processDailyEventRegistration(List<User> users);
}
