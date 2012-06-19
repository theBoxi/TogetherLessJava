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

public class UserID implements ID{
	private static final long serialVersionUID = 4772807214227419835L;
	private static final String ID_FORMAT = "{prefix|-}0##.###.###-##";
	private static final CheckDigitAlgorythm algorythm = new Mod9710Algorythm();
	private ID innerID = null;
	
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
	
	public static UserID createFromDbRepresentiv(long dbRepresentiv){
		return new UserID(dbRepresentiv);
	}
	
	public static UserID createFromNewValueWithoutCheckdigit(long value){
		return new UserID(algorythm.AddCheckDigit(value));
	}
	
	@Override
	public int compareTo(ID o) {
		return ((Long)innerID.getLongValue()).compareTo(o.getLongValue());
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
		return isValid(innerID);
	}
	
	private boolean isValid(ID id){
		if(id instanceof IDBaseDecorator){
			IDBaseDecorator decorator = (IDBaseDecorator) id;
			if(DecoratorType.CeckDigit == decorator.getDecoratorType()){
				CheckDigitDecorator checkDigitDecorator = (CheckDigitDecorator) decorator;
				return checkDigitDecorator.getCheckDigitAlgorythm().isValidID(getLongValue());
			} else{
				return isValid(decorator.getBase());
			}
		}
		return false;
	}

}
