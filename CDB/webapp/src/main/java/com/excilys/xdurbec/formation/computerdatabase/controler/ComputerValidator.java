package com.excilys.xdurbec.formation.computerdatabase.controler;

import java.sql.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.xdurbec.formation.computerdatabase.dto.ComputerDTO;

@Component
public class ComputerValidator implements Validator {
	
	private ComputerValidator() { }
	
	private static boolean dateLogicValidation(ComputerDTO computerDTO) {
			return computerDTO.getIntroduced() == null  || computerDTO.getDiscontinued() == null 
					|| computerDTO.getIntroduced().equals("")  || computerDTO.getDiscontinued().equals("") 
					|| Date.valueOf(computerDTO.getDiscontinued()).after(Date.valueOf(computerDTO.getIntroduced()));
	}
	
	

	private static boolean tooLargeName(ComputerDTO computerDTO)  {
		return computerDTO.getName().length() > 30;
}

	private static boolean tooShortName(ComputerDTO computerDTO)  {
		return computerDTO.getName().length() < 3;
}
	@Override
	public boolean supports(Class<?> paramClass) {
		return ComputerDTO.class.equals(paramClass);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.required");
		ComputerDTO computerDTO = (ComputerDTO) object;
		if (!dateLogicValidation(computerDTO)) {
			errors.rejectValue("introduced", "logicalError");
		}
		if (tooLargeName(computerDTO)) {
			errors.rejectValue("name", "too.short");
		}
		if (tooShortName(computerDTO)) {
			errors.rejectValue("name", "too.long");
		}
		
	}
	
	
}
