package ch.boxi.togetherLess.businessLogic.dto.XmlAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date>{

	public static final String DATE_TIME_FORMAT = "yyyy.MM.dd mm:ss";
	public static final String DATE_FORMAT = "yyyy.MM.dd";
	
	@Override
	public Date unmarshal(String v) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		try{
			return dateFormat.parse(v); 
		} catch(ParseException e){
			//noop, because we have to chack dateTimeFormat also
		}
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dateTimeFormat.parse(v); 
	}

	@Override
	public String marshal(Date v) throws Exception {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dateTimeFormat.format(v);
	}

}
