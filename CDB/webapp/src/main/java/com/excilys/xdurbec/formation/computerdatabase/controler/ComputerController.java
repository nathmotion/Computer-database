package com.excilys.xdurbec.formation.computerdatabase.controler;

import java.security.Principal;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.xdurbec.formation.computerdatabase.dto.CompanyDTO;
import com.excilys.xdurbec.formation.computerdatabase.dto.CompanyMapperDTO;
import com.excilys.xdurbec.formation.computerdatabase.dto.ComputerDTO;
import com.excilys.xdurbec.formation.computerdatabase.dto.ComputerMapperDTO;
import com.excilys.xdurbec.formation.computerdatabase.model.ComputerAttributes;
import com.excilys.xdurbec.formation.computerdatabase.model.ComputerPage;
import com.excilys.xdurbec.formation.computerdatabase.service.CompanyService;
import com.excilys.xdurbec.formation.computerdatabase.service.ComputerService;
import com.excilys.xdurbec.formation.computerdatabase.service.ExceptionService;


@Controller
public class ComputerController {

	private  Logger log = LogManager.getLogger(this.getClass());

	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerValidator computerValidator;

	public ComputerController(CompanyService companyService, ComputerService computerService, ComputerValidator computerValidator) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.computerValidator = computerValidator;
	}



	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(computerValidator);
	}

	@GetMapping("login")
	public String login(ModelMap model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		if (error != null) {
			model.addAttribute("msg", "Invalid username and password!");
		}

		else if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}
		return "login";

	}

	@GetMapping("/")
	public String index(Model model, Principal principal) {
		model.addAttribute("message", "You are logged in as " + principal.getName());
		return "redirect:dashboard";
	}

	@GetMapping("dashboard")
	public String getDashboardPage(ModelMap model, @RequestParam Map<String, String> params) {
		int nbComputerByPage  = Integer.parseInt(params.getOrDefault(ServletString.NB_COMPUTER_BY_PAGE, "20"));
		String filter = params.getOrDefault(ServletString.NAME_SEARCHED, "");
		int nbComputerPage = getNbComputerPage(filter, nbComputerByPage);
		int pageNb = Integer.parseInt(params.getOrDefault(ServletString.PAGE, "1"));
		pageNb = pageNb < nbComputerPage ? pageNb : nbComputerPage;
		ComputerAttributes orderBy = orderBySet(params.getOrDefault(ServletString.ORDER_TYPE, ""));
		String oldOrderByString = params.getOrDefault("oldOrderBy", "");
		String ascendingOrderString = params.getOrDefault("orderDirection", "ASC");

		boolean ascendingOrder = orderDirectionSet(orderBy, orderBySet(oldOrderByString), ascendingOrderString);
		try {
			ComputerPage computerPage = computerService.getComputerPage(pageNb, nbComputerByPage, filter, orderBy, ascendingOrder);
			ascendingOrderString = ascendingOrder ? "ASC" : "DESC";
			model.addAttribute("oldOrderBy", orderBy.sqlName);
			model.addAttribute("orderDirection", ascendingOrderString);
			model.addAttribute("computerByPage", nbComputerByPage);
			model.addAttribute(ServletString.JSP_ORDER_VALUE, orderBy.sqlName);
			model.addAttribute(ServletString.JSP_COMPUTER_LIST, ComputerMapperDTO.toComputerDTOList(computerService.refresh(computerPage).getComputerList()));
			model.addAttribute(ServletString.JSP_SEARCH_VALUE, filter);
			model.addAttribute(ServletString.JSP_COMPUTER_COUNT, computerService.getComputerNumber(filter));
			model.addAttribute(ServletString.JSP_MAX_PAGE, nbComputerPage);
			model.addAttribute(ServletString.JSP_PAGE_NB, pageNb);
		} catch (ExceptionService e) {
			log.error(e);
		}
		return "dashboard";
	}


	@PostMapping("dashboard")
	public String deleteComputer(ModelMap model, @RequestParam Map<String, String> params) {
		String computerListToDelete = params.get(ServletString.COMPUTER_SELECTED);
		if (!computerListToDelete.equals(ServletString.VOID_STRING)) {
			for (String computerIdString : computerListToDelete.split(",")) {
				try {
					computerService.deleteById(Integer.valueOf(computerIdString));
				} catch (NumberFormatException | ExceptionService e) {
					log.error(e);
				}
			}
		}
		return "dashboard";
	}

	private int getNbComputerPage(String filter, int nbComputerByPage) {
		int nbPage;
		try {
			nbPage = computerService.getComputerNumber(filter) / nbComputerByPage;
			if (computerService.getComputerNumber(filter) % nbComputerByPage != 0) {
				nbPage++;
			}
			return nbPage;  
		} catch (ExceptionService e) {
			log.error(e);
			return 0;
		}
	}

	private ComputerAttributes orderBySet(String orderByString) {
		ComputerAttributes orderByTmp;
		switch (orderByString) {
		case "name":
			orderByTmp = ComputerAttributes.NAME;
			break;
		case "introduced":
			orderByTmp = ComputerAttributes.INTRODUCED;
			break;
		case "discontinued":
			orderByTmp = ComputerAttributes.DISCONTINUED;
			break;
		case "company":
			orderByTmp = ComputerAttributes.COMPANY_NAME;
			break;
		default :
			orderByTmp = ComputerAttributes.ID;
		}
		return orderByTmp;
	}

	private boolean orderDirectionSet(ComputerAttributes orderBy, ComputerAttributes oldOrderBy, String ascendingString) {
		boolean orderDirection = ascendingString != null ? ascendingString.equals("DESC") ? false : true : true;
		orderDirection = orderBy.equals(oldOrderBy) ? !orderDirection : orderDirection;
		return orderDirection;
	}

	@GetMapping("editComputer")
	public String getEditedComputerPage(ModelMap model, @RequestParam Map<String, String> params) {
		int computerId = Integer.parseInt(params.get("id"));
		try {
			model.addAttribute("ComputerDTO", ComputerMapperDTO.toComputerDTO(computerService.getById(computerId)));
			model.addAttribute("companyList", CompanyMapperDTO.toCompanyDTOList(companyService.getAll()));
		} catch (ExceptionService e) {
			log.error(e);
		}
		return "editComputer";
	}

	@PostMapping("editComputer")
	public String editComputer(@ModelAttribute("ComputerDTO") @Validated(ComputerDTO.class) ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model, @RequestParam Map<String, String> params) {
		try {
			if (!bindingResult.hasErrors()) {
				log.error(ComputerMapperDTO.toComputer(computerDTO));
				computerService.update(ComputerMapperDTO.toComputer(computerDTO));
				return "redirect:dashboard";
			} else {
				model.addAttribute(ServletString.COMPANY_LIST, CompanyMapperDTO.toCompanyDTOList(companyService.getAll()));
				return "addComputer";
			}

		} catch (NumberFormatException | ExceptionService e) {
			log.error(e);
			return "editComputer";
		}
	}

	@GetMapping("addcomputer")
	public String getAddComputerPage(ModelMap model, @RequestParam Map<String, String> params) {

		try {
			model.addAttribute(ServletString.COMPANY_LIST, CompanyMapperDTO.toCompanyDTOList(companyService.getAll()));
			model.addAttribute("ComputerDTO", new ComputerDTO());
		} catch (ExceptionService e) {
			log.error(e);
		}
		return "addComputer";
	}

	@PostMapping("addcomputer")
	public String addComputer(@ModelAttribute("ComputerDTO") @Validated(ComputerDTO.class) ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model, @RequestParam Map<String, String> params) {
		try {
			if (!bindingResult.hasErrors()) {
				computerService.create(ComputerMapperDTO.toComputer(computerDTO));
				return "redirect:dashboard";
			} else {
				model.addAttribute(ServletString.COMPANY_LIST, CompanyMapperDTO.toCompanyDTOList(companyService.getAll()));
				return "addComputer";
			}
		} catch (ExceptionService e) {
			log.error(e.getMessage());
			return "addComputer";
		}
	}

	private CompanyDTO getCompanyById(int id) {
		if (id != 0) {
			try {
				return CompanyMapperDTO.toCompanyDTO(companyService.getCompanyById(id));
			} catch (ExceptionService e) {
				log.error(e.getMessage());
				return null;
			}
		} else {
			return null;
		}
	}
}
