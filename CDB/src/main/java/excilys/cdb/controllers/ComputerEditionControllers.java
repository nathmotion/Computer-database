package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPANY_ID;
import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPUTER_NAME;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_DISC;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_INTRO;
import static main.java.excilys.cdb.constantes.ConstantesControllers.ID;
import static main.java.excilys.cdb.constantes.ConstantesControllers.VIEW_EDIT_COMPUTER;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.exceptions.InvalidDateException;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.ComputerServiceImpl;
import main.java.excilys.cdb.validator.ValidatorComputer;

@Controller
public class ComputerEditionControllers {

	@Autowired
	private ValidatorComputer validatorComputer;
	@Autowired
	private ComputerServiceImpl serviceComputer;
	@Autowired
	private MapperComputer mapComputer;

	private int idComputer;

	/***
	 * ====== GET : ======
	 */
	@GetMapping("/editComputer.html")
	public String getEditComputer(@RequestParam(value = ID, required = false) String id, Model model) {
		Optional<Computer> optComputer = serviceComputer.findById(idComputer);
		if (optComputer.isPresent()) {
			DtoComputer dtoComputer = mapComputer.mapToDto(optComputer.get());
			model.addAttribute("computer", dtoComputer);
		} else {
			// LOGGER.error(" L'item computer" + idComputer + " n'est pas trouve");
			model.addAttribute("error", "Computer not found");
		}
		return VIEW_EDIT_COMPUTER;
	}

	/***
	 * ====== POST : SUBMIT ======
	 */
	@PostMapping("/editComputer.html")
	public String submit(@RequestParam(value = ID, required = false) String id,
			@RequestParam(value = COMPUTER_NAME, required = false) String computerName,
			@RequestParam(value = DATE_INTRO, required = false) String introduced,
			@RequestParam(value = DATE_DISC, required = false) String discontinued,
			@RequestParam(value = COMPANY_ID, required = false) String companyId, Model model) {
		model = editRequest(id, computerName, introduced, discontinued, companyId, model);
		return VIEW_EDIT_COMPUTER;
	}

	/**
	 * ======= GESTION DE LA REQUETE DE EDITION D'UN COMPUTER =======
	 * 
	 * @param request
	 * @return
	 */
	public Model editRequest(String stringId, String stringName, String stringDateIntro, String stringDateDisc,
			String stringCompanyId, Model request) {
		Boolean validate = true;

		validate = validations(stringName, stringDateIntro, stringDateDisc, stringCompanyId, request);
		if (validate) {
			Company company = new Company(Long.valueOf(stringCompanyId), "");
			DtoComputer dtoComputer = new DtoComputer(stringId, stringName, stringDateIntro, stringDateDisc, company);
			Computer computer = mapComputer.mapToEntity(dtoComputer);
			serviceComputer.update(computer);
		}
		return request;
	}

	/**
	 * ======= TESTE ET VALIDE SI LES DONNEES PASSE PAR LE FORMULAIRE SONT CORRECT
	 * =====
	 * 
	 * @param name
	 * @param dateIntro
	 * @param dateDisc
	 * @param companyId
	 * @return
	 */
	public Boolean validations(String name, String dateIntro, String dateDisc, String companyId, Model model) {
		Boolean validate = true;
		try {
			validatorComputer.validationName(name);
		} catch (NullPointerException e) {
			// LOGGER.error("nom saisie non valide !");
			validate = false;
			model.addAttribute("error", "nom saisie non valide !");
		}

		try {
			validatorComputer.validationDateIntro(dateIntro);
		} catch (IllegalArgumentException e) {
			// LOGGER.error("La date d'introducion n'est pas valide");
			validate = false;
			model.addAttribute("error", "La date d'introducion n'est pas valide");
		}

		try {
			validatorComputer.validationDateDisc(dateIntro, dateDisc);
		} catch (InvalidDateException e) {
			// LOGGER.error(e.getMessage());
			validate = false;
			model.addAttribute("error", "la date de retrait n'ont valide ! ");
		}
		return validate;

	}

}
