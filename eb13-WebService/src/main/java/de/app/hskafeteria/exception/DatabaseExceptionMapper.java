package de.app.hskafeteria.exception;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DatabaseExceptionMapper implements
		ExceptionMapper<DatabaseException> {

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
	public Response toResponse(DatabaseException e) {
		GenericEntity ge = new GenericEntity(e.getMessage()){};
		return Response.noContent().build();
	}

}
