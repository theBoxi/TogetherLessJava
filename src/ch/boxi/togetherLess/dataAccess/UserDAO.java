package ch.boxi.togetherLess.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ch.boxi.togetherLess.businessLogic.dto.CookieLogin;
import ch.boxi.togetherLess.businessLogic.dto.UserLogin;
import ch.boxi.togetherLess.businessLogic.dto.SimpleUserID;
import ch.boxi.togetherLess.businessLogic.dto.User;

public class UserDAO {
	private static Map<SimpleUserID, User> users = new HashMap<SimpleUserID, User>();
	private static Map<String, UserLogin> logins = new TreeMap<String, UserLogin>();
	private static long idCounter = 1000;
	
	static{
		clearinternalCach();
	}
	
	public User getUser(SimpleUserID id){
		return users.get(id);		
	}
	
	public User register(String userName, String password, String firstName, String lastName, String email, int targetWeight, Date targetDate){
		UserLogin login;
		if(logins.get(userName) == null){
			login = new UserLogin(userName, password, null);
			logins.put(userName, login);
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
		UserLogin login = logins.get(userName);
		if(login != null && login.getPassword().equals(password)){
			return login.getUser();
		}
		throw new UserDoesNotExistException();
	}
	
	public void clearCach(){
		UserDAO.clearinternalCach();
	}
	
	private static void clearinternalCach(){
		users.clear();
		logins.clear();
		idCounter = 1000;
	}

	public void addCookieLogin(User user, CookieLogin cookieLogin) {
		user.getLogins().add(cookieLogin);
	}
}
