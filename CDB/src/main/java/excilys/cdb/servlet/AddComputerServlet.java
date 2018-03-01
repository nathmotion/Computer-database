package main.java.excilys.cdb.servlet;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import javax.naming.NamingException;
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
	final String view = "/WEB-INF/views/addComputer.jsp";
	public final String COMPUTER_NAME= "computerName";
	public final String DATE_INTRO= "introduced";
	public final String DATE_DISC= "discontinued";
	public final String COMPANY_ID= "companyId";
	
	ArrayList<String> listError = new ArrayList<>();
	MapperCompany mapCompany= MapperCompany.INSTANCE;
	ServiceCompany serviceCompany = ServiceCompany.INSTANCE;
	ValidatorComputer validatorComputer= ValidatorComputer.INSTANCE;
	MapperComputer mapperComputer = MapperComputer.INSTANCE;
	ServiceComputer serviceComputer = ServiceComputer.INSTANCE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		ArrayList<Company> companys=serviceCompany.daoGetAllEntities();
		ArrayList<DtoCompany> listeDtoCompany= new ArrayList<>();

		for(Company company:companys){
			DtoCompany dtoCompany= new DtoCompany();
			dtoCompany =mapCompany.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		request.setAttribute("ListeCompany",listeDtoCompany);
		request.setAttribute("errors", listError);
		this.getServletContext().getRequestDispatcher(view).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String stringName= request.getParameter(COMPUTER_NAME);
		String stringDateIntro = request.getParameter(DATE_INTRO);
		String stringDateDisc= request.getParameter(DATE_DISC);
		String stringCompanyId = request.getParameter(COMPANY_ID);
		validations(stringName,stringDateIntro,stringDateDisc,stringCompanyId);

		if(listError.size()<=0) {
			DtoComputer dtoComputer= new DtoComputer();
			dtoComputer.name=stringName;
			dtoComputer.date_introduced=stringDateIntro;
			dtoComputer.date_discontinued=stringDateDisc;
			dtoComputer.companyId=stringCompanyId;

			Computer computer =mapperComputer.mapToEntity(dtoComputer);
			serviceComputer.daoCreate(computer);
		}
		doGet(request,response);
	}

	public void validations(String name, String dateIntro,String dateDisc, String companyId) {
		try {
			validatorComputer.validationName(name);
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}

		try {
			validatorComputer.validationDateIntro(dateIntro);
		}catch(IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}

		try {
			validatorComputer.validationDateDisc(dateDisc);
		}catch(DateTimeException e) {
			LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}

		try {
			validatorComputer.validationCompany_id(companyId);
		}catch(DateTimeException e) {
			LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}
	}
}
