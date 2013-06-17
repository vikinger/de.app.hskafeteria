package de.app.hskafeteria.newsverwaltung;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;



public interface NewsResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllNews();
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createNews(JAXBElement<News> news);

}