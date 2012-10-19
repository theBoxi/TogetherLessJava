package ch.boxi.togetherLess.facade.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserInfo {
	public String id;
	public String firstName;
	public String lastName;
	public String email;
}
