package ch.boxi.togetherLess.manager;

import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.UUID;

import ch.boxi.togetherLess.dataAccess.DaoLocator;
import ch.boxi.togetherLess.dataAccess.user.dao.PasswordUtility;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.exception.UserDoesNotExistException;
import ch.boxi.togetherLess.dataAccess.user.dao.exception.UserIsNotActivatedException;
import ch.boxi.togetherLess.dataAccess.user.dto.ActivationCode;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.Login;
import ch.boxi.togetherLess.dataAccess.user.dto.LoginType;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.UserState;
import ch.boxi.togetherLess.emailService.EmailServiceConsoleImpl;
import ch.boxi.togetherLess.emailService.Emailtemplate;
import ch.boxi.togetherLess.exception.LanguageDependentText;

public class UserManager {
	
	public User register(String userName, 
			String password,
			String password2,
			String firstName, 
			String lastName, 
			String email){
		UserDAO userDAO = DaoLocator.getUserDAO();
		String passwordHash;
		try {
			passwordHash = PasswordUtility.hashPassword(password);
		} catch (Exception e) {
			LanguageDependentText msg = new LanguageDependentText();
			msg.put("de", "Server Fehler");
			msg.put("en", "Server error");
			throw new ServerException("1000", msg, e);
		}
		User user = userDAO.register(userName, passwordHash, firstName, lastName, email);
		sendActivationMail(user);
		return user;
	}
	
	
	public CookieLogin login(String userName, String password){
		UserDAO userDAO = DaoLocator.getUserDAO();
		User user = userDAO.getUser(userName);
		
		Boolean passwordOk = false;
		try{
			passwordOk = PasswordUtility.checkPasswird(password, user.getUserLogin().getPassword());
		}catch(Exception e){
			LanguageDependentText msg = new LanguageDependentText();
			msg.put("de", "Server Fehler");
			msg.put("en", "Server error");
			throw new ServerException("1000", msg, e);
		}
		
		if(!passwordOk){
			throw new UserDoesNotExistException();
		} else if(user.getState() == UserState.registered){	
			userDAO.addActivationCode(user, renew(user.getActivatinCode()));
			sendActivationMail(user);
			throw new UserIsNotActivatedException(user.getEmail());
		} else{
			CookieLogin cookieLogin = createCookieLogin(user);
			return cookieLogin;
		}
	}
	
	
	private ActivationCode renew(ActivationCode activationCode){
		ActivationCode newCode = new ActivationCode(null, 10);
		activationCode.setValidUntil(newCode.getValidUntil());
		activationCode.setCode(newCode.getCode());
		return activationCode;
	}
	
	
	public void activateUser(String activationCode){
		UserDAO userDAO = DaoLocator.getUserDAO();
		userDAO.activateUser(activationCode);
	}
	
	
	public User loadUser(Login login){
		if(LoginType.CookieLogin == login.getLoginType()){
			return loadUser((CookieLogin)login);
		} else if(LoginType.UserLogin == login.getLoginType()){
			UserLogin userLogin = (UserLogin)login;
			CookieLogin cookieLogin = login(userLogin.getUsername(), userLogin.getPassword());
			return loadUser(cookieLogin);
		} else{
			return null;
		}
	}
	
	
	public User loadUser(CookieLogin login){
		UserDAO userDAO = DaoLocator.getUserDAO();
		User user = userDAO.login(login.getSessionID());
		return user;
	}
	
	
	private CookieLogin createCookieLogin(User user){
		UserDAO userDAO = DaoLocator.getUserDAO();
		UUID sessionID = UUID.randomUUID();
		CookieLogin cookieLogin = new CookieLogin(sessionID.toString(), user);
		userDAO.addCookieLogin(user, cookieLogin);
		return cookieLogin;
	}
	
	
	private void sendActivationMail(User user){
		EmailServiceConsoleImpl emailService = new EmailServiceConsoleImpl();
		emailService.sendMail(user.getEmail(), Emailtemplate.Register, getRegistrationMailProperties(user));
	}
	
	
	private Properties getRegistrationMailProperties(User user){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy hh:mm");
		Properties properties = new Properties();
		properties.setProperty("firstName", user.getFirstName());
		properties.setProperty("lasName", user.getLastName());
		properties.setProperty("userName", user.getUserLogin().getUsername());
		properties.setProperty("activationCode", user.getActivatinCode().getCode());
		properties.setProperty("validUntil", sdf.format(user.getActivatinCode().getValidUntil()));
		return properties;
	}
}
