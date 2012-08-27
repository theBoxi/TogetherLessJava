package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cookielogin")
public class CookieLogin extends Login{
	@Column private String sessionString;
	
	public CookieLogin(){
		super();
	}
	
	public CookieLogin(String sessionString, User user){
		this();
		this.sessionString = sessionString;
//		super.setUser(user);
	}

	public String getSessionString() {
		return sessionString;
	}

	public void setSessionString(String sessionString) {
		this.sessionString = sessionString;
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
			comp = sessionString.compareTo(otherCookieLogin.getSessionString());
		}
		return comp;
	}
}
