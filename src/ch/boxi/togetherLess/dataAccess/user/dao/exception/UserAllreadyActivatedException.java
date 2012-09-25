package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserAllreadyActivatedException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	public UserAllreadyActivatedException() {
		super(
			Response.status(461)
			.type(MediaType.TEXT_PLAIN)
			.entity("User allready activated")
			.build()
		);
	}
}
