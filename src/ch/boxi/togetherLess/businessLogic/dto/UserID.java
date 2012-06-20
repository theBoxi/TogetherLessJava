package ch.boxi.togetherLess.businessLogic.dto;

import ch.boxi.javaUtil.id.decorator.validation.ValidationException;

public class UserID extends TGLBaseID {
	private static final long serialVersionUID = 8001645872661558082L;
	
	public static final String PREFIX = "UID";
	
	protected UserID(long dbRepresentiv){
		super(PREFIX, dbRepresentiv);
	}
	
	public static UserID createFromDbRepresentiv(long dbRepresentiv, boolean mustBeValid) throws ValidationException{
		UserID id = new UserID(dbRepresentiv);
		if(mustBeValid && !id.isValid()){
			throw new ValidationException("id " + id + " is not valid!");
		}
		return id;
	}
	
	public static UserID createFromDbRepresentiv(long dbRepresentiv){
		return createFromDbRepresentiv(dbRepresentiv, true);
	}
	
	public static UserID createFromNewValueWithoutCheckdigit(long value){
		return new UserID(TGLBaseID.algorythm.AddCheckDigit(value));
	}
	
}
