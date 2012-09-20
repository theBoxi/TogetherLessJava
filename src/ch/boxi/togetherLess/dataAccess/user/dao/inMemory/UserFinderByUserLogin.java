package ch.boxi.togetherLess.dataAccess.user.dao.inMemory;

import ch.boxi.togetherLess.dataAccess.user.dto.Login;
import ch.boxi.togetherLess.dataAccess.user.dto.LoginType;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;

public class UserFinderByUserLogin implements UserFinder {
	private String userName;
	
	public UserFinderByUserLogin(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean isThisUser(User user) {
		for(Login login: user.getLogins()){
			if(login.getLoginType() == LoginType.UserLogin){
				UserLogin userLogin = (UserLogin) login;
				if(userLogin.getUsername().equals(userName)){
					return true;
				}
			}
		}
		return false;
	}

}
