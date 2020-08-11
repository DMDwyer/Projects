package myApp;

import java.io.IOException;  
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/parts")

public class PartResource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public List<Part> getParts() {	
		return PartDao.instance.getParts();
	}
	
	
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	@Path("{partNumber}")
	public Part getPart(@PathParam("partNumber") String number) {
		return PartDao.instance.search(number);
	}

	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postBook(@FormParam("number") String number, 
			@FormParam("name") String name, 
			@FormParam("description") String description, 
			@FormParam("stock") String stock,
			@Context HttpServletResponse servletResponse) throws IOException {
		
		System.out.println("Inside POST number = "+ number);
		System.out.println("name = " + name);
		
		Part part = new Part();
		
		part.setNumber(number);
		part.setName(name);
		part.setDescription(description);
		part.setStock(Integer.parseInt(stock));
		
		PartDao.instance.add(part);
		
		servletResponse.sendRedirect(".../parts");
		
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{number}")
	public void putPart(
			@FormParam("number") String number,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("stock") String stock,
			@Context HttpServletResponse servletResponse) throws IOException {
		System.out.println("Inside PUT SKU = "+ number);
		System.out.println("name = " + name);
		
		int stockNum = Integer.parseInt(stock);
		
		PartDao.instance.update(number, stockNum);
	}	
	
	
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{number}")
	public void deletePart(@PathParam("number") String number) throws IOException {
		System.out.println("Delete id = " + number);
		
		PartDao.instance.delete(number);
	}

	
}
