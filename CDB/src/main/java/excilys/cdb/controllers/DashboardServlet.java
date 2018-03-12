package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.*;
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
	int computerOrderFlag;
	int companyOrderFlag;

	/**
	 * ===== DO GET =====
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request = affichagePage(request);
		this.getServletContext().getRequestDispatcher(VIEW_BOARD).forward(request, response);
	}

	/**
	 * ===== DO POST =====
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> selection = new ArrayList<String>(Arrays.asList(request.getParameter(SELECTION).split(",")));
		gestionDelete(selection);
		searchName = null;
		request = affichagePage(request);
		this.getServletContext().getRequestDispatcher(VIEW_BOARD).forward(request, response);
	}

	/**
	 * ===== GESTION DE LA REQUETE DE SUPPRESSION DES COMPUTER =======
	 * 
	 * @param selection
	 */
	public void gestionDelete(ArrayList<String> selection) {
		for (String idString : selection) {
			Long idComputer = Long.parseLong(idString);
			Computer computer = new Computer();
			computer.setId(idComputer);
			serviceComputer.daoDelete(computer);
		}
	}

	/**
	 * ======== GESTION D'AFFICHAGE DES PAGE DE COMPUTER ET LA RECHERCHE ======
	 * 
	 * @param request
	 * @return
	 */
	public HttpServletRequest affichagePage(HttpServletRequest request) {
		String action = request.getParameter(ACTION_PAGE);
		String stringLimite = request.getParameter(LIMIT);
		String searchExpression = request.getParameter(SEARCH_NAME);
		String searchFlag = request.getParameter(FLAG_SEARCH);
		String order = request.getParameter(ORDER);
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
		if (order != null) {
			if (order.equals("computer")) {
				computerOrderFlag++;
				if (computerOrderFlag % 2 == 0) {
					page.offset = 0;
					page=serviceComputer.daoGetPageByOrder(page,"computer","ASC");
					page.current = (page.getOffset() / page.getLimit()) + 1;
				}
				if (computerOrderFlag % 2 == 1) {
					page.offset = 0;
					page=serviceComputer.daoGetPageByOrder(page,"computer","DESC");
					page.current = (page.getOffset() / page.getLimit()) + 1;
				}
				
			}
			if (order.equals("company")) {
				companyOrderFlag++;
				if (companyOrderFlag % 2 == 0) {
					page.limit = Integer.parseInt(stringLimite);
					page.offset = 0;
					page=serviceComputer.daoGetPageByOrder(page,"company","ASC");
					page.current = (page.getOffset() / page.getLimit()) + 1;
				}
				if (companyOrderFlag % 2 == 1) {
					page.limit = Integer.parseInt(stringLimite);
					page.offset = 0;
					page=serviceComputer.daoGetPageByOrder(page,"company","DESC");
					page.current = (page.getOffset() / page.getLimit()) + 1;
				}
				
			}
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

	/**
	 * ===== GESTION DES ACTIONS DEMANDE POUR AFFICHER LA PAGE DE COMPUTER ====
	 * 
	 * @param action
	 * @param count
	 */
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
