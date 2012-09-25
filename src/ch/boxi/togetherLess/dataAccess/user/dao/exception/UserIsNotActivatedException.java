package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserIsNotActivatedException extends WebApplicationException {
	private static final long serialVersionUID = 126573109559222302L;

	private String emailForResendingActivationCode;
	
	public UserIsNotActivatedException(String emailForResendingActivationCode) {
		super(
			Response.status(462)
			.type(MediaType.APPLICATION_JSON)
			.entity("{\"msg\":\"User is not activated\", \"email\":\""+emailForResendingActivationCode+"\"}")
			.build()
		);
		
		this.emailForResendingActivationCode = emailForResendingActivationCode;
	}
	
	public String getEmailForResendingActivationCode(){
		return emailForResendingActivationCode;
	}

}
