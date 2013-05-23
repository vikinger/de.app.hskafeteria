package de.app.hskafeteria.exception;

public class DatabaseException extends RuntimeException {
	private String message;
	
	public DatabaseException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
