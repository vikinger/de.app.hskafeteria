package de.app.hskafeteria.exception;

public class AccountNotFoundException extends RuntimeException {
	private String message;
	
	public AccountNotFoundException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
