package de.app.hskafeteria.exception;

public class NewsNotFoundException extends RuntimeException {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String message;
		
		public NewsNotFoundException(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
	
}
