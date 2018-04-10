package main.java.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.repositories.CompanyRepositoryImpl;

@Service
public class CompanyServiceImpl {

	private CompanyRepositoryImpl companyRepository;
	
	public CompanyServiceImpl(CompanyRepositoryImpl companyRepositoryImpl) {
		this.companyRepository =companyRepositoryImpl;
	}

	public List<Company> getAll() {
		return (List<Company>) companyRepository.findAll();
	}

	@Transactional 
	public void delete(Company company) {
		companyRepository.delete(company);
	}

}
