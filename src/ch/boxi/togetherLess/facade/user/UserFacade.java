package ch.boxi.togetherLess.facade.user;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import ch.boxi.togetherLess.dataAccess.DaoLocator;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.manager.UserManager;

@Path("/user")
public class UserFacade {
	@GET
	@Path("register")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public RegisterInfo register(
			@QueryParam("userName") 	String userName, 
			@QueryParam("password") 	String password,
			@QueryParam("password2") 	String password2,
			@QueryParam("firstName") 	String firstName, 
			@QueryParam("lastName") 	String lastName, 
			@QueryParam("email") 		String email) throws Exception{
		
		RegisterInfo info = isValidUserParams(userName, password, password2, firstName, lastName, email);
		
		if(!info.hasErrors()){
			UserManager manager = new UserManager();
			User user = manager.register(userName, password, password2, firstName, lastName, email);
			info.userID = user.getId();
			info.registrationOK = true;
		}
		return info;
	}
	
	private RegisterInfo isValidUserParams(
			String userName, 
			String password,
			String password2,
			String firstName, 
			String lastName, 
			String email){
		UserDAO userDAO = DaoLocator.getUserDAO();
		RegisterInfo info = new RegisterInfo();
		if(StringUtils.isEmpty(password)){
			info.errors.put("password", false);
		} else{ info.errors.put("password", true);}
		if( !password.equals(password2) || StringUtils.isEmpty(password)){
			info.errors.put("password2", false);
		} else{ info.errors.put("password2", true);}
		if( StringUtils.isEmpty(userName) || !userDAO.isUserNameFree(userName)){
			info.errors.put("userName", false);
		} else{ info.errors.put("userName", true);}
		if(StringUtils.isEmpty(firstName)){
			info.errors.put("firstName", false);
		} else{ info.errors.put("firstName", true);}
		if(StringUtils.isEmpty(lastName)){
			info.errors.put("lastName", false);
		} else{ info.errors.put("lastName", true);}
		if(StringUtils.isEmpty(email)){
			info.errors.put("email", false);
		} else{ info.errors.put("email", true);}
		return info;
	}
	
	@GET
	@Path("login")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public SessionIDHolder login(
			@QueryParam("userName") 	String userName, 
			@QueryParam("password") 	String password) throws Exception{
		UserManager manager = new UserManager();
		CookieLogin cookieLogin = manager.login(userName, password);
		return new SessionIDHolder(cookieLogin.getSessionID());
	}
	
	@GET
	@Path("get")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserInfo getUser(@CookieParam("sessionID") String sessionID){
		UserManager manager = new UserManager();
		User user = manager.loadUser(new CookieLogin(sessionID, null));
		UserInfo info = new UserInfo();
		info.id = user.getId().toString();
		info.firstName = user.getFirstName();
		info.lastName = user.getLastName();
		info.email = user.getEmail();
		return info;
	}
	
	@GET
	@Path("activate")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void activateUser(@QueryParam("activationCode") String activationCode){
		UserManager manager = new UserManager();
		manager.activateUser(activationCode);
	}
}
