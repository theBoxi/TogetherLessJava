package ch.boxi.togetherLess.dataAccess.user.dto;

public abstract class ID{
	private long id;
	
	private ID(){
		super();
	}
	
	public ID(long id){
		this();
		this.id = id;
	}
	
	public ID(String id){
		this(extractLongFromPrefixString(id));
	}
	
	private static long extractLongFromPrefixString(String id){
		String idLonly = id.substring(id.indexOf(":"));
		long longID = Long.parseLong(idLonly);
		return longID;
	}
	
	@Override
	public String toString(){
		return getPrefix() + ":" + Long.toString(id);
	}
	
	public long getDBID(){
		return id;
	}
	
	public abstract String getPrefix();
}
