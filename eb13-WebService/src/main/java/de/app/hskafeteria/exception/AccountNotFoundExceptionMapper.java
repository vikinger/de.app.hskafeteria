package de.app.hskafeteria.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException> {

	@Override
	public Response toResponse(AccountNotFoundException e) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
