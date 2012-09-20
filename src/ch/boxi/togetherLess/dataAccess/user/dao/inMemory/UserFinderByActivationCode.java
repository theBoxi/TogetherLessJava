package ch.boxi.togetherLess.dataAccess.user.dao.inMemory;

import ch.boxi.togetherLess.dataAccess.user.dto.ActivationCode;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

public class UserFinderByActivationCode implements UserFinder {
	private String activationCode;
	
	public UserFinderByActivationCode(String activationCode) {
		super();
		this.activationCode = activationCode;
	}

	@Override
	public boolean isThisUser(User user) {
		ActivationCode code = user.getActivatinCode();
		if(code != null){
			code.getCode().equals(activationCode);
		}
		return false;
	}

}
