package main.java.excilys.cdb.exceptions;

public class InvalidDateException extends Exception {
	
	public InvalidDateException(String message) {
		super( "Exception - Date non valide :" + message);
	}
}
