package de.app.hskafeteria.bewertungsverwaltung;

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

public interface BewertungResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/")
	public Response getAllBewertungen();
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/")
	public Response createBewertung(JAXBElement<Bewertung> bewertung);

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{titel}/")
	public Response getBewertungenFromAngebot(@PathParam("titel") String angebotTitel);
	
	@DELETE
	@Path("/{id}/")
	public Response deleteBewertung(@PathParam("id") String bewertungId);
}