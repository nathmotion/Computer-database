package main.java.excilys.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import main.java.excilys.cdb.dto.ComputerDto;
import main.java.excilys.cdb.exceptions.InvalidDateException;
import main.java.excilys.cdb.model.Computer;

@Component
public class ValidatorComputer {

	public List<String> validate(ComputerDto computer) {
		if (computer == null) {
			throw new IllegalArgumentException(" Computer is null  ! ");
		}
		List<String> errors = new ArrayList<String>();
		try {
			validateName(computer.getName());
		} catch (NullPointerException e) {
			// LOGGER.error("nom saisie non valide !");
			errors.add("nom saisie non valide");
		}

		try {
			validateDateIntro(computer.getDate_introduced());
		} catch (IllegalArgumentException e) {
			// LOGGER.error("La date d'introducion n'est pas valide");
			errors.add("La date d'introducion n'est pas valide");

		}

		try {
			validateDateDisc(computer.getDate_introduced(), computer.getDate_discontinued());
		} catch (InvalidDateException e) {
			// LOGGER.error(e.getMessage());
			errors.add("la date de retrait n'ont valide ! ");
		}
		return errors;

	}

	private void validateName(String name) throws NullPointerException {
		if (name.equals(null) || name.equals("")) {
			throw new NullPointerException(" Le nom de l'ordinateur n'as pas été saisie ou incorrect ! ");
		}
	}

	private void validateDateIntro(String date) throws DateTimeParseException {
		if (date != null && !date.equals("")) {
		}
	}

	private void validateDateDisc(String introduced, String discontinued) throws InvalidDateException {

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
