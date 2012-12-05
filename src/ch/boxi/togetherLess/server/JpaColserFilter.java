package ch.boxi.togetherLess.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ch.boxi.togetherLess.dataAccess.DaoLocator;

public class JpaColserFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(req, resp);
		DaoLocator.getTransactionControllerDAO().closeTransaction();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
