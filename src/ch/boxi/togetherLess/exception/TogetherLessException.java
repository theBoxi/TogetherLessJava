package ch.boxi.togetherLess.exception;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TogetherLessException extends WebApplicationException {
	private static final long serialVersionUID = 1L;
	
	public TogetherLessException(String tglErrorCode, LanguageDependentText msg){
		this(500, tglErrorCode, msg, null);
	}
	
	public TogetherLessException(String tglErrorCode, LanguageDependentText msg,
			Exception e){
		this(500, tglErrorCode, msg, e);
	}
	
	public TogetherLessException(int httpErrorCode, String tglErrorCode, LanguageDependentText msg,
			Exception e) {
		this(httpErrorCode, tglErrorCode, msg, new TreeMap<String, String>(), e);
	}
	
	public TogetherLessException(int httpErrorCode, String tglErrorCode, LanguageDependentText msg,
			Map<String, String> additionalFields, Exception e) {
		super(	e, 
				Response.status(httpErrorCode)
				.type(MediaType.APPLICATION_JSON)
				.entity("{\"tglErrorCode\":\""+tglErrorCode+"\", \"msg\":"+msg.toJson()+additionalFieldsToJson(additionalFields)+"}")
				.build()
		);
	}
	
	private static String additionalFieldsToJson(Map<String, String> additionalFields){
		if(additionalFields != null && !additionalFields.isEmpty()){
			StringBuffer sb = new StringBuffer();
			sb.append(",");
			for(Entry<String, String> entry: additionalFields.entrySet()){
				sb.append("\"")
				.append(entry.getKey())
				.append("\":\"")
				.append(entry.getValue())
				.append("\",");
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		} else{
			return "";
		}
	}
	
	
}
