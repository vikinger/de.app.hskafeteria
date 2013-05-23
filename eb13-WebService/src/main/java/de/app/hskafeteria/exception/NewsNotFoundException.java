package de.app.hskafeteria.exception;

public class NewsNotFoundException extends RuntimeException {

	
		private String message;
		
		public NewsNotFoundException(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
	
}
