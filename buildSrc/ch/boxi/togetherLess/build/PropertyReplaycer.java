package ch.boxi.togetherLess.build;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyReplaycer {
	private static Pattern propertyPattern = Pattern.compile("(.*)<tgl:property\\s+name=\"([0-9a-zA-Z\\./-_]+)\"\\s*/>(.*)");
	private Properties properties;
	private boolean verbose;
	
	public PropertyReplaycer(Properties properties, boolean verbose) {
		super();
		this.properties = properties;
		this.verbose = verbose;
	}
	
	public String replayceAllProperties(String line){
		Matcher propertyMatcher = propertyPattern.matcher(line);
		if(propertyMatcher.find()){
			String propertyName = propertyMatcher.group(2);
			String propertyValue = properties.getProperty(propertyName);
			log("found property to set <" + propertyName + "> set value \"" + propertyValue + "\"");
			line = propertyMatcher.group(1) + propertyValue + propertyMatcher.group(3);
			line = replayceAllProperties(line);
		}
		return line;
	}
	
	private void log(String msg){
		if(verbose){
			System.out.println(msg);
		}
	}
}
