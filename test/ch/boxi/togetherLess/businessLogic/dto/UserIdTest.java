package ch.boxi.togetherLess.businessLogic.dto;

import org.junit.Assert;
import org.junit.Test;

import ch.boxi.javaUtil.id.decorator.validation.ValidationException;

public class UserIdTest {
	@Test
	public void testToString(){
		TGLBaseID userID = UserID.createFromNewValueWithoutCheckdigit(1l);
		Assert.assertEquals("UID-000.000.001-95", userID.toString());
	}
	
	@Test
	public void testIsValid(){
		TGLBaseID userID;

		userID = UserID.createFromNewValueWithoutCheckdigit(1l);
		Assert.assertTrue(userID.isValid());
		
		userID = UserID.createFromDbRepresentiv(295l, false);
		Assert.assertFalse(userID.isValid());
		
		Exception exception = null;
		try{
			UserID.createFromDbRepresentiv(295l, true);
			Assert.assertTrue(false);
		} catch(ValidationException e){
			exception = e;
		} finally{
			Assert.assertNotNull(exception);
		}
	}
}
