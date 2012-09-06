package ch.boxi.togetherLess.dataAccess.user.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="activationCode")
public class ActivationCode {
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="userid")
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column private Date validUntil;
	@Column private String code;
	
	public ActivationCode(){
		super();
	}

	public ActivationCode(User user, Date validUntil, String code) {
		this();
		this.user = user;
		this.validUntil = validUntil;
		this.code = code;
	}
	
	public ActivationCode(User user, int expireInDays){
		this();
		this.user = user;
		this.validUntil = getDateTodayPlus(expireInDays);
		this.code = UUID.randomUUID().toString();
	}
	
	private Date getDateTodayPlus(int days){
		int millisecoundPerDay = 24*60*60*1000;
		return new Date(System.currentTimeMillis() + millisecoundPerDay * days);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
