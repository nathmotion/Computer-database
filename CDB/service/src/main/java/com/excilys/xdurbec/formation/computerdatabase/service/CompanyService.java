package com.excilys.xdurbec.formation.computerdatabase.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.xdurbec.formation.computerdatabase.dao.CompanyDAO;
import com.excilys.xdurbec.formation.computerdatabase.dao.ComputerDAO;
import com.excilys.xdurbec.formation.computerdatabase.dao.ExceptionDAO;
import com.excilys.xdurbec.formation.computerdatabase.model.Company;

@Service
public class CompanyService extends EntityService implements EntityServiceComportment<Company> {

	public CompanyService(CompanyDAO companyDAO, ComputerDAO computerDAO) {
		this.companyDAO = companyDAO;

	}

	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;

	@Override
	public List<Company> getAll() throws ExceptionService {
		try {
			return companyDAO.getAll();
		} catch (ExceptionDAO e) {
			throw new ExceptionService(ExceptionService.GET_ALL_ERROR);
		}
	}

	public Boolean companyExistenceVerification(int id) {
		return companyDAO.doesExist(id);
	}

	public  Company getCompanyById(int id) throws ExceptionService {
		if (id == 0) {
			return null;
		} else {
			try {
				return companyDAO.getById(id);
			} catch (ExceptionDAO e) {
				log.error(e.getMessage());
				throw new ExceptionService(ExceptionService.GET_COMPANY_BY_ID);
			}
		}
	}

	@Transactional
	public void deleteCompany(int id) {
		try {
			computerDAO.deleteByCompany(id);
			companyDAO.delete(id);		
		} catch (ExceptionDAO e) {
			log.error(e);
		}
	}
}
