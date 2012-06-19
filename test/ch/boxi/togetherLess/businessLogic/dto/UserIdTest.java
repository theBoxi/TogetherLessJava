package ch.boxi.togetherLess.businessLogic.dto;

import org.junit.Assert;
import org.junit.Test;

public class UserIdTest {
	@Test
	public void testToString(){
		UserID userID = UserID.createFromNewValueWithoutCheckdigit(1l);
		Assert.assertEquals("UID-000.000.001-95", userID.toString());
	}
	
	@Test
	public void testIsValid(){
		UserID userID = UserID.createFromNewValueWithoutCheckdigit(1l);
		Assert.assertTrue(userID.isValid());
	}
}
