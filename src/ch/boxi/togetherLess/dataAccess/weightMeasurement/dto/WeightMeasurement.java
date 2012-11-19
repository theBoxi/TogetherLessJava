package ch.boxi.togetherLess.dataAccess.weightMeasurement.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ch.boxi.togetherLess.dataAccess.user.dto.User;

@Entity
@Table(name="weightMeasurement")
public class WeightMeasurement {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column private Date measurementDate;
	
	@Column private int grams;
	
	public WeightMeasurement(){
		super();
	}

	public WeightMeasurement(Integer id, Date measurementDate, int grams, User user) {
		this();
		this.id = id;
		this.measurementDate = measurementDate;
		this.grams = grams;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public Date getMeasurementDate() {
		return measurementDate;
	}

	public void setMeasurementDate(Date measurementDate) {
		this.measurementDate = measurementDate;
	}

	public int getGrams() {
		return grams;
	}

	public void setGrams(int grams) {
		this.grams = grams;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}
