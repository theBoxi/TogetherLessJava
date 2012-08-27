package ch.boxi.togetherLess.dataAccess.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.SimpleUserID;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;

public class UserDAO {
	private static Map<SimpleUserID, User> users = new HashMap<SimpleUserID, User>();
	private static Map<String, UserLogin> userLogins = new TreeMap<String, UserLogin>();
	private static Map<String, CookieLogin> cookieLogins = new TreeMap<String, CookieLogin>();
	private static long idCounter = 1000;
	
	static{
		clearinternalCach();
	}
	
	public User getUser(SimpleUserID id){
		return users.get(id);		
	}
	
	public User register(String userName, String password, String firstName, String lastName, String email, int targetWeight, Date targetDate){
		UserLogin login;
		if(userLogins.get(userName) == null){
			login = new UserLogin(userName, password, null);
			userLogins.put(userName, login);
		}else{
			throw new UserAllreadyExistsException();
		}
		long id = idCounter++;
		User user = new User(id, firstName, lastName, email, targetWeight, targetDate, login);
		login.setUser(user);
		users.put(user.getId(), user);
		return user;
	}
	
	public User login(String userName, String password){
		UserLogin login = userLogins.get(userName);
		if(login != null && login.getPassword().equals(password)){
			return login.getUser();
		}
		throw new UserDoesNotExistException();
	}
	
	public User login(String sessionID){
		CookieLogin login = cookieLogins.get(sessionID);
		if(login != null){
			return login.getUser();
		}
		throw new UserDoesNotExistException();
	}
	
	public void clearCach(){
		UserDAO.clearinternalCach();
	}
	
	private static void clearinternalCach(){
		users.clear();
		userLogins.clear();
		cookieLogins.clear();
		idCounter = 1000;
	}

	public void addCookieLogin(User user, CookieLogin cookieLogin) {
		cookieLogin.setUser(user);
		user.getLogins().add(cookieLogin);
		cookieLogins.put(cookieLogin.getSessionString(), cookieLogin);
	}
}
