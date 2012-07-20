package ch.boxi.togetherLess.dataAccess;

public class UserDoesNotExistException extends RuntimeException {
	private static final long serialVersionUID = 4177936057058634334L;

	public UserDoesNotExistException() {
		super();
	}

	public UserDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDoesNotExistException(String message) {
		super(message);
	}

	public UserDoesNotExistException(Throwable cause) {
		super(cause);
	}

}
