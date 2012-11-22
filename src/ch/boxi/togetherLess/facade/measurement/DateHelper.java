package ch.boxi.togetherLess.facade.measurement;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

public class DateHelper {
	public static Interval getActualMonth(){
		DateMidnight firstInMonth = new LocalDate().withDayOfMonth(1).toDateMidnight();
		DateMidnight firstNextMonth = new LocalDate().plusMonths(1).withDayOfMonth(1).toDateMidnight();
		return new Interval(firstInMonth, firstNextMonth);
	}
}
