package ch.boxi.togetherLess.dataAccess.user.dto;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="appuser")
public class User{
	@Id
	@GeneratedValue
	private Integer id;
	@Column private String firstName;
	@Column private String lastName;
	@Column private String email;
	@Column private int targetWeight;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column private Date targetDate;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<Login> logins = new TreeSet<>();
	
	public User(){
		super();
	}

	public User(Integer id, String firstName, String lastName, String email,
			int targetWeight, Date targetDate, Login login) {
		this();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.targetWeight = targetWeight;
		this.targetDate = targetDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(int targetWeight) {
		this.targetWeight = targetWeight;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Integer getId() {
		return id;
	}

	public Set<Login> getLogins() {
		return logins;
	}

	public void setLogins(Set<Login> logins) {
		this.logins = logins;
		if(logins == null){
			logins = new TreeSet<>();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((logins == null) ? 0 : logins.hashCode());
		result = prime * result
				+ ((targetDate == null) ? 0 : targetDate.hashCode());
		result = prime * result + targetWeight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (logins == null) {
			if (other.logins != null)
				return false;
		} else if (!logins.equals(other.logins))
			return false;
		if (targetDate == null) {
			if (other.targetDate != null)
				return false;
		} else if (!targetDate.equals(other.targetDate))
			return false;
		if (targetWeight != other.targetWeight)
			return false;
		return true;
	}
}