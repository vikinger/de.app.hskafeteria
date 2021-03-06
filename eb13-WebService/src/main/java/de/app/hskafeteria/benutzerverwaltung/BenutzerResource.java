package de.app.hskafeteria.benutzerverwaltung;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;



public interface BenutzerResource {
	@POST
	@Consumes("application/xml")
	public Response createBenutzer(JAXBElement<Benutzer> benutzer);

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{email}/")
	public Response getBenutzerByEmail(@PathParam("email") String email);

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllBenutzers();
	
	@POST
	@Path("/newpassword/{userNewPassword}")
	public Response changePassword(@PathParam("userNewPassword") String userNewPassword);
}

