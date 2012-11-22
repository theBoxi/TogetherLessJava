package ch.boxi.togetherLess.exception;

import java.util.TreeMap;
import java.util.Map.Entry;

public class LanguageDependentText extends TreeMap<String, String>{
	private static final long serialVersionUID = 1L;

	public String toJson(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Entry<String, String> entry: entrySet()){
			sb.append("{\"lang\":\"")
			.append(entry.getKey())
			.append("\",\"msg\":\"")
			.append(entry.getValue())
			.append("\"},");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	public LanguageDependentText add(String lang, String msg){
		put(lang, msg);
		return this;
	}
}
