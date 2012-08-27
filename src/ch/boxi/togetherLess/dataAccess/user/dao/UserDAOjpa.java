package ch.boxi.togetherLess.dataAccess.user.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		Query query = session.createQuery("from UserLogin where username = " + userName);
		List list = query.list();
		List<UserLogin> userLogins = new ArrayList<>(list.size());
		for(Object o: list){
			UserLogin login = (UserLogin) o;
			userLogins.add(login);
		}
		return userLogins.iterator().next().getUser();
	}

	@Override
	public User login(String sessionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCach() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCookieLogin(User user, CookieLogin cookieLogin) {
		// TODO Auto-generated method stub

	}

}
