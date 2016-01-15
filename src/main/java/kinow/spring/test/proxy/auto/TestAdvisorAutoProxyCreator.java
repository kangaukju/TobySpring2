package kinow.spring.test.proxy.auto;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kinow.spring.test.dao.UserDao;
import kinow.spring.test.exception.TestUserServiceException;
import kinow.spring.test.pointcut.Bean;
import kinow.spring.test.pointcut.Target;

import static kinow.spring.test.service.UserService.*;
import kinow.spring.test.service.UserService;
import kinow.spring.test.service.UserServiceImpl;
import kinow.spring.test.user.Level;
import kinow.spring.test.user.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="TestAdvisorAutoProxyCreator.xml")
public class TestAdvisorAutoProxyCreator {
	List<User> users;
	@Autowired UserDao userDao;
	@Autowired UserService userService;
	@Autowired UserService testUserService;
	
/*	
	@Autowired List<TestAspectJExp> testAspectJExps;
	@Test
	public void testAspectJExp() throws NoSuchMethodException, SecurityException {
		for (TestAspectJExp e : testAspectJExps) {
			AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
			pointcut.setExpression("execution("+e.getExp()+")");
			
			System.out.println(
					String.format(
							"[%s] class:%s(%s) method:%s(%s)",
							e.getExp(),
							e.getClassClazz().getSimpleName(),
							pointcut.getClassFilter().matches(e.getClassClazz()),
							e.getMethod(),
							pointcut.getMethodMatcher().matches(e.getClassClazz().getMethod(e.getMethod(), e.getArgs().toArray(new Class<?>[e.getArgs().size()])), null)
							));
			
		}
	}
*/

/*
	@Autowired AspectJExpressionPointcut transactionPointcutAspecJ;
	@Test
	public void testTransactionPointcutAspecJ() {
		assertThat(transactionPointcutAspecJ.getClassFilter().matches(TestUserService.class), is(true));
		assertThat(transactionPointcutAspecJ.getClassFilter().matches(UserServiceImpl.class), is(true));
	}
*/
	
	@Test
	public void classView() throws NoSuchMethodException, SecurityException {
		System.out.println(Target.class.getMethod("minus", int.class, int.class));
	}
	
	@Test
	public void methodSignaturePointcut() throws NoSuchMethodException, SecurityException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		
		String pointcutExp = "execution(public int kinow.spring.test.pointcut.Target.minus(int,int) throws java.lang.RuntimeException)";
		pointcut.setExpression(pointcutExp);
		
		assertThat(
				pointcut.getClassFilter().matches(Target.class) &&
				pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null),
				 is(true));
		
		assertThat(
				pointcut.getClassFilter().matches(Target.class) &&
				pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null), 
				is(false));
		
		assertThat(
				pointcut.getClassFilter().matches(Bean.class) &&
				pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null),
				is(false));		
	}
	
//	@Test
	public void checkUserServiceBean() {
		System.out.println(userService.getClass().getName());
		Assert.assertThat(userService, Is.is(java.lang.reflect.Proxy.class));
	}
	
	@Test
	public void upgrade() {
		for (User user : users) {
			userDao.add(user);
		}
		
		userService.upgradeLevels();
		
		checkLevelUpgrade(users.get(1), true);
	}
	@Test
	public void upgradeAllOrNothing() {
		for (User user : users) {
			userDao.add(user);
		}
		
		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected...");
		} catch(Exception e) {
		}
		
		checkLevelUpgrade(users.get(1), false);
	}
	static class TestUserService extends UserServiceImpl {
		private String id = "kinow3";
		
		protected void upgradeLevel(User user) {
			if (user.getId().equals(id)) {
				throw new TestUserServiceException();
			}
			super.upgradeLevel(user);
		}
	}
	
	
	@Before
	public void setUp() {
		users = Arrays.asList(
				new User("kinow0", "kaka0", "p0", Level.BASIC, MIN_LOGCOUNT_FOR_SIVER-1, 0, "kinow@naver.com"),
				new User("kinow1", "kaka1", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SIVER, 0, "kinow@naver.com"),
				new User("kinow2", "kaka2", "p2", Level.SILVER, 60, MIN_RECOMMEND_FRO_GOLD-1, "kinow@naver.com"),
				new User("kinow3", "kaka3", "p3", Level.SILVER, 60, MIN_RECOMMEND_FRO_GOLD, "kinow@naver.com")
				);
		userDao.deleteAll();
	}
	
	private void checkLevelUpgrade(User user, boolean upgrade) {
		User userUpdate = userDao.get(user.getId());
		if (upgrade) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
}
