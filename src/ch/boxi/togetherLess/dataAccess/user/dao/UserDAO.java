package ch.boxi.togetherLess.dataAccess.user.dao;

import java.util.Date;

import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

public interface UserDAO {

	public abstract User getUser(Integer id);

	public abstract User register(String userName, String password,
			String firstName, String lastName, String email, int targetWeight,
			Date targetDate);

	public abstract User login(String userName, String password);

	public abstract User login(String sessionID);

	public abstract void clearCach();

	public abstract void addCookieLogin(User user, CookieLogin cookieLogin);

}