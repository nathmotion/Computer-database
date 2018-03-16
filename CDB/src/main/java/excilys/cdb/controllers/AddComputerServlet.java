package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPANY_ID;
import static main.java.excilys.cdb.constantes.ConstantesControllers.COMPUTER_NAME;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_DISC;
import static main.java.excilys.cdb.constantes.ConstantesControllers.DATE_INTRO;
import static main.java.excilys.cdb.constantes.ConstantesControllers.VIEW_ADD_COMPUTER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/addComputer.html")
@Controller
public class AddComputerServlet extends HttpServlet {

	//final static Logger LOGGER = LogManager.getLogger(AddComputerServlet.class);

	ArrayList<String> listError = new ArrayList<>();
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request = affichageCompany(request);
		this.getServletContext().getRequestDispatcher(VIEW_ADD_COMPUTER).forward(request, response);
	}

	/**
	 * ===== DO POST =====
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String stringName = request.getParameter(COMPUTER_NAME);
		String stringDateIntro = request.getParameter(DATE_INTRO);
		String stringDateDisc = request.getParameter(DATE_DISC);
		String stringCompanyId = request.getParameter(COMPANY_ID);

		validations(stringName, stringDateIntro, stringDateDisc, stringCompanyId);
		gestionCreation(stringName, stringDateIntro, stringDateDisc, stringCompanyId);
		request.setAttribute("errors", listError);
		request = affichageCompany(request);
		this.getServletContext().getRequestDispatcher(VIEW_ADD_COMPUTER).forward(request, response);
	}

	/**
	 * ======= GESTION DE LA RECUPERATION DE LA LISTE DE COMPANY =========
	 * 
	 * @param request
	 * @return
	 */
	public HttpServletRequest affichageCompany(HttpServletRequest request) {
		List<Company> companys = serviceCompany.getAllEntities();
		List<DtoCompany> listeDtoCompany = new ArrayList<>();

		for (Company company : companys) {
			DtoCompany dtoCompany = new DtoCompany();
			dtoCompany = mapCompany.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		request.setAttribute("ListeCompany", listeDtoCompany);
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
	public void gestionCreation(String stringName, String stringDateIntro, String stringDateDisc,
			String stringCompanyId) {
		if (listError.size() <= 0) {
			DtoComputer dtoComputer = new DtoComputer();
			dtoComputer.name = stringName;
			dtoComputer.date_introduced = stringDateIntro;
			dtoComputer.date_discontinued = stringDateDisc;
			dtoComputer.companyId = stringCompanyId;
			listError.clear();
			Computer computer = mapperComputer.mapToEntity(dtoComputer);
			serviceComputer.create(computer);
		}

	}

	/**
	 * ======= TESTE ET VALIDE SI LES DONNEES PASSE PAR LE FORMULAIRE SONT CORRECT
	 * =====
	 * 
	 * @param name
	 * @param dateIntro
	 * @param dateDisc
	 * @param companyId
	 */
	public void validations(String name, String dateIntro, String dateDisc, String companyId) {
		try {
			validatorComputer.validationName(name);
		} catch (NullPointerException e) {
			//LOGGER.error(e.getMessage());
			listError.add("nom saisie non valide !");
		}

		try {
			validatorComputer.validationDateIntro(dateIntro);
		} catch (IllegalArgumentException e) {
			//LOGGER.error(e.getMessage());
			listError.add("La date d'introducion n'est pas valide");
		}

		try {
			validatorComputer.validationDateDisc(dateIntro, dateDisc);
		} catch (InvalidDateException e) {
		//	LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}

	}

	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);	
	}
}
