package ch.boxi.togetherLess.businessLogic.dto;

import java.util.Date;

public class User{
	private UserID id;
	private String firstName;
	private String lastName;
	private String email;
	private int targetWeight;
	private Date targetDate;
	
	public User(){
		super();
	}

	public User(Long id, String firstName, String lastName, String email,
			int targetWeight, Date targetDate) {
		this();
		this.id = new UserID(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.targetWeight = targetWeight;
		this.targetDate = targetDate;
	}
	
}
