package ch.boxi.togetherLess.facade.user;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterInfo {
	public boolean registrationOK = false;
	public List<String> errors = new ArrayList<>();
	public Integer userID = null;
}
