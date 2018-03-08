package main.java.excilys.cdb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;
import main.java.excilys.cdb.service.ServiceComputer;
import main.java.excilys.cdb.taglib.PageTag;

/**
 * Servlet implementation class MyServletInterface
 */
@WebServlet("/dashboard.html")
public class DashboardServlet extends HttpServlet {
	MapperComputer mapComputer = MapperComputer.INSTANCE;
	ServiceComputer serviceComputer = ServiceComputer.INSTANCE;
	PageTag paginationTag = new PageTag();
	Page<Computer> page = new Page<Computer>(0, 0, 10);
	String searchName;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request = affichagePage(request);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> selection = new ArrayList<String>(
				Arrays.asList(request.getParameter("selection").split(",")));
		gestionDelete(selection);
		searchName = null;
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

	public HttpServletRequest affichagePage(HttpServletRequest request) {
		String action = request.getParameter("actionpage");
		String stringLimite = request.getParameter("limit");
		String searchExpression = request.getParameter("searchName");
		String searchFlag = request.getParameter("search");
		int count = serviceComputer.daoGetNbComputer();
		if (searchFlag != null) {
			if (Boolean.parseBoolean(searchFlag) == false) {
				searchName = null;
			}
		}
		if (action == null) {
			action = "";
		}
		if (stringLimite != null) {
			page.limit = Integer.parseInt(stringLimite);
			page.offset = 0;
			page.current = (page.getOffset() / page.getLimit()) + 1;
		}
		if (searchExpression != null) {
			searchName = searchExpression;
		}

		if (searchName == null) {
			page = serviceComputer.daoGetPage(page);
		} else {
			count = serviceComputer.daoGetNbComputerSearch(searchName);
			page = serviceComputer.daoGetPageByName(page, searchName);
		}
		actionPage(action, count);
		ArrayList<DtoComputer> listeDtoComputers = new ArrayList<>();
		for (Computer computer : page.elementsPage) {
			DtoComputer dtoComputer = new DtoComputer();
			dtoComputer = mapComputer.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		request.setAttribute("page", page);
		request.setAttribute("ListeComputer", listeDtoComputers);
		request.setAttribute("nbComputer", count);
		return request;
	}

	public void actionPage(String action, int count) {
		switch (action) {
		case "next":
			if (page.current <= Math.ceil((count / page.limit))) {
				page.offset = page.offset + page.limit;
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			break;

		case "previous":
			if (page.current - 1 > 0) {
				page.offset = page.offset - page.limit;
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			break;
		}
	}
}
