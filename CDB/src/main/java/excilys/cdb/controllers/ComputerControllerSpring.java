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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.excilys.cdb.dto.CompanyDto;
import main.java.excilys.cdb.dto.ComputerDto;
import main.java.excilys.cdb.exceptions.DatabaseException;
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
	@Autowired
	MessageSource messageSource;

	Page<Computer> page = new Page<Computer>(0, 0, 10);
	private int idComputer;

	public ComputerControllerSpring(ComputerServiceImpl computerService, CompanyServiceImpl companyService,
			MapperComputer computerMap, MapperCompany companyMap, ValidatorComputer computerValidator) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMap = companyMap;
		this.computerMap = computerMap;
		this.computerValidator = computerValidator;
	}

	/***
	 * ====== GET : VIEW DASHBOARD ======
	 */
	@GetMapping("/dashboard.html")
	public String getDashboard(@RequestParam Map<String, String> params, Locale Locale, Model model) {
		try {
			model = affichagePage(params.get(ACTION_PAGE), params.get(LIMIT), params.get(SEARCH),
					params.get(TYPE_ORDER), params.get(ORDER_CMP), model);
			return VIEW_BOARD;
		} catch (DatabaseException e) {
			return "500";
		}
	}

	/***
	 * ====== POST : VIEW DASHBOARD ======
	 */
	@PostMapping("/dashboard.html")
	public String submitDashboard(@RequestParam Map<String, String> params, Locale Locale,
			@RequestParam(value = SELECTION, required = false) List<String> selection, Model model) {
		try {
			String search = null;
			String orderCmp = null;
			String typeOrder = null;
			gestionDelete(selection);
			model = affichagePage(params.get(ACTION_PAGE), params.get(LIMIT), search, typeOrder, orderCmp, model);
			return VIEW_BOARD;
		} catch (DatabaseException e) {
			return "500";
		}
	}

	/**
	 * ===== GET : VIEW ADD COMPUTER ======
	 */
	@GetMapping("/addComputer.html")
	public String getAddComputer(Model model) {
		model.addAttribute("computerDto", new ComputerDto());
		model = affichageCompany(model);
		return VIEW_ADD_COMPUTER;
	}

	/**
	 * ===== POST : VIEW ADD COMPUTER =====
	 */
	@PostMapping("/addComputer.html")
	private String submitAddComputer(@ModelAttribute("computerDto") @Valid ComputerDto computerDto,
			BindingResult binding, Model model) {
		if (binding.hasErrors()) {
			System.out.println(" le binding a trouve une erreur !");
			return VIEW_ADD_COMPUTER;
		}
		List<String> errors = computerValidator.validate(computerDto);
		if (errors.size() == 0) {
			gestionCreation(computerDto.getName(), computerDto.getDate_introduced(), computerDto.getDate_discontinued(),
					computerDto.getCompanyId());
			model = affichageCompany(model);
			return "redirect:" + VIEW_BOARD + ".html";
		} else {
			model.addAttribute("errors", errors);
			return VIEW_ADD_COMPUTER;
		}
	}

	/***
	 * ====== GET : Edit Computer ======
	 */
	@GetMapping("/editComputer.html")
	public String getEditComputer(@RequestParam(value = ID, required = false) String id, Model model) {
		try {
			Optional<Computer> optComputer = computerService.findById(Integer.parseInt(id));
			if (optComputer.isPresent()) {
				ComputerDto dtoComputer = computerMap.mapToDto(optComputer.get());
				model.addAttribute("computerDto", dtoComputer);
			} else {
				// LOGGER.error(" L'item computer" + idComputer + " n'est pas trouve");
				model.addAttribute("error", "Computer not found");
			}
			return VIEW_EDIT_COMPUTER;
		} catch (SQLException e) {
			return "500";
		}
	}

	/***
	 * ====== POST : SUBMIT EDIT COMPUTER ======
	 */
	@PostMapping("/editComputer.html")
	public String submitEditComputer(@ModelAttribute("ComputerDto") @Valid ComputerDto computerDto,
			BindingResult binding, Model model) {
		List<String> errors = computerValidator.validate(computerDto);
		if (errors.size() == 0) {
			model = editRequest(computerDto.getId(), computerDto.getName(), computerDto.getDate_introduced(),
					computerDto.getDate_discontinued(), computerDto.getCompanyId(), model);
			return "redirect:" + VIEW_BOARD + ".html";
		} else {
			model.addAttribute("errors", errors);
			return VIEW_EDIT_COMPUTER;
		}
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
		if (validate) {
			Company company = new Company(Long.parseLong(stringCompanyId), "");
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
	public void gestionDelete(List<String> selection) throws DatabaseException {
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
			Model model) throws DatabaseException {

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
	public void gestionCreation(String computerName, String introduced, String discontinued, String companyId) {
		ComputerDto dtoComputer = new ComputerDto(computerName, introduced, discontinued, companyId, "");
		Computer computer = computerMap.mapToEntity(dtoComputer);
		computerService.create(computer);
	}

}
