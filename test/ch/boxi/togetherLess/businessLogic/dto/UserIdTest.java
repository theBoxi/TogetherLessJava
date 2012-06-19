package ch.boxi.togetherLess.businessLogic.dto;

import org.junit.Assert;
import org.junit.Test;

public class UserIdTest {
	@Test
	public void testToString(){
		UserID userID = new UserID(1l);
		Assert.assertEquals("UID-000.000.001", userID.toString());
	}
}
