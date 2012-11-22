package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import ch.boxi.togetherLess.exception.LanguageDependentText;
import ch.boxi.togetherLess.exception.TogetherLessException;

public class UserDoesNotExistException extends TogetherLessException {
	private static final long serialVersionUID = 4177936057058634334L;

	private static LanguageDependentText msg = new LanguageDependentText();
	static{
		msg.put("de", "Benutzer unbekannt");
		msg.put("en", "User does not exist");
	}
	
	public UserDoesNotExistException() {
		super(471, "1002", msg, null);
	}
}
