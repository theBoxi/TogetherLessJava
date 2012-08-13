package ch.boxi.togetherLess.backend;

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
 
        context.addServlet(new ServletHolder(new TestServlet()),"/*");
        context.addServlet(new ServletHolder(new TestServlet("Buongiorno Mondo")),"/it/*");
        context.addServlet(new ServletHolder(new TestServlet("Bonjour le Monde")),"/fr/*");
        
        ServletHolder servletHolder = new ServletHolder(new ServletContainer());
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "ch.boxi.togetherLess.backend");
        
        //Add logging for request response!
        servletHolder.setInitParameter("com.sun.jersey.spi.container.ContainerRequestFilters", "com.sun.jersey.api.container.filter.LoggingFilter");
        servletHolder.setInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters", "com.sun.jersey.api.container.filter.LoggingFilter");
       
		context.addServlet(servletHolder, "/rest/*");
 
        server.start();
        server.join();
	}
}
