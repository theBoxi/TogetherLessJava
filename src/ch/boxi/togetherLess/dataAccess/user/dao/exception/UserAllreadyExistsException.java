package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import ch.boxi.togetherLess.dataAccess.exception.LanguageDependentText;
import ch.boxi.togetherLess.dataAccess.exception.TogetherLessException;

public class UserAllreadyExistsException extends TogetherLessException {
	private static final long serialVersionUID = -5995503971969892180L;

	private static LanguageDependentText msg = new LanguageDependentText();
	static{
		msg.put("de", "Benutzer existiert bereits");
		msg.put("en", "User allready exists");
	}
	
	public UserAllreadyExistsException() {
		super(470, "1002", msg, null);
	}

}
