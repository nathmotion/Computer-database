package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.java.excilys.cdb.dao.DaoComputer;
import main.java.excilys.cdb.dto.DtoCompany;
import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.exceptions.InvalidDateException;
import main.java.excilys.cdb.mapper.MapperCompany;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.ServiceCompany;
import main.java.excilys.cdb.service.ServiceComputer;
import main.java.excilys.cdb.validator.ValidatorComputer;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/addComputer.html")
public class AddComputerServlet extends HttpServlet {
	final static Logger LOGGER = LogManager.getLogger(DaoComputer.class);

	ArrayList<String> listError = new ArrayList<>();
	MapperCompany mapCompany = MapperCompany.INSTANCE;
	ServiceCompany serviceCompany = ServiceCompany.INSTANCE;
	ValidatorComputer validatorComputer = ValidatorComputer.INSTANCE;
	MapperComputer mapperComputer = MapperComputer.INSTANCE;
	ServiceComputer serviceComputer = ServiceComputer.INSTANCE;

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
		ArrayList<Company> companys = serviceCompany.getAllEntities();
		ArrayList<DtoCompany> listeDtoCompany = new ArrayList<>();

		for (Company company : companys) {
			DtoCompany dtoCompany = new DtoCompany();
			dtoCompany = mapCompany.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		request.setAttribute("ListeCompany", listeDtoCompany);
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
	 * ======= TESTE ET VALIDE SI LES DONNEES PASSE PAR LE FORMULAIRE SONT CORRECT =====
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
			LOGGER.error(e.getMessage());
			listError.add("nom saisie non valide !");
		}

		try {
			validatorComputer.validationDateIntro(dateIntro);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			listError.add("La date d'introducion n'est pas valide");
		}

		try {
			validatorComputer.validationDateDisc(dateIntro, dateDisc);
		} catch (InvalidDateException e) {
			LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}

	}
}
