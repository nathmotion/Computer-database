package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.exceptions.InvalidDateException;
import main.java.excilys.cdb.mapper.MapperCompany;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.CompanyServiceImpl;
import main.java.excilys.cdb.service.ComputerServiceImpl;
import main.java.excilys.cdb.validator.ValidatorComputer;

@WebServlet("/editComputer.html")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	final static Logger LOGGER = LogManager.getLogger(EditComputerServlet.class);

	@Autowired
	private ValidatorComputer validatorComputer;
	@Autowired
	private ComputerServiceImpl serviceComputer;
	@Autowired
	private MapperComputer mapComputer;

	ArrayList<String> listError = new ArrayList<String>();
	private int idComputer;

	/**
	 * === DO GET ====
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selection = request.getParameter(ID);
		idComputer = Integer.parseInt(selection);
		Optional<Computer> optComputer = serviceComputer.findById(Integer.parseInt(selection));
		if (optComputer.isPresent()) {
			DtoComputer dtoComputer = mapComputer.mapToDto(optComputer.get());
			System.out.println("date " + dtoComputer.date_introduced);
			request.setAttribute("computer", dtoComputer);
		} else {
			LOGGER.error("  L'item computer" + selection + " n'est pas trouve");
			request.setAttribute("error", "Computer not found");
			request.getRequestDispatcher(VIEW_BOARD).forward(request, response);
		}
		request.getRequestDispatcher(VIEW_EDIT_COMPUTER).forward(request, response);
	}

	/***
	 * === DO POST ====
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		editRequest(request);
		request.setAttribute("errors", listError);
		Optional<Computer> optComputer = serviceComputer.findById(idComputer);
		if (optComputer.isPresent()) {
			DtoComputer dtoComputer = mapComputer.mapToDto(optComputer.get());
			request.setAttribute("computer", dtoComputer);
		} else {
			LOGGER.error("  L'item computer" + idComputer + " n'est pas trouve");
			request.setAttribute("error", "Computer not found");
			request.getRequestDispatcher(VIEW_BOARD).forward(request, response);
		}
		this.getServletContext().getRequestDispatcher(VIEW_EDIT_COMPUTER).forward(request, response);
	}

	/**
	 * ======= GESTION DE LA REQUETE DE EDITION D'UN COMPUTER =======
	 * 
	 * @param request
	 */
	public void editRequest(HttpServletRequest request) {
		String stringId = request.getParameter(COMPANY_ID);
		String stringName = request.getParameter(COMPUTER_NAME);
		String stringDateIntro = request.getParameter(DATE_INTRO);
		String stringDateDisc = request.getParameter(DATE_DISC);
		String stringCompanyId = request.getParameter(COMPANY_ID);

		validations(stringName, stringDateIntro, stringDateDisc, stringCompanyId);
		if (listError.size() <= 0) {
			Company company = new Company(Long.valueOf(stringCompanyId), "");
			DtoComputer dtoComputer = new DtoComputer(stringId, stringName, stringDateIntro, stringDateDisc, company);
			Computer computer = mapComputer.mapToEntity(dtoComputer);
			serviceComputer.update(computer);
		}
		listError.clear();
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
			LOGGER.error("nom saisie non valide !");
			listError.add("nom saisie non valide !");
		}

		try {
			validatorComputer.validationDateIntro(dateIntro);
		} catch (IllegalArgumentException e) {
			LOGGER.error("La date d'introducion n'est pas valide");
			listError.add("La date d'introducion n'est pas valide");
		}

		try {
			validatorComputer.validationDateDisc(dateIntro, dateDisc);
		} catch (InvalidDateException e) {
			LOGGER.error(e.getMessage());
			listError.add("la date de retrait n'ont valide ! ");
		}

	}
}
