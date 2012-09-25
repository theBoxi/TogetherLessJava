package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ActivationCodeOutOfDateException extends WebApplicationException {
	private static final long serialVersionUID = 126573109559222302L;

	public ActivationCodeOutOfDateException() {
		super(
			Response.status(460)
			.type(MediaType.TEXT_PLAIN)
			.entity("ActivationCode is out of date")
			.build()
		);
	}

}
