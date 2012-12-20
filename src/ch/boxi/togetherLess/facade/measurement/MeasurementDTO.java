package ch.boxi.togetherLess.facade.measurement;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.boxi.togetherLess.dataAccess.weightMeasurement.dto.Measurement;

public class MeasurementDTO implements Comparable<MeasurementDTO> {
	private int ID;
	private String recordingDate;
	private int grams;
	
	public MeasurementDTO(){
		super();
	}
	
	public MeasurementDTO(int iD, String recordingDate,
			int grams) {
		this();
		ID = iD;
		this.recordingDate = recordingDate;
		this.grams = grams;
	}
	
	public MeasurementDTO(Measurement m){
		this(m.getId(), formatDate(m.getRecordingDate()), m.getGrams());
	}
	
	private static String formatDate(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
		return sdf.format(d);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(String recordingDate) {
		this.recordingDate = recordingDate;
	}

	public int getGrams() {
		return grams;
	}

	public void setGrams(int grams) {
		this.grams = grams;
	}

	@Override
	public int compareTo(MeasurementDTO o) {
		return Integer.compare(o.getID(), ID);
	} 
}
