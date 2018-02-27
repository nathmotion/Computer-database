package main.java.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.excilys.cdb.dto.DtoCompany;
import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.mapper.MapperCompany;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.ServiceCompany;
import main.java.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class MyServletInterface
 */
@WebServlet("/dashboard.html")
public class dashboardServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServiceComputer serviceComputer= ServiceComputer.INSTANCE;
		ServiceCompany serviceCompany = ServiceCompany.INSTANCE;
		int count = serviceComputer.daoGetNbComputer();
		ArrayList<Computer> computers=serviceComputer.daoGetAllEntities(); 
		ArrayList<DtoComputer> listeDtoComputers=  new ArrayList<DtoComputer>();
		ArrayList<Company> companys=serviceCompany.daoGetAllEntities();
		ArrayList<DtoCompany> listeDtoCompany= new ArrayList<DtoCompany>();
		
		for(Company company:companys){
			DtoCompany dtoCompany= new DtoCompany();
			MapperCompany mapCompany= MapperCompany.INSTANCE;
			dtoCompany =mapCompany.mapToDto(company);
			listeDtoCompany.add(dtoCompany);
		}
		
		for(Computer computer:computers){
			DtoComputer dtoComputer= new DtoComputer();
			MapperComputer mapComputer= MapperComputer.INSTANCE;
			dtoComputer =mapComputer.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		
		request.setAttribute("ListeCompany",listeDtoCompany);
		request.setAttribute("ListeComputer",listeDtoComputers);
		request.setAttribute("nbComputer", count);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}



}
