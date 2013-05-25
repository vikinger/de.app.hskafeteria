package de.app.hskafeteria.aktionenverwaltung;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;



@Path("/Aktion")
public class AktionResourceImpl implements AktionResource {
	@Autowired
	private AktionDao aktionDao;
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response getAllAktionen() {
		List<Aktion> aktion = aktionDao.getAllAktionen();
		GenericEntity<List<Aktion>> entity = new GenericEntity<List<Aktion>>(aktion){};
		return Response.status(200).entity(entity).build();
	}

	@Override
	public Response createAktion(
			JAXBElement<Aktion> aktion) {
		aktionDao.createAktion(aktion.getValue());
		aktionDao.shutdown();
		return Response.status(201).build();
	}

	@Override
	public Response deleteAktion(String aktionId) {
		aktionDao.deleteAktion(aktionId);
		aktionDao.shutdown();
		return Response.status(Status.OK).build();
	}

	
	@Override
	public Response updateAktion(JAXBElement<Aktion> aktion) {
		aktionDao.updateAktion(aktion.getValue());
		return Response.status(200).entity(aktion).build();
	}

}
