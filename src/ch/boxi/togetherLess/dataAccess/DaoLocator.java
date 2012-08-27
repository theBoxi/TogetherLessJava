package ch.boxi.togetherLess.dataAccess;

import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAOinMemory;

public class DaoLocator {
	public static UserDAO getUserDAO(){
		return new UserDAOinMemory();
	}
}
