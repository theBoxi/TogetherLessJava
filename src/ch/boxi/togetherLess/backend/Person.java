package ch.boxi.togetherLess.backend;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	private String firstName;
	private String secoundName;
	
	public Person(){
		super();
	}
	
	public Person(String firstName, String secoundName) {
		this();
		this.firstName = firstName;
		this.secoundName = secoundName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecoundName() {
		return secoundName;
	}

	public void setSecoundName(String secoundName) {
		this.secoundName = secoundName;
	}
}
