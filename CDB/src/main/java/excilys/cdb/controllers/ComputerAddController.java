package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPANY_ID;
import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPUTER_NAME;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_DISC;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_INTRO;
import static main.java.excilys.cdb.constantes.ConstantesControllers.VIEW_ADD_COMPUTER;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.excilys.cdb.dto.DtoCompany;
import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.exceptions.InvalidDateException;
import main.java.excilys.cdb.mapper.MapperCompany;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.CompanyServiceImpl;
import main.java.excilys.cdb.service.ComputerServiceImpl;
import main.java.excilys.cdb.validator.ValidatorComputer;

@Controller
public class ComputerAddController {

	@Autowired
	MapperCompany mapCompany;
	@Autowired
	CompanyServiceImpl serviceCompany;
	@Autowired
	ValidatorComputer validatorComputer;
	@Autowired
	MapperComputer mapperComputer;
	@Autowired
	ComputerServiceImpl serviceComputer;

	/**
	 * ===== DO GET ======
	 */
	@GetMapping("/addComputer.html")
	public String getAddComputer(Model model) {
		model = affichageCompany(model);
		return VIEW_ADD_COMPUTER;
	}
	/**
	 * ===== DO POST =====
	 */
	@PostMapping("/addComputer.html")
	private String submitAddComputer(@RequestParam(value = COMPUTER_NAME, required = false) String computerName,
			@RequestParam(value = DATE_INTRO, required = false) String introduced,
			@RequestParam(value = DATE_DISC, required = false) String discontinued,
			@RequestParam(value = COMPANY_ID, required = false) String companyId, Model model) {

		gestionCreation(computerName, introduced, discontinued, companyId, model);
		model = affichageCompany(model);
		return VIEW_ADD_COMPUTER;
	}

	/***
	 * ======= GESTION DE LA RECUPERATION DE LA LISTE DE COMPANY =========
	 * 
	 * @param request
	 * @return
	 */
	public Model affichageCompany(Model request) {
		List<Company> companys = serviceCompany.getAllEntities();
		List<DtoCompany> listeDtoCompany = new ArrayList<>();

		for (Company company : companys) {
			DtoCompany dtoCompany = new DtoCompany();
			dtoCompany = mapCompany.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		request.addAttribute("ListeCompany", listeDtoCompany);
		return request;
	}

	/**
	 * ======= GERE LA CREATION D'UN COMPUTER : APPEL DE LA REQUETE CREATION FOURNI PAR LE SERVICE ========
	 * 
	 * @param stringName
	 * @param stringDateIntro
	 * @param stringDateDisc
	 * @param stringCompanyId
	 */
	public void gestionCreation(String computerName, String introduced, String discontinued, String companyId,
			Model model) {
		Boolean validate = true;
		validate = validations(computerName, introduced, discontinued, companyId, model);
		if (validate) {
			DtoComputer dtoComputer = new DtoComputer(computerName, introduced, discontinued, companyId, "");
			Computer computer = mapperComputer.mapToEntity(dtoComputer);
			serviceComputer.create(computer);
		}

	}

	/**
	 * ======= TESTE ET VALIDE SI LES DONNEES PASSE PAR LE FORMULAIRE SONT CORRECT=====
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
