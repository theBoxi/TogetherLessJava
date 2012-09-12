package ch.boxi.togetherLess.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class TogetherLessServer {
	public static void main(String[] args) throws Exception{
		Server server = new Server(8080);
		 
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        ServletHolder fileServletHolder = new ServletHolder();
        fileServletHolder.setInitParameter("basePath", "/apps/WEB");
        fileServletHolder.setServlet(new FileServlet());
        context.addServlet(fileServletHolder, "/resource/*");
        
        ServletHolder servletHolder = new ServletHolder(new ServletContainer());
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "ch.boxi.togetherLess.facade");
        
        //Add logging for request response!
        servletHolder.setInitParameter("com.sun.jersey.spi.container.ContainerRequestFilters", "com.sun.jersey.api.container.filter.LoggingFilter");
        servletHolder.setInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters", "com.sun.jersey.api.container.filter.LoggingFilter");
       
		context.addServlet(servletHolder, "/rest/*");
 
        server.start();
        server.join();
	}
}
