package main.java.excilys.cdb.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public enum ValidatorComputer {
	INSTANCE;
	
	LocalDate dateIntroTmp;
	
	public void validationName(String name)throws NullPointerException {
		if(name.isEmpty() || name.equals(null) || name.equals("") ) {
			throw new NullPointerException(" name est vide ! ");
		}		
	}
	
	public void validationDateIntro(String date) throws DateTimeParseException {
		System.out.println(" lol "+ date);
		if(date!=null && !date.equals("") && !date.isEmpty()) {
			dateIntroTmp = LocalDate.parse(date);
		}
	}
	
	public void validationDateDisc(String date) throws DateTimeException {
		LocalDate dateDisc=null;
		if(date!=null  && !date.equals("") && date.isEmpty()){
			dateDisc=LocalDate.parse(date);
			if(dateIntroTmp.getYear()>dateDisc.getYear()) {
				throw new DateTimeException("Date discontinued est avant la date de introduced");
			}
		}
		
	}
	
	public void validationCompany_id(String id) throws IllegalArgumentException {
		if(id== null && id.equals("") && id.isEmpty() ){
			throw new IllegalArgumentException(" company id est null ");
		}
	}
}
