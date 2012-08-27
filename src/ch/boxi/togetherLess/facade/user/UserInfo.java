package ch.boxi.togetherLess.facade.user;

import javax.xml.bind.annotation.XmlRootElement;

import ch.boxi.togetherLess.dataAccess.user.dto.SimpleUserID;

@XmlRootElement
public class UserInfo {
	public SimpleUserID id;
	public String firstName;
	public String lastName;
	public String email;
	public int targetWeight;
	//public Date targetDate;

}
