package ch.boxi.togetherLess.businessLogic.dto;

public abstract class Login implements Comparable<Login>{

	private User user;

	public Login() {
		super();
	}
	
	public Login(User user){
		this();
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public abstract LoginType getLoginType();
	
	@Override
	public int compareTo(Login other){
		if(getLoginType() != other.getLoginType()){
			return getLoginType().compareTo(other.getLoginType());
		}
		return 0;
	}

}