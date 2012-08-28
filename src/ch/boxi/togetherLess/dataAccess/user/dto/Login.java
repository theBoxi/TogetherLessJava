package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Login implements Comparable<Login>{

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public Login() {
		super();
	}
	
	public Login(User user){
		this();
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public abstract LoginType getLoginType();
	
	@Override
	public int compareTo(Login other){
		if(getLoginType() != other.getLoginType()){
			return getLoginType().compareTo(other.getLoginType());
		}
		return 0;
	}

}