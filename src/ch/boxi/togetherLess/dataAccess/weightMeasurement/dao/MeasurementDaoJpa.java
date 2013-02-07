package ch.boxi.togetherLess.dataAccess.weightMeasurement.dao;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;

import ch.boxi.togetherLess.dataAccess.jpaHelper.AbstractHibernateDAO;
import ch.boxi.togetherLess.dataAccess.user.dto.User;
import ch.boxi.togetherLess.dataAccess.weightMeasurement.dto.Measurement;

public class MeasurementDaoJpa extends AbstractHibernateDAO implements MeasurementDao {

	@Override
	public Measurement createMeasurement(int grams, Date date, User user) {
		Session session = takeTransaction();
		Measurement m = new Measurement();
		m.setGrams(grams);
		m.setRecordingDate(date);
		m.setUser(user);
		session.save(m);
		session.getTransaction().commit();
		return m;
	}

	@Override
	public SortedSet<Measurement> readMeasurements(Date from, Date to, User dataFromUser) {
		SortedSet<Measurement> retMeasurements = new TreeSet<>();
		Session session = takeTransaction();
		Query query = session.createQuery("from Measurement as m where m.user.id = :userid and m.recordingDate >= :from and m.recordingDate <= :to");
		query.setInteger("userid", dataFromUser.getId());
		query.setTimestamp("from", from);
		query.setTimestamp("to", to);
		for(Object obj: query.list()){
			Measurement m = (Measurement) obj;
			retMeasurements.add(m);
		}
		session.close();
		return retMeasurements;
	}

	@Override
	public void deleteWeightLog(int id) {
		Session session = takeTransaction();  
		String hql = "delete from Measurement where id= :id"; 
		session.createQuery(hql).setInteger("id", id).executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public User getUserForWeightLog(int weightLogID) {
		Session session = takeTransaction();
		Query query = session.createQuery("select u from User u, Measurement m where u.id = m.user.id and m.id = :id");
		query.setInteger("id", weightLogID);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	@Override
	public void updateWeightLog(int id, int grams, Date recordingDate){
		Session session = takeTransaction();
		Query query = session.createQuery("update Measurement set grams = :grams, recordingDate = :recordingDate where id = :id");
		query.setInteger("grams", grams);
		query.setDate("recordingDate", recordingDate);
		query.setInteger("id", id);
		query.executeUpdate();
		session.getTransaction().commit();
	}

}
