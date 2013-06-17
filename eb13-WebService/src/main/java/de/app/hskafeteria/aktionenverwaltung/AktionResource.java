package de.app.hskafeteria.aktionenverwaltung;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;



public interface AktionResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllAktionen();
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createAktion(JAXBElement<Aktion> aktion);
	
}