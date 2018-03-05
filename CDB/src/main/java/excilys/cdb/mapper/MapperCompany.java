package main.java.excilys.cdb.mapper;

import main.java.excilys.cdb.dto.DtoCompany;
import main.java.excilys.cdb.model.Company;

public enum MapperCompany {
	INSTANCE;

	public DtoCompany mapToDto(Company company) {
		DtoCompany dtoCompany = new DtoCompany();
		dtoCompany.name = company.getName();
		dtoCompany.id = String.valueOf(company.getId());
		return dtoCompany;
	}

	public Company mapToEntity(DtoCompany dtoCompany) {
		Company company = new Company(Long.valueOf(dtoCompany.id), dtoCompany.name);
		return company;
	}

}
