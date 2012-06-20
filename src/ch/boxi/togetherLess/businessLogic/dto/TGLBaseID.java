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

public abstract class TGLBaseID implements ID{
	private static final long serialVersionUID = 4772807214227419835L;
	
	protected static final String ID_FORMAT = "{prefix|-}0##.###.###-##";
	protected static final CheckDigitAlgorythm algorythm = new Mod9710Algorythm();
	
	private IDBaseDecorator innerID = null;
	
	public TGLBaseID(){
		super();
	}
	
	protected TGLBaseID(String prefix, long dbRepresentiv){
		this();
		IDFormat Idformat = new SimpleIDFormat(ID_FORMAT);
		innerID = new FormatterDecorator(
					new CheckDigitDecorator(
						new PrefixDecorator(
							new SimpleLongID(dbRepresentiv), prefix)
						, algorythm)
					, Idformat);
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
