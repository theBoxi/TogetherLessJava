package ch.boxi.togetherLess.businessLogic.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Login {
	private String username;
	private String password;
	
	@XmlTransient
	private User user;
	
	public Login(){
		super();
	}
	
	public Login(String username, String password, User user) {
		this();
		this.username = username;
		this.password = password;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
