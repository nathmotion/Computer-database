package main.java.excilys.cdb.controllers;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Optional;

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

@WebServlet("/editComputer.html")
public class EditComputerServlet extends HttpServlet {
	final static Logger LOGGER = LogManager.getLogger(DaoComputer.class);
	ArrayList<String> listError = new ArrayList();
	final ServiceComputer serviceComputer = ServiceComputer.INSTANCE;
	final ServiceCompany serviceCompany = ServiceCompany.INSTANCE;
	private int idComputer;
	final MapperCompany mapCompany = MapperCompany.INSTANCE;
	final MapperComputer mapComputer = MapperComputer.INSTANCE;
	ValidatorComputer validatorComputer = ValidatorComputer.INSTANCE;

	public final String COMPUTER_NAME = "computerName";
	public final String DATE_INTRO = "introduced";
	public final String DATE_DISC = "discontinued";
	public final String COMPANY_ID = "companyId";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selection = request.getParameter("id");
		idComputer=Integer.parseInt(selection);
		Optional<Computer> optComputer = serviceComputer.daoFindById(Integer.parseInt(selection));
		request = affichagePage(request);
		if (optComputer.isPresent()) {
			DtoComputer dtoComputer = mapComputer.mapToDto(optComputer.get());
			System.out.println("date "+ dtoComputer.date_introduced);
			request.setAttribute("computer", dtoComputer);
		} else {
			LOGGER.error("  L'item computer" + selection + " n'est pas trouve");
			request.setAttribute("error", "Computer not found");
			request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		editRequest(request);
		request.setAttribute("errors", listError);
		Optional<Computer> optComputer = serviceComputer.daoFindById(idComputer);
		if (optComputer.isPresent()) {
			DtoComputer dtoComputer = mapComputer.mapToDto(optComputer.get());
			request.setAttribute("computer", dtoComputer);
		} else {
			LOGGER.error("  L'item computer" + idComputer	 + " n'est pas trouve");
			request.setAttribute("error", "Computer not found");
			request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public void editRequest(HttpServletRequest request) {
		String stringId = request.getParameter("computerId");
		String stringName = request.getParameter(COMPUTER_NAME);
		String stringDateIntro = request.getParameter(DATE_INTRO);
		String stringDateDisc = request.getParameter(DATE_DISC);
		String stringCompanyId = request.getParameter(COMPANY_ID);

		validations(stringName, stringDateIntro, stringDateDisc, stringCompanyId);
		if (listError.size() <= 0) {
			Company company = new Company(Long.valueOf(stringCompanyId), "");
			DtoComputer dtoComputer = new DtoComputer(stringId, stringName, stringDateIntro, stringDateDisc, company);
			Computer computer = mapComputer.mapToEntity(dtoComputer);
			System.out.println("date "+ computer.getIntroduced());
			serviceComputer.daoUpdate(computer);
			request = affichagePage(request);
		}
		listError.clear();
	}

	public HttpServletRequest affichagePage(HttpServletRequest request) {
		int count = serviceComputer.daoGetNbComputer();

		ArrayList<Computer> computers = serviceComputer.daoGetAllEntities();
		ArrayList<DtoComputer> listeDtoComputers = new ArrayList<>();

		ArrayList<Company> companys = serviceCompany.daoGetAllEntities();
		ArrayList<DtoCompany> listeDtoCompany = new ArrayList<>();

		for (Computer computer : computers) {
			DtoComputer dtoComputer = new DtoComputer();
			dtoComputer = mapComputer.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		for (Company company : companys) {
			DtoCompany dtoCompany = new DtoCompany();
			dtoCompany = mapCompany.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		request.setAttribute("ListeCompany", listeDtoCompany);
		request.setAttribute("ListeComputer", listeDtoComputers);
		request.setAttribute("nbComputer", count);
		return request;
	}

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
			validatorComputer.validationDateDisc(dateIntro,dateDisc);
		} catch (InvalidDateException e) {
			LOGGER.error(e.getMessage());
			listError.add("la date de retrait n'ont valide ! ");
		}

	}
}
