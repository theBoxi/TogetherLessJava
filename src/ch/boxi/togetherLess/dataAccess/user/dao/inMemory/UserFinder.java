package ch.boxi.togetherLess.dataAccess.user.dao.inMemory;

import ch.boxi.togetherLess.dataAccess.user.dto.User;

public interface UserFinder {
	public boolean isThisUser(User user);
}
