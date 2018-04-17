package main.java.excilys.cdb.exceptions;

public class InvalidDateException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDateException(String message) {
		super( "Exception - Date non valide :" + message);
	}
}
