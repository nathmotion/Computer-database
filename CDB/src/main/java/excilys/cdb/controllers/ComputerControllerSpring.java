package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.ACTION_PAGE;
import static main.java.excilys.cdb.constantes.ConstantesControllers.ID;
import static main.java.excilys.cdb.constantes.ConstantesControllers.LIMIT;
import static main.java.excilys.cdb.constantes.ConstantesControllers.ORDER_CMP;
import static main.java.excilys.cdb.constantes.ConstantesControllers.SEARCH;
import static main.java.excilys.cdb.constantes.ConstantesControllers.SELECTION;
import static main.java.excilys.cdb.constantes.ConstantesControllers.TYPE_ORDER;
import static main.java.excilys.cdb.constantes.ConstantesControllers.VIEW_ADD_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstantesControllers.VIEW_BOARD;
import static main.java.excilys.cdb.constantes.ConstantesControllers.VIEW_EDIT_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPUTER_NAME;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_DISC;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_INTRO;
import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPANY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.excilys.cdb.dto.CompanyDto;
import main.java.excilys.cdb.dto.ComputerDto;
import main.java.excilys.cdb.exceptions.InvalidDateException;
import main.java.excilys.cdb.mapper.MapperCompany;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;
import main.java.excilys.cdb.service.CompanyServiceImpl;
import main.java.excilys.cdb.service.ComputerServiceImpl;
import main.java.excilys.cdb.validator.ValidatorComputer;

@Controller
public class ComputerControllerSpring {

	private final ComputerServiceImpl computerService;
	private final CompanyServiceImpl companyService;
	private final MapperComputer computerMap;
	private final MapperCompany companyMap;
	private ValidatorComputer computerValidator;
	Page<Computer> page = new Page<Computer>(0, 0, 10);
	private int idComputer;

