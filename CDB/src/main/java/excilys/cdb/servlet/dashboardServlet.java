package main.java.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class MyServletInterface
 */
@WebServlet("/dashboard.html")
public class dashboardServlet extends HttpServlet {
	MapperComputer mapComputer= MapperComputer.INSTANCE;
	ServiceComputer serviceComputer= ServiceComputer.INSTANCE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int count = serviceComputer.daoGetNbComputer();
		ArrayList<Computer> computers=serviceComputer.daoGetAllEntities(); 
		ArrayList<DtoComputer> listeDtoComputers=  new ArrayList<>();

		for(Computer computer:computers){
			DtoComputer dtoComputer= new DtoComputer();
			dtoComputer =mapComputer.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		request.setAttribute("ListeComputer",listeDtoComputers);
		request.setAttribute("nbComputer",count);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> selection =new ArrayList<String>(Arrays.asList(request.getParameter("selection").split(",")));
		for(String idString :selection) {
			Long idComputer = Long.valueOf(idString);
			Computer computer= new Computer();
			computer.setId(idComputer);
			serviceComputer.daoDelete(computer);
		}
	}
	// page to go index => (indexPage - 1) * limit
}
