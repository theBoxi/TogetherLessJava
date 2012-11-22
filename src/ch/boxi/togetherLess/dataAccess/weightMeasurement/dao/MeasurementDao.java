package ch.boxi.togetherLess.dataAccess.weightMeasurement.dao;

import java.util.Date;
import java.util.SortedSet;

import ch.boxi.togetherLess.dataAccess.weightMeasurement.dto.Measurement;
import ch.boxi.togetherLess.dataAccess.user.dto.User;

public interface MeasurementDao {
	public Measurement createMeasurement(int grams, Date date, User user);
	public SortedSet<Measurement> readMeasurements(Date from, Date to, User dataFromUser);
}
