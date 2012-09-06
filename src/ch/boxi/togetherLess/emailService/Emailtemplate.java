package ch.boxi.togetherLess.emailService;

public class Emailtemplate {
	private String fileName;
	private String name;
	
	public static Emailtemplate Register = new Emailtemplate("Register", "");
			
	public Emailtemplate(String name, String fileName){
		this.fileName = fileName;
		this.name = name;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String getName(){
		return name;
	}
}
