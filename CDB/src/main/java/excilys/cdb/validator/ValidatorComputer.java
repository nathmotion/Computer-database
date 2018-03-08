package main.java.excilys.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import main.java.excilys.cdb.exceptions.InvalidDateException;

public enum ValidatorComputer {
	INSTANCE;

	public void validationName(String name) throws NullPointerException {
		if (name.equals(null) || name.equals("")) {
			throw new NullPointerException(" Le nom de l'ordinateur n'as pas été saisie ou incorrect ! ");
		}
	}

	public void validationDateIntro(String date) throws DateTimeParseException {
		if (date != null && !date.equals("")) {
		}
	}

	public void validationDateDisc(String introduced, String discontinued) throws InvalidDateException {

		if (discontinued != null && !discontinued.equals("")) {
			if (introduced != null && !introduced.equals("")) {
				LocalDate dateIntro = LocalDate.parse(introduced);
				LocalDate dateDisc = LocalDate.parse(discontinued);
				if (dateDisc.isBefore(dateIntro)) {
					throw new InvalidDateException("la date de discontinued est avant la date de introduced");
				}
			} else {
				throw new InvalidDateException(
						"Impossible d'ajouter une date de 'Discontinued' s'il aucune date d'introduction pour le 'computer'");
			}
		}

	}

}
