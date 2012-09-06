package ch.boxi.togetherLess.facade.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
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
import ch.boxi.togetherLess.emailService.EmailServiceConsoleImpl;
import ch.boxi.togetherLess.emailService.Emailtemplate;

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
		RegisterInfo info = isValidUserParams(userName, password, password2, firstName, lastName, email, targetWeight, targetDate);
		
		if(!info.hasErrors()){
			Date date = sdf.parse(targetDate);
			User user = userDAO.register(userName, password, firstName, lastName, email, targetWeight, date);
			info.userID = user.getId();
			info.registrationOK = true;
			EmailServiceConsoleImpl emailService = new EmailServiceConsoleImpl();
			emailService.sendMail(email, Emailtemplate.Register, getRegistrationMailProperties(user));
		}
		return info;
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
	
	private RegisterInfo isValidUserParams(
			String userName, 
			String password,
			String password2,
			String firstName, 
			String lastName, 
			String email, 
			int targetWeight, 
			String targetDate){
		UserDAO userDAO = DaoLocator.getUserDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		if(targetWeight == 0){
			info.errors.put("targetWeight", false);
		} else{ info.errors.put("targetWeight", true);}
		if(StringUtils.isEmpty(targetDate)){
			info.errors.put("targetDate", false);
		} else{
			try{
				sdf.parse(targetDate);
				info.errors.put("targetDate", true);
			}catch(ParseException e){
				info.errors.put("targetDate", false);
			}
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
