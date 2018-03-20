package main.java.excilys.cdb.controllers;

import static main.java.excilys.cdb.constantes.ConstantesControllers.ACTION_PAGE;
import static main.java.excilys.cdb.constantes.ConstantesControllers.LIMIT;
import static main.java.excilys.cdb.constantes.ConstantesControllers.ORDER_CMP;
import static main.java.excilys.cdb.constantes.ConstantesControllers.SEARCH;
import static main.java.excilys.cdb.constantes.ConstantesControllers.TYPE_ORDER;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.mapper.MapperComputer;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;
import main.java.excilys.cdb.service.ComputerServiceImpl;

@Controller
public class DashboardControllers {

	@Autowired
	private ComputerServiceImpl computerService;

	@Autowired
	private MapperComputer mapComputer;

	Page<Computer> page = new Page<Computer>(0, 0, 10);

	@GetMapping("/dashboard.html")
	public String doGet(@RequestParam(value = ACTION_PAGE, required = false) String action,
			@RequestParam(value = LIMIT, required = false) String limit,
			@RequestParam(value = SEARCH, required = false) String search,
			@RequestParam(value = ORDER_CMP, required = false) String orderCmp,
			@RequestParam(value = TYPE_ORDER, required = false) String typeOrder, Model model) {

		model = affichagePage(action, limit, search, typeOrder, orderCmp, model);
		return "dashboard";
	}

	/***
	 * ====== POST : SUBMIT ======
	 */
	@PostMapping("/dashboard.html")
	public String submit(@RequestParam(value = ACTION_PAGE, required = false) String action,
			@RequestParam(value = LIMIT, required = false) String limit,
			@RequestParam(value = SEARCH, required = false) String search,
			@RequestParam(value = ORDER_CMP, required = false) String orderCmp,
			@RequestParam(value = TYPE_ORDER, required = false) String typeOrder, Model model) {
		search = null;
		orderCmp = null;
		typeOrder = null;
		model = affichagePage(action, limit, search, typeOrder, orderCmp, model);
		return "dashboard";
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
			computerService.delete(computer);
		}
	}

	/**
	 * ======== GESTION D'AFFICHAGE DES PAGE DE COMPUTER ET LA RECHERCHE ======
	 * 
	 * @param request
	 * @return
	 */
	public Model affichagePage(String actionPage, String limit, String search, String typeOrder, String orderCmp,
			Model model) {

		if (search != null) {
			page.setSearch(search);
		}
		int count = computerService.getNbTotal();

		if (typeOrder != null) {
			page.setTypeOrder(typeOrder);
		}
		if (orderCmp != null) {
			page.setOrderCmp(Integer.parseInt(orderCmp));

		}
		if (actionPage == null) {
			actionPage = "";
		}
		if (limit != null) {
			page.limit = Integer.parseInt(limit);
			page.offset = 0;
			page.current = (page.getOffset() / page.getLimit()) + 1;
		}

		actionPage(actionPage, count);

		if (page.getSearch() != null && !page.getSearch().equals("null")) {
			count = computerService.getNbSearch(page.getSearch());
			page = computerService.getPageByName(page, page.getSearch());
		} else {
			handlePageOrder(computerService);
		}

		ArrayList<DtoComputer> listeDtoComputers = new ArrayList<>();
		for (Computer computer : page.elementsPage) {
			DtoComputer dtoComputer = new DtoComputer();
			dtoComputer = mapComputer.mapToDto(computer);
			listeDtoComputers.add(dtoComputer);
		}
		model.addAttribute("page", page);
		model.addAttribute("ListeComputer", listeDtoComputers);
		model.addAttribute("nbComputer", count);
		return model;
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

	public void handlePageOrder(ComputerServiceImpl computerService) {
		if (page.getTypeOrder() != null && !page.getTypeOrder().equals("null")) {
			page.setOrderCmp(page.getOrderCmp() + 1);
			if (page.getOrderCmp() % 2 == 0) {
				page.offset = 0;
				page = computerService.getPageByOrder(page, page.getTypeOrder(), true);
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			if (page.getOrderCmp() % 2 == 1) {
				page.offset = 0;
				page = computerService.getPageByOrder(page, page.getTypeOrder(), false);
				page.current = (page.getOffset() / page.getLimit()) + 1;
			}
			page.setTypeOrder(null);
		} else {
			page = computerService.getPage(page);
		}
	}

}
