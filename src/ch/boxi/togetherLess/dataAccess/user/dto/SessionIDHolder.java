package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SessionIDHolder {
	public String sessionID;
	
	public SessionIDHolder(){
		super();
	}
	
	public SessionIDHolder(String sessionID){
		this();
		this.sessionID = sessionID;
	}
}
