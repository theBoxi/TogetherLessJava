package ch.boxi.togetherLess.facade.measurement;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Interval;

import ch.boxi.togetherLess.dataAccess.user.dto.CookieLogin;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.weightMeasurement.dto.Measurement;
import ch.boxi.togetherLess.manager.MeasurementManager;
import ch.boxi.togetherLess.manager.UserManager;

@Path("/measurement")
public class MeasurementFacade {

	@GET
	@Path("logWeight")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void logWeight(@CookieParam("sessionID") String sessionID, @QueryParam("grams")Integer grams, @QueryParam("date") String date){
		Date recordingDate = new Date();
		if(StringUtils.isNotEmpty(date)){
			//noop until Test finished
		}
		UserManager userManager = new UserManager();
		CookieLogin cookieLogin = new CookieLogin(sessionID, null);
		User user = userManager.loadUser(cookieLogin);
		new MeasurementManager().logWeight(grams, recordingDate, user);
	}
	
	
	@GET
	@Path("actualMonth")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Measurement[] loadMeasurementsOfActualMonth(@CookieParam("sessionID") String sessionID){
		ArrayList<Measurement> measurements = new ArrayList<>();
		UserManager userManager = new UserManager();
		CookieLogin cookieLogin = new CookieLogin(sessionID, null);
		User user = userManager.loadUser(cookieLogin);
		Interval interval = DateHelper.getActualMonth();
		new MeasurementManager().loadMeasurements(cookieLogin, user, interval.getStart().toDate(), interval.getEnd().toDate());
		measurements.addAll(user.getMeasurements());
		return measurements.toArray(new Measurement[measurements.size()]);
	}
}
