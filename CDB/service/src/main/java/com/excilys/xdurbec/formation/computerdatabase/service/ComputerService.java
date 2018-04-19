package com.excilys.xdurbec.formation.computerdatabase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.xdurbec.formation.computerdatabase.dao.ComputerDAO;
import com.excilys.xdurbec.formation.computerdatabase.dao.ExceptionDAO;
import com.excilys.xdurbec.formation.computerdatabase.model.Computer;
import com.excilys.xdurbec.formation.computerdatabase.model.ComputerPage;


@Service
public class ComputerService extends EntityService implements EntityServiceComportment<Computer> {


	private ComputerDAO computerDAO;
	private CompanyService companyService;

	public ComputerService(ComputerDAO computerDAO, CompanyService companyService) {
		this.computerDAO = computerDAO;
		this.companyService = companyService;
	}

	public Computer getById(int id) throws ExceptionService {
		try {
			return computerDAO.getById(id);
		} catch (ExceptionDAO e) {
			throw new ExceptionService(ExceptionService.ID_COMPUTER_ERROR);
		}
	}

	@Override
	public List<Computer> getAll() throws ExceptionService {
		try {
			return computerDAO.getAll();
		} catch (ExceptionDAO e) {
			log.error(e);
			throw new ExceptionService(ExceptionService.GET_ALL_ERROR);
		}
	}

	@Transactional
	public void create(Computer entity) throws  ExceptionService {
		if (entity.getCompany() == null || entity.getCompany().getId() == 0 || companyService.companyExistenceVerification(entity.getCompany().getId())) {
			try {
				computerDAO.create(entity);
			} catch (ExceptionDAO e) {
				throw new ExceptionService(ExceptionService.CREATE_ERROR);
			}
		} else {
			throw new ExceptionService(ExceptionService.DOES_EXIST_ERROR);
		}
	}


	@Transactional
	public void update(Computer entity) throws  ExceptionService {
		if (entity.getCompany() == null || entity.getCompany().getId() == 0 || companyService.companyExistenceVerification(entity.getCompany().getId())) {
			try {
				computerDAO.update(entity);
			} catch (ExceptionDAO e) {
				throw new ExceptionService(ExceptionService.UPDATE_ERROR);
			}
		} else {
			throw new ExceptionService(ExceptionService.DOES_EXIST_ERROR);
		}
	}

	@Transactional
	public void deleteById(int id) throws  ExceptionService {
		try {
			computerDAO.deleteById(id);
		} catch (ExceptionDAO e) {
			throw new ExceptionService(ExceptionService.DELETE_ERROR);
		}
	}

	public ComputerPage getComputerPage(int pageNumber, int nbComputerByPage, String filter, com.excilys.xdurbec.formation.computerdatabase.model.ComputerAttributes orderBy, Boolean ascendingOrder) throws ExceptionService {
		return new ComputerPage(pageNumber, nbComputerByPage, filter, orderBy, ascendingOrder);
	}

	public int getComputerNumber(String filter) throws ExceptionService {
		if (filter == null) {
			filter = "";
		}
		return computerDAO.getComputerNumber(filter);
	}


	public static Boolean computerDateValidator(Computer computer) {
		return computer.getIntroduced() == null || computer.getDiscontinued() == null 
				|| computer.getIntroduced().before(computer.getDiscontinued());
	}


	public ComputerPage refresh(ComputerPage computerPage) throws ExceptionService {
		ComputerPage newComputerPage = new ComputerPage(computerPage.getPageNumber(), computerPage.getNbComputerPerPage(), computerPage.getFilter(), computerPage.getOrderBy(), computerPage.getAscendingOrder());
		newComputerPage.setComputerList(computerDAO.getAllPage(newComputerPage.getPageNumber(), newComputerPage.getNbComputerPerPage(), 
				newComputerPage.getFilter(), newComputerPage.getOrderBy(), newComputerPage.getAscendingOrder()));
		return newComputerPage;
	}
}
