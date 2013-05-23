package de.app.hskafeteria.authentication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import de.app.hskafeteria.benutzerverwaltung.Benutzer;
import de.app.hskafeteria.benutzerverwaltung.BenutzerDao;


@Path("/Login")
public class AuthenticationCheckService {
	@Autowired
	private BenutzerDao benutzerDao;

	@GET
	@Path("/{emailpassword}")
	public Response login(@PathParam("emailpassword") String emailpassword) {
		String delim = "-";
		String[] parsedStr = emailpassword.split(delim);
		String email = parsedStr[0];
		String password = parsedStr[1];
		Benutzer benutzer = benutzerDao.getBenutzerByEmail(email);
		// Check
		if (benutzer == null)
			return Response.status(Status.UNAUTHORIZED).build();
		if ((benutzer.getEmail().equals(email)) && (benutzer.getPasswort().equals(password))) {
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
