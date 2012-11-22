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

}
