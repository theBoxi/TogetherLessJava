package ch.boxi.togetherLess.manager;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServerException extends WebApplicationException {
	private static final long serialVersionUID = 7450203192586145908L;
	
	public ServerException(String errorCode, String externalMsg,
			Exception e) {
		super(	e, 
				Response.status(500)
				.type(MediaType.APPLICATION_JSON)
				.entity("{\"errorCode\":\""+errorCode+"\", \"msg\":\""+externalMsg+"\"}")
				.build()
		);
	}

}
