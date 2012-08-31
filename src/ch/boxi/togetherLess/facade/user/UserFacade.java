package ch.boxi.togetherLess.facade.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ch.boxi.togetherLess.dataAccess.DaoLocator;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAOinMemory;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

@Path("/user")
public class UserFacade {
	@GET
	@Path("register")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserInfo register(
			@QueryParam("userName") 	String userName, 
			@QueryParam("password") 	String password, 
			@QueryParam("firstName") 	String firstName, 
			@QueryParam("lastName") 	String lastName, 
			@QueryParam("email") 		String email, 
			@QueryParam("targetWeight") int targetWeight, 
			@QueryParam("targetDate")	String targetDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(targetDate);
		UserDAO userDAO = DaoLocator.getUserDAO();
		User user = userDAO.register(userName, password, firstName, lastName, email, targetWeight, date);
		
		UserInfo info = new UserInfo();
		info.id = user.getId().toString();
		info.firstName = user.getFirstName();
		info.lastName = user.getLastName();
		info.email = user.getEmail();
		info.targetWeight = user.getTargetWeight();
		return info;
	}
	
	@GET
	@Path("login")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public SessionIDHolder login(
			@QueryParam("userName") 	String userName, 
			@QueryParam("password") 	String password) throws Exception{
		UserDAO userDAO = DaoLocator.getUserDAO();
		User user = userDAO.login(userName, password);
		if(user != null){
			UUID sessionID = UUID.randomUUID();
			CookieLogin cookieLogin = new CookieLogin(sessionID.toString(), user);
			userDAO.addCookieLogin(user, cookieLogin);
			return new SessionIDHolder(sessionID.toString());
		}
		return null;
	}
	
	@GET
	@Path("get")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserInfo getUser(@CookieParam("sessionID") String sessionID){
		UserDAO userDAO = DaoLocator.getUserDAO();
		User user = userDAO.login(sessionID);
		UserInfo info = new UserInfo();
		info.id = user.getId().toString();
		info.firstName = user.getFirstName();
		info.lastName = user.getLastName();
		info.email = user.getEmail();
		info.targetWeight = user.getTargetWeight();
		return info;
	}
}
