package de.app.hskafeteria.exception;

public class DatabaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public DatabaseException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
