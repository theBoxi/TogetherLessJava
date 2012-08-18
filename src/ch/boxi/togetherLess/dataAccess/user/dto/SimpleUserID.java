package ch.boxi.togetherLess.dataAccess.user.dto;

import javax.xml.bind.annotation.XmlValue;

public class SimpleUserID implements Comparable<SimpleUserID> {
	@XmlValue
	private Long id;
	
	private SimpleUserID(){
		super();
	}
	
	public SimpleUserID(long id){
		this();
		this.id = id;
	}
	
	public SimpleUserID(String id){
		this(extractLongFromPrefixString(id));
	}
	
	private static long extractLongFromPrefixString(String id){
		String idLonly = id.substring(id.indexOf(":")+1);
		long longID = Long.parseLong(idLonly);
		return longID;
	}
	
	@Override
	public String toString(){
		return "UID:" + id.toString();
	}
	
	public long getDBID(){
		return id;
	}

	@Override
	public int compareTo(SimpleUserID o) {
		return id.compareTo(o.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleUserID other = (SimpleUserID) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
