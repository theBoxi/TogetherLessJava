package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserIsNotActivatedException extends WebApplicationException {
	private static final long serialVersionUID = 126573109559222302L;

	public UserIsNotActivatedException() {
		super(
			Response.status(462)
			.type(MediaType.TEXT_PLAIN)
			.entity("User is not activated")
			.build()
		);
	}

}
