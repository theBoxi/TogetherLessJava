package ch.boxi.togetherLess.facade.ParamUtil;

import javax.ws.rs.WebApplicationException;

public class IntegerParam extends AbstractParam<Integer>{

	public IntegerParam(String param) throws WebApplicationException {
		super(param);
	}

	@Override
	protected Integer parse(String param) throws Throwable {
		return Integer.parseInt(param);
	}

}
