package ch.boxi.togetherLess.manager;

import ch.boxi.togetherLess.dataAccess.exception.LanguageDependentText;
import ch.boxi.togetherLess.dataAccess.exception.TogetherLessException;

public class ServerException extends TogetherLessException {
	private static final long serialVersionUID = 7450203192586145908L;

	public ServerException(String tglErrorCode, LanguageDependentText msg,
			Exception e) {
		super(tglErrorCode, msg, e);
	}

	public ServerException(String tglErrorCode, LanguageDependentText msg) {
		super(tglErrorCode, msg);
	}

}
