package de.app.hskafeteria.angeboteverwaltung;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;



@Path("/Angebot")
public class AngebotResourceImpl implements AngebotResource {
	@Autowired
	private AngebotDao angebotDao;
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response getAllAngebote() {
		List<Angebot> angebot = angebotDao.getAllAngebote();
		GenericEntity<List<Angebot>> entity = new GenericEntity<List<Angebot>>(angebot){};
		return Response.status(200).entity(entity).build();
	}

	@Override
	public Response createAngebot(
			JAXBElement<Angebot> angebot) {
		angebotDao.createAngebot(angebot.getValue());
		angebotDao.shutdown();
		return Response.status(201).build();
	}

}
