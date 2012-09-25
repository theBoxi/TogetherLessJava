package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserDoesNotExistException extends WebApplicationException {
	private static final long serialVersionUID = 4177936057058634334L;

	public UserDoesNotExistException() {
		super(
			Response.status(471)
			.type(MediaType.TEXT_PLAIN)
			.entity("User does not exist")
			.build()
		);
	}
}
