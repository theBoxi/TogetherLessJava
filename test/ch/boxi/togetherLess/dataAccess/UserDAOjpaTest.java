package ch.boxi.togetherLess.dataAccess;

import org.junit.Assert;
import org.junit.Test;

import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAOjpa;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.test.AbstractUnitTest;

public class UserDAOjpaTest extends AbstractUnitTest {

	private UserDAO dao = new UserDAOjpa();
	
//	@Test
//	public void readConfig() throws Exception{
//		File localDir = new File("./");
//		System.out.println(localDir.getAbsoluteFile());
//		BufferedReader in = new BufferedReader(new FileReader("hibernate.cfg.xml"));
//		while(in.ready()){
//			System.out.println(in.readLine());
//		}
//		in.close();
//	}
	
	@Test
	public void testRegisterUser() throws Exception{
		User user = dao.register("login", "tester11", "Tester", "11", "tester11@bluewin.ch");
		User user2 = dao.getUser("login");
		Assert.assertEquals(user, user2);
	}
}
