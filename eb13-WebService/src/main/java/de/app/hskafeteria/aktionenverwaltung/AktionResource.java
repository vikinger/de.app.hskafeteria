package de.app.hskafeteria.aktionenverwaltung;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;



public interface AktionResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/")
	public Response getAllAktionen();
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/")
	public Response createAktion(JAXBElement<Aktion> aktion);
	
	@DELETE
	@Path("/{id}/")
	public Response deleteAktion(@PathParam("id") String aktionId);
	
	@POST
	@Path("/{id}/")
	public Response updateAktion(JAXBElement<Aktion> aktion);
}