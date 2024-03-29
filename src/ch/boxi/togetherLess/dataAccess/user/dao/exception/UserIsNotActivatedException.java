package ch.boxi.togetherLess.dataAccess.user.dao.exception;

import java.util.Map;
import java.util.TreeMap;

import ch.boxi.togetherLess.exception.LanguageDependentText;
import ch.boxi.togetherLess.exception.TogetherLessException;

public class UserIsNotActivatedException extends TogetherLessException {
	private static final long serialVersionUID = 126573109559222302L;

	private String emailForResendingActivationCode;
	
	private static LanguageDependentText msg = new LanguageDependentText();
	static{
		msg.put("de", "noch nicht activiert");
		msg.put("en", "not jet activated");
	}
	
	public UserIsNotActivatedException(String emailForResendingActivationCode) {
		super(462, "1003", msg, createAdditionalFieldsMap(emailForResendingActivationCode), null);
		this.emailForResendingActivationCode = emailForResendingActivationCode;
	}
	
	public String getEmailForResendingActivationCode(){
		return emailForResendingActivationCode;
	}

	private static Map<String, String> createAdditionalFieldsMap(String emailForResendingActivationCode){
		Map<String, String> fields = new TreeMap<String, String>();
		fields.put("email", emailForResendingActivationCode);
		return fields;
	}
}
