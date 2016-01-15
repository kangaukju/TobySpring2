package kinow.spring.test.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import kinow.spring.test.dao.UserDao;
import kinow.spring.test.user.Level;
import kinow.spring.test.user.User;


public class UserServiceImpl implements UserService {
	private UserDao userDao; // DAO DI
	private MailSender mailSender; // java mail DI
	
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		if (user.getEmail() == null) user.setEmail("kinow@unet.kr");
		userDao.add(user);
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeUserEmail(user);
	}
	
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {			
			upgradeLevel(user);
		}	
	}
	
	private void sendUpgradeUserEmail(User user) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());
		msg.setFrom("kinow@unet.kr");
		msg.setSubject("Upgrade 안내");
		msg.setText("사용자님의 등급이 "+user.getLevel().name()+"(으)로 업그레이 되었습니다.");
		mailSender.send(msg);
	}
}
