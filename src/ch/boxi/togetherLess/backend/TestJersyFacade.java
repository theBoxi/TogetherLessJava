package ch.boxi.togetherLess.backend;

import javax.servlet.ServletContext;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class TestJersyFacade {
	
	@Context ServletContext context;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
				+ "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	}

	@GET
	@Path("name/{name}")
	@Produces(MediaType.TEXT_HTML)
	public String sayYourName(@PathParam("name") String firstName, @QueryParam("sirName")@DefaultValue("noSirName") String secoundName){
		return "<html><body><h1>hello " + firstName + " " + secoundName + "</h1></body></html>";
	}
	
	@GET
	@Path("person/{name}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Person getPerson(@PathParam("name") String firstName, @QueryParam("sirName")@DefaultValue("noSirName") String secoundName){
		Person person = new Person(firstName, secoundName);
		return person;
	}
}
