package ch.boxi.togetherLess.manager;

import ch.boxi.togetherLess.exception.LanguageDependentText;
import ch.boxi.togetherLess.exception.TogetherLessException;

public class NotAuthorizedException extends TogetherLessException {
	private static final long serialVersionUID = 914681634284953189L;
	
	public static final String tglErrorCode = "1000";
	public static final LanguageDependentText msg;
	
	static{
		msg = new LanguageDependentText()
			.add("en", "Your not authorized to read/modify this users data!")
			.add("de", "Sie sind nicht befugt die Daten dieses Users zu lesen/editieren!");
	}
	
	public NotAuthorizedException() {
		super(tglErrorCode, msg);
	}
}
