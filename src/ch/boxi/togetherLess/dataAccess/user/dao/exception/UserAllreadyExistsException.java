package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserAllreadyExistsException extends WebApplicationException {
	private static final long serialVersionUID = -5995503971969892180L;

	public UserAllreadyExistsException() {
		super(
			Response.status(470)
			.type(MediaType.TEXT_PLAIN)
			.entity("User allready exists")
			.build()
		);
	}

}
