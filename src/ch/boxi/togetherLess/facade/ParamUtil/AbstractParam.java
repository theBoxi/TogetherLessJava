package ch.boxi.togetherLess.facade.ParamUtil;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

public abstract class AbstractParam<V> {
	  private final V value;
	  private final String originalParam;
	  
	  public AbstractParam(String param) throws WebApplicationException {
	    this.originalParam = param;
	    try {
	      this.value = parse(param);
	    } catch (Throwable e) {
	      throw new WebApplicationException(onError(param, e));
	    }
	  }
	  
	  public V getValue() {
	    return value;
	  }
	  
	  public String getOriginalParam() {
	    return originalParam;
	  }
	  
	  @Override
	  public String toString() {
	    return value.toString();
	  }
	  
	  protected abstract V parse(String param) throws Throwable;
	  
	  protected Response onError(String param, Throwable e) {
	    return Response
	        .status(Status.BAD_REQUEST)
	        .entity(getErrorMessage(param, e))
	        .build();
	  }
	  
	  protected String getErrorMessage(String param, Throwable e) {
	    return "Invalid parameter: " + param + " (" + e.getMessage() + ")";
	  }
}
