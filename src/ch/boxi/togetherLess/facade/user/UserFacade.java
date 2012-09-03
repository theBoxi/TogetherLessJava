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

import org.apache.commons.lang3.StringUtils;

import ch.boxi.togetherLess.dataAccess.DaoLocator;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

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
			@QueryParam("email") 		String email, 
			@QueryParam("targetWeight") int targetWeight, 
			@QueryParam("targetDate")	String targetDate) throws Exception{
		UserDAO userDAO = DaoLocator.getUserDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		RegisterInfo info = new RegisterInfo();
		Date date = null;
		if( !password.equals(password2) || StringUtils.isEmpty(password)){
			info.errors.add("password");
			info.errors.add("password2");
		}
		if( !userDAO.isUserNameFree(userName)){
			info.errors.add("userName");
		}
		if(StringUtils.isEmpty(firstName)){
			info.errors.add("firstName");
		}
		if(StringUtils.isEmpty(lastName)){
			info.errors.add("lastName");
		}
		if(StringUtils.isEmpty(email)){
			info.errors.add("email");
		}
		if(targetWeight == 0){
			info.errors.add("targetWeight");
		}
		if(StringUtils.isEmpty(targetDate)){
			info.errors.add("targetDate");
		} else{
			try{
				date = sdf.parse(targetDate);
			}catch(ParseException e){
				info.errors.add("targetDate");
			}
		}
		
		if(info.errors.isEmpty()){
			User user = userDAO.register(userName, password, firstName, lastName, email, targetWeight, date);
			info.userID = user.getId();
			info.registrationOK = true;
		}
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
