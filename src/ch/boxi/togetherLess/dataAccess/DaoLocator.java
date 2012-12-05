package ch.boxi.togetherLess.dataAccess;

import ch.boxi.togetherLess.dataAccess.jpaHelper.JpaTransactionControllerDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAO;
import ch.boxi.togetherLess.dataAccess.user.dao.UserDAOjpa;
import ch.boxi.togetherLess.dataAccess.weightMeasurement.dao.MeasurementDao;
import ch.boxi.togetherLess.dataAccess.weightMeasurement.dao.MeasurementDaoJpa;

public class DaoLocator {
	public static UserDAO getUserDAO(){
		//return new UserDAOinMemory();
		return new UserDAOjpa();
	}
	
	public static MeasurementDao getMeasurementDAO(){
		return new MeasurementDaoJpa();
	}
	
	public static JpaTransactionControllerDAO getTransactionControllerDAO(){
		return new JpaTransactionControllerDAO();
	}
}
