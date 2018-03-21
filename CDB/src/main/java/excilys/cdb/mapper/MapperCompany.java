package main.java.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import main.java.excilys.cdb.dto.CompanyDto;
import main.java.excilys.cdb.model.Company;

@Component
public class MapperCompany {
	

	public CompanyDto mapToDto(Company company) {
		CompanyDto dtoCompany = new CompanyDto();
		dtoCompany.name = company.getName();
		dtoCompany.id = String.valueOf(company.getId());
		return dtoCompany;
	}

	public Company mapToEntity(CompanyDto dtoCompany) {
		Company company = new Company(Long.valueOf(dtoCompany.id), dtoCompany.name);
		return company;
	}

}
