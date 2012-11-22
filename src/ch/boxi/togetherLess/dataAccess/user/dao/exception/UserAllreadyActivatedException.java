package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import ch.boxi.togetherLess.exception.LanguageDependentText;
import ch.boxi.togetherLess.exception.TogetherLessException;

public class UserAllreadyActivatedException extends TogetherLessException {
	private static final long serialVersionUID = 1L;

	private static LanguageDependentText msg = new LanguageDependentText();
	
	static{
		msg.put("de", "Benutzer ist schon activiert");
		msg.put("en", "User allready activated");
	}
	
	public UserAllreadyActivatedException() {
		super(461, "1001", msg, null);
	}
}
