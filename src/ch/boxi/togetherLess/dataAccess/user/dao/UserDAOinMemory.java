package ch.boxi.togetherLess.dataAccess.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;

public class UserDAOinMemory implements UserDAO {
	private static Map<Integer, User> users = new HashMap<>();
	private static Map<String, UserLogin> userLogins = new TreeMap<String, UserLogin>();
	private static Map<String, CookieLogin> cookieLogins = new TreeMap<String, CookieLogin>();
	private static Integer idCounter = 1000;
	
	static{
		clearinternalCach();
	}
	
	@Override
	public User getUser(Integer id){
		return users.get(id);		
	}
	
	@Override
	public User register(String userName, String password, String firstName, String lastName, String email, int targetWeight, Date targetDate){
		UserLogin login;
		if(userLogins.get(userName) == null){
			login = new UserLogin(userName, password, null);
			userLogins.put(userName, login);
		}else{
			throw new UserAllreadyExistsException();
		}
		Integer id = idCounter++;
		User user = new User(id, firstName, lastName, email, targetWeight, targetDate, login);
		login.setUser(user);
		users.put(user.getId(), user);
		return user;
	}
	
	@Override
	public User login(String userName, String password){
		UserLogin login = userLogins.get(userName);
		if(login != null && login.getPassword().equals(password)){
			return login.getUser();
		}
		throw new UserDoesNotExistException();
	}
	
	@Override
	public User login(String sessionID){
		CookieLogin login = cookieLogins.get(sessionID);
		if(login != null){
			return login.getUser();
		}
		throw new UserDoesNotExistException();
	}
	
	@Override
	public void clearCach(){
		UserDAOinMemory.clearinternalCach();
	}
	
	private static void clearinternalCach(){
		users.clear();
		userLogins.clear();
		cookieLogins.clear();
		idCounter = 1000;
	}

	@Override
	public void addCookieLogin(User user, CookieLogin cookieLogin) {
		cookieLogin.setUser(user);
		user.getLogins().add(cookieLogin);
		cookieLogins.put(cookieLogin.getSessionID(), cookieLogin);
	}

	@Override
	public boolean isUserNameFree(String userName) {
		return userLogins.get(userName) == null;
	}
}
