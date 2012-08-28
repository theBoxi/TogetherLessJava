package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cookielogin")
public class CookieLogin extends Login{
	@Column private String sessionID;
	
	public CookieLogin(){
		super();
	}
	
	public CookieLogin(String sessionString, User user){
		this();
		this.sessionID = sessionString;
		super.setUser(user);
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	@Override
	public LoginType getLoginType() {
		return LoginType.CookieLogin;
	}
	
	@Override
	public int compareTo(Login other){
		int comp = super.compareTo(other);
		if(comp == 0){
			CookieLogin otherCookieLogin = (CookieLogin) other;
			comp = sessionID.compareTo(otherCookieLogin.getSessionID());
		}
		return comp;
	}
}
