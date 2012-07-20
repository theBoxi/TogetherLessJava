package ch.boxi.togetherLess.dataAccess;

public class UserAllreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -5995503971969892180L;

	public UserAllreadyExistsException() {
		super();
	}

	public UserAllreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAllreadyExistsException(String message) {
		super(message);
	}

	public UserAllreadyExistsException(Throwable cause) {
		super(cause);
	}

}
