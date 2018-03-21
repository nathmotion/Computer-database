package main.java.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.excilys.cdb.dao.InterfaceDao;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Page;

@Service
public class CompanyServiceImpl implements ServiceCDB<Company> {

	private InterfaceDao<Company> daoCompany;

	public CompanyServiceImpl(InterfaceDao<Company> daoCompany) {
		this.daoCompany=daoCompany;
	}

	public List<Company> getAllEntities() {
		return daoCompany.getAll();
	}

	public void delete(Company company) {
		daoCompany.delete(company);
	}

	@Override
	public Page<Company> getPage(Page<Company> page) {
		return null;
	}

	@Override
	public Page<Company> getPageByName(Page<Company> page, String name) {
		return null;
	}

	@Override
	public int getNbSearch(String name) {
		return 0;
	}

	@Override
	public Page<Company> getPageByOrder(Page<Company> page, String critere, Boolean order) {
		return null;
	}

	@Override
	public int getNbTotal() {
		return 0;
	}

	@Override
	public void create(Company obj) {
	}

	@Override
	public void update(Company obj) {

	}

	@Override
	public Optional<Company> findById(int id) {
		return null;
	}

}
