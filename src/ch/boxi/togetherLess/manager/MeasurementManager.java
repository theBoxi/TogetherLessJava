package ch.boxi.togetherLess.manager;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import ch.boxi.togetherLess.dataAccess.DaoLocator;
import ch.boxi.togetherLess.dataAccess.user.dto.Login;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.weightMeasurement.dao.MeasurementDao;
import ch.boxi.togetherLess.dataAccess.weightMeasurement.dto.Measurement;
import ch.boxi.togetherLess.exception.LanguageDependentText;
import ch.boxi.togetherLess.exception.TogetherLessException;

public class MeasurementManager {

	private static final Logger logger = Logger.getLogger(MeasurementManager.class);
	
	
	public Measurement logWeight(int grams, Date recordingDate, User user){
		Measurement measurement = DaoLocator.getMeasurementDAO().createMeasurement(grams, recordingDate, user);
		//user.getMeasurements().add(measurement);
		return measurement;
	}
	
	
	public Set<Measurement> loadMeasurements(Login login, User forUser, Date from, Date to){
//		User requestingUser = new UserManager().loadUser(login);
//		if(!requestingUser.equals(forUser)){
//			logger.fatal("forbidden access to measurementData from account: " + requestingUser + " tryed to load data from account: " + forUser);
//			throw new TogetherLessException("9999", 
//					new LanguageDependentText()
//			.add("de", "Zugriff auf Daten von User: " + forUser + " verweigert!")
//			.add("en", "Access to data from user " + forUser + " denied!"));
//		}
		SortedSet<Measurement> measurements = DaoLocator.getMeasurementDAO().readMeasurements(from, to, forUser);
		return measurements;
	}
	
	public void deleteWeightLog(Login login, int id){
		 MeasurementDao dao = DaoLocator.getMeasurementDAO();
		 User user = dao.getUserForWeightLog(id);
		 if(user.getId().equals(login.getUser().getId())){
			 dao.deleteWeightLog(id);
		 } else{
			 throw new NotAuthorizedException();
		 }
	}
	
	public void updateWeightLog(Login login, int id, int grams, Date recordingDate){
		MeasurementDao dao = DaoLocator.getMeasurementDAO();
		 User user = dao.getUserForWeightLog(id);
		 if(user.getId().equals(login.getUser().getId())){
			 dao.updateWeightLog(id, grams, recordingDate);
		 } else{
			 throw new NotAuthorizedException();
		 }
	}
}
