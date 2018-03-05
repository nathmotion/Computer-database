package main.java.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.excilys.cdb.dao.Page;
import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class MyServletInterface
 */
@WebServlet("/dashboard.html")
public class DashboardServlet extends HttpServlet {
	MapperComputer mapComputer = MapperComputer.INSTANCE;
	ServiceComputer serviceComputer = ServiceComputer.INSTANCE;
	int offset = 0;
	int limitPage = 10;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request = affichagePage(request);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> selection = new ArrayList<String>(Arrays.asList(request.getParameter("selection").split(",")));
		gestionDelete(selection);
		request = affichagePage(request);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	public void gestionDelete(ArrayList<String> selection) {
		for (String idString : selection) {
			Long idComputer = Long.parseLong(idString);
			Computer computer = new Computer();
			computer.setId(idComputer);
			serviceComputer.daoDelete(computer);
		}
	}

	// page to go index => (indexPage - 1) * limit
	public HttpServletRequest affichagePage(HttpServletRequest request) {
		int count = serviceComputer.daoGetNbComputer();
		String selectionPage = request.getParameter("page");
		if (selectionPage == null) {
			selectionPage = "0";
		}
		switch (selectionPage) {
		case "next":
			if (offset + limitPage < count) {
				offset = offset + limitPage;
			}
			break;

		case "previous":
			if (offset + limitPage > limitPage) {
				offset = offset - limitPage;
			}
			break;
		default:
			offset = 0;
			limitPage = 10;
		}

		Page<Computer> page = serviceComputer.daoGetPage(offset, limitPage);
		ArrayList<DtoComputer> listeDtoComputers = new ArrayList<>();

		for (Computer computer : page.getPage()) {
			DtoComputer dtoComputer = new DtoComputer();
			dtoComputer = mapComputer.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		request.setAttribute("ListeComputer", listeDtoComputers);
		request.setAttribute("nbComputer", count);
		return request;
	}

}
