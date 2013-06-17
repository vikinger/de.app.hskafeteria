package de.app.hskafeteria.angeboteverwaltung;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;



public interface AngebotResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllAngebote();
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createAngebot(JAXBElement<Angebot> angebot);
	
}