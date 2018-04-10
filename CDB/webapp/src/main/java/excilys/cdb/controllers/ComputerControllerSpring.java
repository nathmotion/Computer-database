package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.ACTION_PAGE;
import static main.java.excilys.cdb.constantes.ConstantesControllers.ID;
import static main.java.excilys.cdb.constantes.ConstantesControllers.LIMIT;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import main.java.excilys.cdb.mapper.MapperCompany;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
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
	private Page<Computer> page;
	int numPage;
	int nbElementP;

	@Autowired
	MessageSource messageSource;

	public ComputerControllerSpring(ComputerServiceImpl computerService, CompanyServiceImpl companyService,
			MapperComputer computerMap, MapperCompany companyMap, ValidatorComputer computerValidator) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMap = companyMap;
		this.computerMap = computerMap;
		this.computerValidator = computerValidator;
		this.numPage = 0;
		this.nbElementP = 10;
	}

	/***
	 * ======[ GET ] : VIEW DASHBOARD ======
	 */
	@GetMapping("/dashboard.html")
	public String getDashboard(@RequestParam Map<String, String> params, Locale Locale, Model model) {
		try {
			model = affichagePage(params.get(ACTION_PAGE), params.get(LIMIT), params.get("numpage"), params.get(SEARCH),
					params.get(TYPE_ORDER), model);
			return VIEW_BOARD;
		} catch (DatabaseException e) {
			return "500";
		}
	}

	/***
	 * ====== [ POST ] : VIEW DASHBOARD ======
	 */
	@PostMapping("/dashboard.html")
	public String submitDashboard(@RequestParam Map<String, String> params, Locale Locale,
			@RequestParam(value = SELECTION, required = false) List<String> selection, Model model) {
		try {
			String search = null;
			String typeOrder = null;
			gestionDelete(selection);
			model = affichagePage(params.get(ACTION_PAGE), params.get(LIMIT), params.get("numpage"), search, typeOrder,
					model);
			return VIEW_BOARD;
		} catch (DatabaseException e) {
			return "500";
		}
	}

	/**
	 * ===== [ GET ] : VIEW ADD COMPUTER ======
	 */
	@GetMapping("/addComputer.html")
	public String getAddComputer(Model model) {
		model.addAttribute("computerDto", new ComputerDto());
		model = affichageCompany(model);
		return VIEW_ADD_COMPUTER;
	}

	/**
	 * ===== [ POST ] : VIEW ADD COMPUTER =====
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
			gestionCreation(computerDto);
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
	 * ====== [ POST ] : SUBMIT EDIT COMPUTER ======
	 */
	@PostMapping("/editComputer.html")
	public String submitEditComputer(@ModelAttribute("ComputerDto") @Valid ComputerDto computerDto,
			BindingResult binding, Model model) {
		List<String> errors = computerValidator.validate(computerDto);
		if (errors.size() == 0) {
			model = editRequest(computerDto, model);
			return "redirect:" + VIEW_BOARD + ".html";
		} else {
			model.addAttribute("errors", errors);
			return VIEW_EDIT_COMPUTER;
		}
	}

	/**
	 * ======= [ REQUETE ] : EDITION D'UN COMPUTER =======
	 * 
	 * @param request
	 * @return
	 */
	public Model editRequest(ComputerDto computerDto, Model request) {
		
		if (!computerDto.getCompanyId().equals("") || computerDto.getCompanyId()!= null) {
			computerDto.setCompanyId(null);
			computerDto.setCompanyName(null);
		}
		Computer computer = computerMap.mapToEntity(computerDto);
		computerService.update(computer);

		return request;
	}

	/**
	 * ===== [ REQUETE ] : SUPPRESSION DES COMPUTER =======
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
	 * ======== [ GESTION ]: AFFICHAGE DES PAGE DE COMPUTER ET LA RECHERCHE ======
	 * 
	 * @param request
	 * @return
	 */
	public Model affichagePage(String actionPage, String limit, String stringNumPage, String search, String order,
			Model model) throws DatabaseException {
		int sortNum = 0;

		int count = computerService.getNbTotal();

		if (order != null) {
			sortNum = Integer.parseInt(order);
		}

		if (actionPage == null) {
			actionPage = "";
		}
		if (limit != null) {
			nbElementP = Integer.parseInt(limit);
		}
		if (stringNumPage != null) {
			numPage = Integer.parseInt(stringNumPage);
		}
		if (search != null && !search.equals("null")) {
			System.out.println(" RECHERCHE");
			count = (int) computerService.getNbSearch(search);
			System.out.println("			count = " + count);
			page = computerService.getPageByName(numPage, nbElementP, search);

		} else {
			handlePageOrder(computerService, sortNum);
		}

		ArrayList<ComputerDto> listeDtoComputers = new ArrayList<>();
		for (Computer computer : page.getContent()) {
			ComputerDto dtoComputer = new ComputerDto();
			dtoComputer = computerMap.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		model.addAttribute("page", page);
		model.addAttribute("ListeComputer", listeDtoComputers);
		model.addAttribute("nbComputer", count);
		return model;
	}

	public void handlePageOrder(ComputerServiceImpl computerService, int sortNum) {
		if (sortNum != 0) {
			numPage = 0;
			if (sortNum == 1) {
				page = computerService.getPageByOrder(numPage, nbElementP, Sort.Direction.ASC);
			} else {
				page = computerService.getPageByOrder(numPage, nbElementP, Sort.Direction.DESC);
			}
		} else {
			page = computerService.getPage(numPage, nbElementP);
		}
	}

	/***
	 * ======= [ GESTION ]: RECUPERATION DE LA LISTE DE COMPANY =========
	 * 
	 * @param request
	 * @return
	 */
	public Model affichageCompany(Model request) {
		List<Company> companys = companyService.getAll();
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
	 * ======= [ REQUETE ] LA CREATION D'UN COMPUTER ===========
	 * 
	 * @param stringName
	 * @param stringDateIntro
	 * @param stringDateDisc
	 * @param stringCompanyId
	 */
	public void gestionCreation(ComputerDto computerDto) {
		if (!computerDto.getCompanyId().equals("") || computerDto.getCompanyId()!= null) {
			computerDto.setCompanyId(null);
			computerDto.setCompanyName(null);
		}
		Computer computer = computerMap.mapToEntity(computerDto);
		computerService.create(computer);
	}

}
