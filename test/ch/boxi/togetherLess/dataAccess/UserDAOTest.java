package ch.boxi.togetherLess.dataAccess;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.boxi.togetherLess.businessLogic.dto.User;

public class UserDAOTest {
	private UserDAO dao = new UserDAO();
	
	@Before
	public void setUp(){
		dao.clearCach();
	}
	
	@Test
	public void testRegisterUser(){
		User user = dao.register("login", "tester11", "Tester", "11", "tester11@bluewin.ch", 80, new Date());
		User user2 = dao.getUser(user.getId());
		Assert.assertEquals(user, user2);
	}
	
	@Test
	public void testLogin(){
		User user = dao.register("login", "tester11", "Tester", "11", "tester11@bluewin.ch", 80, new Date());
		User user2 = dao.login("login", "tester11");
		Assert.assertEquals(user, user2);
	}
}
