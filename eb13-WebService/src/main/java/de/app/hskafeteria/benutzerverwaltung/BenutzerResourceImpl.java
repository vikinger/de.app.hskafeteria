package de.app.hskafeteria.benutzerverwaltung;


import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;



@Path("/Benutzer")
public class BenutzerResourceImpl implements BenutzerResource {
	@Autowired
	private BenutzerDao benutzerDao;
	@Context
	UriInfo uriInfo;

	@Override
	public Response createBenutzer(JAXBElement<Benutzer> benutzer) {
		benutzerDao.createBenutzer(benutzer.getValue());
		benutzerDao.shutdown();
		return Response.status(201).build();
	}

	@Override
	public Response getBenutzerByEmail(String email) {
		Benutzer benutzer = benutzerDao.getBenutzerByEmail(email);
		return Response.status(200).entity(benutzer).build();
	}

	@Override
	public Response getAllBenutzers() {
		Benutzers benutzers = benutzerDao.getAllBenutzers();
		return Response.status(200).entity(benutzers).build();
	}

	@Override
	public Response changePassword(String userNewPassword) {
		String delim = "-";
		String[] parsedStr = userNewPassword.split(delim);
		String userEmail = parsedStr[0];
		String newPassword = parsedStr[1];
		benutzerDao.updatePassword(userEmail, newPassword);
		return Response.status(Status.CREATED).build();
	}
}

