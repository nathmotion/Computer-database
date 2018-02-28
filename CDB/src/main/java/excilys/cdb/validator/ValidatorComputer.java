package main.java.excilys.cdb.validator;

import java.time.format.DateTimeParseException;
import javax.naming.NamingException;

public enum ValidatorComputer {
	INSTANCE;
	
	public void validationName(String name)throws NullPointerException {
		if(name.isEmpty() || name.equals(null) || name.equals("") ) {
			throw new NullPointerException(" name est vide ! ");
		}		
	}
	
	public void validationDateIntro(String date) throws DateTimeParseException {
		
	}
	
	public void validationDateDisc(String date) throws DateTimeParseException {
		
	}
	
	public void validationCompany_id(String id) throws IllegalArgumentException {
		if(id== null) {
			throw new IllegalArgumentException(" company id est null ");
		}
	}
}
