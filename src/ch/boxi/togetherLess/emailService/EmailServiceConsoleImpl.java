package ch.boxi.togetherLess.emailService;

import java.util.Properties;

import org.apache.log4j.Logger;

public class EmailServiceConsoleImpl {
	Logger logger = Logger.getLogger(EmailServiceConsoleImpl.class);
	
	public void sendMail(String to, Emailtemplate template, Properties values){
		logger.info("mail (onlyConsole): to: " + to + " template: " + template.getName() + " properties: " + propertiesToString(values));
	}
	
	private String propertiesToString(Properties properties){
		return properties.toString();
	}
}
