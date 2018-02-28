package main.java.excilys.cdb.servlet;

import java.io.IOException;
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
import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.ServiceComputer;
import main.java.excilys.cdb.validator.ValidatorComputer;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/addComputer.html")
public class AddComputerServlet extends HttpServlet {
	final static Logger LOGGER = LogManager.getLogger(DaoComputer.class);
	final String view = "WEB-INF/views/addComputer.jsp";

	public final String COMPUTER_NAME= "nomputerName";
	public final String DATE_INTRO= "introduced";
	public final String DATE_DISC= "discontinued";
	public final String COMPANY_ID= "companyId";

	ArrayList<String> listError = new ArrayList<>();

	ValidatorComputer validatorComputer= ValidatorComputer.INSTANCE;
	MapperComputer mapperComputer = MapperComputer.INSTANCE;
	ServiceComputer serviceComputer = ServiceComputer.INSTANCE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
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
			dtoComputer.companyName=stringName;
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
		} catch (NamingException e) {
			LOGGER.error(e.getExplanation());
			listError.add(e.getExplanation());
		}

		validatorComputer.validationDateIntro(dateIntro);
		validatorComputer.validationDateDisc(dateDisc);
		try {
			validatorComputer.validationCompany_id(companyId);
		}catch(IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			listError.add(e.getMessage());
		}
	}
}