	public ComputerControllerSpring(ComputerServiceImpl computerService, CompanyServiceImpl companyService,
			MapperComputer computerMap, MapperCompany companyMap) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMap = companyMap;
		this.computerMap = computerMap;
	}

	/***
	 * ====== GET : VIEW DASHBOARD ======
	 */
	@GetMapping("/dashboard.html")
	public String getDashboard(@RequestParam(value = ACTION_PAGE, required = false) String action,
			@RequestParam(value = LIMIT, required = false) String limit,
			@RequestParam(value = SEARCH, required = false) String search,
			@RequestParam(value = ORDER_CMP, required = false) String orderCmp,
			@RequestParam(value = TYPE_ORDER, required = false) String typeOrder, Model model) {

		model = affichagePage(action, limit, search, typeOrder, orderCmp, model);
		return VIEW_BOARD;
	}

	/***
	 * ====== POST : VIEW DASHBOARD ======
	 */
	@PostMapping("/dashboard.html")
	public String submitDashboard(@RequestParam(value = ACTION_PAGE, required = false) String action,
			@RequestParam(value = LIMIT, required = false) String limit,
			@RequestParam(value = SEARCH, required = false) String search,
			@RequestParam(value = ORDER_CMP, required = false) String orderCmp,
			@RequestParam(value = SELECTION, required = false) List<String> selection,
			@RequestParam(value = TYPE_ORDER, required = false) String typeOrder, Model model) {
		search = null;
		orderCmp = null;
		typeOrder = null;
		gestionDelete(selection);
		model = affichagePage(action, limit, search, typeOrder, orderCmp, model);
		return VIEW_BOARD;
	}

	/**
	 * ===== GET : VIEW ADD COMPUTER ======
	 */
	@GetMapping("/addComputer.html")
	public String getAddComputer(Model model) {
		model = affichageCompany(model);
		return VIEW_ADD_COMPUTER;
	}

	/**
	 * ===== POST : VIEW ADD COMPUTER =====
	 */
	@PostMapping("/addComputer.html")
	private String submitAddComputer(@RequestParam Map<String,String> param,BindingResult binding ,Model model) {

		gestionCreation(param.get(COMPUTER_NAME), param.get(DATE_INTRO), param.get(DATE_DISC), param.get(COMPANY_ID), model);
		if (binding.hasErrors()) {
			System.out.println(" Bean validator a trouve une erreur ");
		} 
		model = affichageCompany(model);
		return VIEW_ADD_COMPUTER;
	}

	/***
	 * ====== GET : Edit Computer ======
	 */
	@GetMapping("/editComputer.html")
	public String getEditComputer(@RequestParam(value = ID, required = false) String id, Model model) {
		Optional<Computer> optComputer = computerService.findById(idComputer);
		if (optComputer.isPresent()) {
			ComputerDto dtoComputer = computerMap.mapToDto(optComputer.get());
			model.addAttribute("computer", dtoComputer);
		} else {
			// LOGGER.error(" L'item computer" + idComputer + " n'est pas trouve");
			model.addAttribute("error", "Computer not found");
		}
		return VIEW_EDIT_COMPUTER;
	}

	/***
	 * ====== POST : SUBMIT EDIT COMPUTER ======
	 */
	@PostMapping("/editComputer.html")
	public String submitEditComputer(@RequestParam Map<String,String> param,BindingResult binding, Model model) {
		model = editRequest(param.get(ID), param.get(COMPUTER_NAME), param.get(DATE_INTRO), param.get(DATE_DISC), param.get(COMPANY_ID), model);
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
			ComputerDto dtoComputer = new ComputerDto(stringId, stringName, stringDateIntro, stringDateDisc, company);
			Computer computer = computerMap.mapToEntity(dtoComputer);
			computerService.update(computer);
		}
		return request;
	}

	/**
	 * ===== GESTION DE LA REQUETE DE SUPPRESSION DES COMPUTER =======
	 * 
	 * @param selection
	 */
	public void gestionDelete(List<String> selection) {
		for (String idString : selection) {
			Long idComputer = Long.parseLong(idString);
			Computer computer = new Computer();
			computer.setId(idComputer);
			computerService.delete(computer);
		}
	}

	/**
	 * ======== GESTION D'AFFICHAGE DES PAGE DE COMPUTER ET LA RECHERCHE ======
	 * 
	 * @param request
	 * @return
	 */
	public Model affichagePage(String actionPage, String limit, String search, String typeOrder, String orderCmp,
			Model model) {

		if (search != null) {
			page.setSearch(search);
		}
		int count = computerService.getNbTotal();

		if (typeOrder != null) {
			page.setTypeOrder(typeOrder);
		}
		if (orderCmp != null) {
			page.setOrderCmp(Integer.parseInt(orderCmp));

		}
		if (actionPage == null) {
			actionPage = "";
		}
		if (limit != null) {
			page.limit = Integer.parseInt(limit);
			page.offset = 0;
			page.current = (page.getOffset() / page.getLimit()) + 1;
		}

		actionPage(actionPage, count);

		if (page.getSearch() != null && !page.getSearch().equals("null")) {
			count = computerService.getNbSearch(page.getSearch());
			page = computerService.getPageByName(page, page.getSearch());
		} else {
			handlePageOrder(computerService);
		}

		ArrayList<ComputerDto> listeDtoComputers = new ArrayList<>();
		for (Computer computer : page.elementsPage) {
			ComputerDto dtoComputer = new ComputerDto();
			dtoComputer = computerMap.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		model.addAttribute("page", page);
		model.addAttribute("ListeComputer", listeDtoComputers);
		model.addAttribute("nbComputer", count);
		return model;
	}

	/**
	 * ===== GESTION DES ACTIONS DEMANDE POUR AFFICHER LA PAGE DE COMPUTER ====
	 * 
	 * @param action
	 * @param count
	 */
	public void actionPage(String action, int count) {
		switch (action) {
		case "next":
			if (page.current <= Math.ceil((count / page.limit))) {
				page.offset = page.offset + page.limit;
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			break;

		case "previous":
			if (page.current - 1 > 0) {
				page.offset = page.offset - page.limit;
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			break;
		}
	}

	public void handlePageOrder(ComputerServiceImpl computerService) {
		if (page.getTypeOrder() != null && !page.getTypeOrder().equals("null")) {
			page.setOrderCmp(page.getOrderCmp() + 1);
			if (page.getOrderCmp() % 2 == 0) {
				page.offset = 0;
				page = computerService.getPageByOrder(page, page.getTypeOrder(), true);
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			if (page.getOrderCmp() % 2 == 1) {
				page.offset = 0;
				page = computerService.getPageByOrder(page, page.getTypeOrder(), false);
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			page.setTypeOrder(null);
		} else {
			page = computerService.getPage(page);
		}
	}

	/***
	 * ======= GESTION DE LA RECUPERATION DE LA LISTE DE COMPANY =========
	 * 
	 * @param request
	 * @return
	 */
	public Model affichageCompany(Model request) {
		List<Company> companys = companyService.getAllEntities();
		List<CompanyDto> listeDtoCompany = new ArrayList<>();

		for (Company company : companys) {
			CompanyDto dtoCompany = new CompanyDto();
			dtoCompany = companyMap.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		request.addAttribute("ListeCompany", listeDtoCompany);
		return request;
	}

	/**
	 * ======= GERE LA CREATION D'UN COMPUTER : APPEL DE LA REQUETE CREATION FOURNI
	 * PAR LE SERVICE ========
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
			ComputerDto dtoComputer = new ComputerDto(computerName, introduced, discontinued, companyId, "");
			Computer computer = computerMap.mapToEntity(dtoComputer);
			computerService.create(computer);
		}

	}

	/**
	 * ======= TESTE ET VALIDE SI LES DONNEES PASSE PAR LE FORMULAIRE SONT
	 * CORRECT=====
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
			computerValidator.validationName(name);
		} catch (NullPointerException e) {
			// LOGGER.error("nom saisie non valide !");
			validate = false;
			model.addAttribute("error", "nom saisie non valide !");
		}

		try {
			System.out.println(" [Validation method] - dateIntro "+ dateIntro);
			computerValidator.validationDateIntro(dateIntro);
		} catch (IllegalArgumentException e) {
			// LOGGER.error("La date d'introducion n'est pas valide");
			validate = false;
			model.addAttribute("error", "La date d'introducion n'est pas valide");
		}

		try {
			computerValidator.validationDateDisc(dateIntro, dateDisc);
		} catch (InvalidDateException e) {
			// LOGGER.error(e.getMessage());
			validate = false;
			model.addAttribute("error", "la date de retrait n'ont valide ! ");
		}
		return validate;

	}
}
