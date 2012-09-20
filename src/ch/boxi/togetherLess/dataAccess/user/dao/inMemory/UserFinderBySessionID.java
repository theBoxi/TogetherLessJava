package ch.boxi.togetherLess.dataAccess.user.dao.inMemory;

import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.Login;
import ch.boxi.togetherLess.dataAccess.user.dto.LoginType;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

public class UserFinderBySessionID implements UserFinder {
	private String sessionID;
	
	public UserFinderBySessionID(String sessionID){
		super();
		this.sessionID = sessionID;
	}
	
	@Override
	public boolean isThisUser(User user) {
		for(Login login: user.getLogins()){
			if(login.getLoginType() == LoginType.CookieLogin){
				CookieLogin cookieLogin = (CookieLogin) login;
				if(cookieLogin.getSessionID().equals(sessionID)){
					return true;
				}
			}
		}
		return false;
	}

}
