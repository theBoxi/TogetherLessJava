package ch.boxi.togetherLess.dataAccess.user.dao.inMemory;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.exception.ActivationCodeOutOfDateException;
import ch.boxi.togetherLess.dataAccess.user.dao.exception.UserAllreadyExistsException;
import ch.boxi.togetherLess.dataAccess.user.dao.exception.UserDoesNotExistException;
import ch.boxi.togetherLess.dataAccess.user.dto.ActivationCode;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.Login;
import ch.boxi.togetherLess.dataAccess.user.dto.LoginType;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.UserState;

public class UserDAOinMemory implements UserDAO {
	private static List<User> users = new LinkedList<>();
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
		if(!isUserNameFree(userName)){
			throw new UserAllreadyExistsException();
		}
		UserLogin login = new UserLogin(userName, password, null);
		Set<Login> logins = new TreeSet<>();
		logins.add(login);
		
		ActivationCode activationcode = new ActivationCode(null, 10);
		
		Integer id = idCounter++;
		User user = new User(
				id, 
				firstName, 
				lastName, 
				email, 
				targetWeight, 
				UserState.registered, 
				new Date(), 
				targetDate, 
				logins,
				activationcode); 
		login.setUser(user);
		activationcode.setUser(user);
		users.add(user);
		return user;
	}
	
	@Override
	public User login(String userName, String password){
		User user = getUser(new UserFinderByUserLogin(userName));
		if(user != null){
			for(Login login: user.getLogins()){
				if(login.getLoginType() == LoginType.UserLogin){
					UserLogin userLogin = (UserLogin) login;
					if(userLogin.getPassword().equals(password)){
						return login.getUser();
					}
				}
			}
		}
		throw new UserDoesNotExistException();
	}
	
	@Override
	public User login(String sessionID){
		User user = getUser(new UserFinderBySessionID(sessionID));
		if(user != null){
			return user;
		}
		throw new UserDoesNotExistException();
	}
	
	@Override
	public void clearCach(){
		UserDAOinMemory.clearinternalCach();
	}
	
	private static void clearinternalCach(){
		users.clear();
		idCounter = 1000;
	}

	@Override
	public void addCookieLogin(User user, CookieLogin cookieLogin) {
		cookieLogin.setUser(user);
		user.getLogins().add(cookieLogin);
	}

	@Override
	public boolean isUserNameFree(String userName) {
		User user = getUser(new UserFinderByUserLogin(userName));
		return user == null;
	}
	
	private User getUser(UserFinder finder){
		for(User user: users){
			if(finder.isThisUser(user)){
				return user;
			}
		}
		return null;
	}

	@Override
	public void activateUser(String activationCode) {
		User user = getUser(new UserFinderByActivationCode(activationCode));
		if(user != null){
			if(user.getActivatinCode().getValidUntil().before(new Date())){
				throw new ActivationCodeOutOfDateException();
			}
			user.setState(UserState.active);
		}else{
			throw new UserDoesNotExistException();
		}
	}
}
