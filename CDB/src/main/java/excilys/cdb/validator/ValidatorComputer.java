package main.java.excilys.cdb.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public enum ValidatorComputer {
	INSTANCE;

	LocalDate dateIntroTmp;

	public void validationName(String name)throws NullPointerException {
		if(name.equals(null) || name.equals("") ) {
			throw new NullPointerException(" Le nom de l'ordinateur n'as pas été saisie ou incorrect ! ");
		}		
	}

	public void validationDateIntro(String date) throws DateTimeParseException {
		if(date!=null && !date.equals("")) {
			dateIntroTmp = LocalDate.parse(date);
		}
	}

	public void validationDateDisc(String date) throws DateTimeException {
		LocalDate dateDisc=null;
		if(date!=null  && !date.equals("")){
			dateDisc=LocalDate.parse(date);
			if(dateIntroTmp.isBefore(dateDisc)) {
				throw new DateTimeException("la date de discontinued est avant la date de introduced");
			}
		}

	}

	public void validationCompany_id(String id) throws IllegalArgumentException {
		if(id== null && id.equals("")){
			throw new IllegalArgumentException(" L'ID de la \"company\" est null ");
		}
	}
}
