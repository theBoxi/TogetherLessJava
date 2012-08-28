package ch.boxi.togetherLess.dataAccess.user.dao;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import ch.boxi.togetherLess.dataAccess.jpaHelper.AbstractHibernateDAO;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.user.dto.UserLogin;

public class UserDAOjpa extends AbstractHibernateDAO implements UserDAO {

	@Override
	public User getUser(Integer id) {
		return null;
	}

	@Override
	public User register(String userName, String password, String firstName,
			String lastName, String email, int targetWeight, Date targetDate) {
		Session session = takeTransaction();
		
		UserLogin login = new UserLogin(userName, password, null);
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setTargetDate(targetDate);
		user.setTargetWeight(targetWeight);
		user.getLogins().add(login);
		
		login.setUser(user);
		
		session.save(user);
		session.save(login);
		session.getTransaction().commit();
		return user;
	}

	@Override
	public User login(String userName, String password) {
		Session session = takeTransaction();
		Query query = session.createQuery("from UserLogin where username = '" + userName + "'");
		UserLogin userLogin = (UserLogin)query.uniqueResult();
		session.close();
		return userLogin.getUser();
	}

	@Override
	public User login(String sessionID) {
		Session session = takeTransaction();
		Query query = session.createQuery("from CookieLogin where sessionID = '" + sessionID + "'");
		CookieLogin cookieLogin = (CookieLogin) query.uniqueResult();
		session.close();
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

}