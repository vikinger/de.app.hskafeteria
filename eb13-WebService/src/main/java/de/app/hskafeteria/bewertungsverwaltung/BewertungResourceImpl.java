package de.app.hskafeteria.bewertungsverwaltung;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;

@Path("/Bewertung")
public class BewertungResourceImpl implements BewertungResource {
	@Autowired
	private BewertungDao bewertungDao;
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response getAllBewertungen() {
		List<Bewertung> bewertungen = bewertungDao.getAllBewertungen();
		GenericEntity<List<Bewertung>> entity = new GenericEntity<List<Bewertung>>(bewertungen){};
		return Response.status(200).entity(entity).build();
	}

	@Override
	public Response createBewertung(
			JAXBElement<Bewertung> bewertung) {
		bewertungDao.createBewertung(bewertung.getValue());
		bewertungDao.shutdown();
		return Response.status(201).build();
	}
	
	@Override
	public Response getBewertungenFromAngebot(String angebotTitel) {
		List<Bewertung> bewertungen = bewertungDao.getBewertungenByAngebotTitel(angebotTitel);
		GenericEntity<List<Bewertung>> entity = new GenericEntity<List<Bewertung>>(bewertungen){};
		return Response.status(200).entity(entity).build();
	}

	@Override
	public Response deleteBewertung(String bewertungId) {
		bewertungDao.deleteBewertung(bewertungId);
		bewertungDao.shutdown();
		return Response.status(Status.OK).build();
	}
}
