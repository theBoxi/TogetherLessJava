package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="userlogin")
public class UserLogin extends Login{
	@Column	private String username;
	@Column private String password;
	
	public UserLogin(){
		super();
	}
	
	public UserLogin(String username, String password, User user) {
		this();
		this.username = username;
		this.password = password;
//		super.setUser(user);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public LoginType getLoginType() {
		return LoginType.UserLogin;
	}

	@Override
	public int compareTo(Login other){
		int comp = super.compareTo(other);
		if(comp == 0){
			UserLogin otherUserLogin = (UserLogin) other;
			comp = username.compareTo(otherUserLogin.getUsername());
		}
		return comp;
	}
}
