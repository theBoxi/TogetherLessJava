package ch.boxi.togetherLess.dataAccess.user.dao;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;

import ch.boxi.togetherLess.dataAccess.jpaHelper.AbstractHibernateDAO;
import ch.boxi.togetherLess.dataAccess.user.dto.ActivationCode;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.Login;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.UserState;

public class UserDAOjpa extends AbstractHibernateDAO implements UserDAO {

	@Override
	public User getUser(Integer id) {
		return null;
	}

	@Override
	public User register(String userName, String password, String firstName,
			String lastName, String email, int targetWeight, Date targetDate) throws Exception {
		Session session = takeTransaction();
		
		String passwordHash = PasswordUtility.hashPassword(password);
		UserLogin login = new UserLogin(userName, passwordHash, null);
		Set<Login> logins = new TreeSet<>();
		logins.add(login);
		
		ActivationCode activationCode = new ActivationCode(null, 10);
		
		User user = new User(
				null, // ID will be Set by JPA
				firstName, 
				lastName, 
				email, 
				targetWeight, 
				UserState.registered, 
				new Date(), 
				targetDate, 
				logins,
				activationCode);
		
		login.setUser(user);
		activationCode.setUser(user);
		
		session.save(user);
		session.save(login);
		session.save(activationCode);
		session.getTransaction().commit();
		return user;
	}
	
	@Override
	public User login(String userName, String password) throws Exception {
		Session session = takeTransaction();
		Query query = session.createQuery("from UserLogin where username = '" + userName + "'");
		UserLogin userLogin = (UserLogin)query.uniqueResult();
		session.close();
		if(userLogin == null){
			throw new UserDoesNotExistException();
		}
		String savedPassword = userLogin.getPassword();
		if(!PasswordUtility.checkPasswird(password, savedPassword)){
			throw new UserDoesNotExistException();
		}
		return userLogin.getUser();
	}

	@Override
	public User login(String sessionID) {
		Session session = takeTransaction();
		Query query = session.createQuery("from CookieLogin where sessionID = '" + sessionID + "'");
		CookieLogin cookieLogin = (CookieLogin) query.uniqueResult();
		session.close();
		if(cookieLogin == null){
			throw new UserDoesNotExistException();
		}
		return cookieLogin.getUser();
	}

	@Override
	public void clearCach() {
		// NOOP: We have a DB no Memory cache
	}

	@Override
	public void addCookieLogin(User user, CookieLogin cookieLogin) {
		Session session = takeTransaction();
		cookieLogin.setUser(user);
		user.getLogins().add(cookieLogin);
		session.save(cookieLogin);
		session.getTransaction().commit();
	}

	@Override
	public boolean isUserNameFree(String userName) {
		Session session = takeTransaction();
		Query query = session.createQuery("from UserLogin where username = '" + userName + "'");
		UserLogin userLogin = (UserLogin)query.uniqueResult();
		session.close();
		return userLogin == null;
	}

	@Override
	public void activateUser(String activationCode) {
		Session session = takeTransaction();
		Query query = session.createQuery("from ActivationCode where code = '" + activationCode + "'");
		ActivationCode code = (ActivationCode)query.uniqueResult();
		if(code != null){
			User user = code.getUser();
			user.setState(UserState.active);
			session.save(user);
		} else{
			throw new UserDoesNotExistException();
		}
	}

}
