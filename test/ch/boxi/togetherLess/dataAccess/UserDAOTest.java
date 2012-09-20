package ch.boxi.togetherLess.dataAccess;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.inMemory.UserDAOinMemory;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

public class UserDAOTest {
	private UserDAO dao = new UserDAOinMemory();
	
	@Before
	public void setUp(){
		dao.clearCach();
	}
	
	@Test
	public void testRegisterUser() throws Exception{
		User user = dao.register("login", "tester11", "Tester", "11", "tester11@bluewin.ch", 80, new Date());
		User user2 = dao.getUser(user.getId());
		Assert.assertEquals(user, user2);
	}
	
	@Test
	public void testLogin() throws Exception{
		User user = dao.register("login", "tester11", "Tester", "11", "tester11@bluewin.ch", 80, new Date());
		User user2 = dao.login("login", "tester11");
		Assert.assertEquals(user, user2);
	}
}
