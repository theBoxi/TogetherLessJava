package ch.boxi.togetherLess.facade.user;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RegisterInfo {
	public boolean registrationOK = false;
	public Map<String, Boolean> errors = new HashMap<>();
	public Integer userID = null;
	
	public boolean hasErrors(){
		for(Boolean error: errors.values()){
			if(!error){
				return true;
			}
		}
		return false;
	}
}
