package main.java.excilys.cdb.dto;

import org.springframework.lang.NonNull;


import main.java.excilys.cdb.model.Company;

public class DtoComputer {
	public String id;
	@NonNull
	public String name;
	public String date_introduced;
	public String date_discontinued;
	public String companyId;
	public String companyName;

	public DtoComputer() {

	}

	public DtoComputer(String id, String name, String date_introduced, String date_discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.companyId = String.valueOf(company.getId());
		this.companyName = company.getName();
	}
	public DtoComputer(String name, String date_introduced, String date_discontinued, String companyId,String companyName) {
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDate_introduced() {
		return date_introduced;
	}

	public String getDate_discontinued() {
		return date_discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}
}
