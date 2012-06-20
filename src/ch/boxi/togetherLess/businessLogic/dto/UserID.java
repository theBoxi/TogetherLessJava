package ch.boxi.togetherLess.businessLogic.dto;

import ch.boxi.javaUtil.id.ID;
import ch.boxi.javaUtil.id.SimpleLongID;
import ch.boxi.javaUtil.id.decorator.DecoratorType;
import ch.boxi.javaUtil.id.decorator.IDBaseDecorator;
import ch.boxi.javaUtil.id.decorator.checkdigit.CheckDigitDecorator;
import ch.boxi.javaUtil.id.decorator.checkdigit.algorythms.CheckDigitAlgorythm;
import ch.boxi.javaUtil.id.decorator.checkdigit.algorythms.Mod9710Algorythm;
import ch.boxi.javaUtil.id.decorator.format.FormatterDecorator;
import ch.boxi.javaUtil.id.decorator.format.IDFormat;
import ch.boxi.javaUtil.id.decorator.format.SimpleIDFormat;
import ch.boxi.javaUtil.id.decorator.prefix.PrefixDecorator;
import ch.boxi.javaUtil.id.decorator.validation.ValidationException;

public class UserID implements ID{
	private static final long serialVersionUID = 4772807214227419835L;
	private static final String ID_FORMAT = "{prefix|-}0##.###.###-##";
	private static final CheckDigitAlgorythm algorythm = new Mod9710Algorythm();
	private IDBaseDecorator innerID = null;
	
	public UserID(){
		super();
	}
	
	private UserID(long dbRepresentiv){
		this();
		IDFormat Idformat = new SimpleIDFormat(ID_FORMAT);
		innerID = new FormatterDecorator(
					new CheckDigitDecorator(
						new PrefixDecorator(
							new SimpleLongID(dbRepresentiv), "UID")
						, algorythm)
					, Idformat);
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
		return new UserID(algorythm.AddCheckDigit(value));
	}
	
	@Override
	public int compareTo(ID o) {
		return toString().compareTo(o.toString());
	}

	@Override
	public long getLongValue() {
		return innerID.getLongValue();
	}
	
	@Override
	public String toString(){
		return innerID.toString();
	}
	
	public boolean isValid(){
		CheckDigitDecorator decorator = innerID.getIDBaseDecorator(innerID, DecoratorType.CeckDigit);
		if(decorator != null){
			return decorator.getCheckDigitAlgorythm().isValidID(getLongValue());
		}
		return false;
	}
	
	public String getPrefix(){
		PrefixDecorator decorator = innerID.getIDBaseDecorator(innerID, DecoratorType.Prefix);
		if(decorator != null){
			return decorator.getPrefix();
		}
		return null;
	}

}
