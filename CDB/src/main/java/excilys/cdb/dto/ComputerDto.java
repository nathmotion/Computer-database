package main.java.excilys.cdb.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import main.java.excilys.cdb.model.Company;

@Component
public class ComputerDto {

	private String id;
	
	@NotBlank(message = "Name can't be empty")
	@Pattern(regexp = "[!@#$%^&*()<>]", message="name not valid expression")
	@Valid
	private String name;
	private String date_introduced;
	private String date_discontinued;
	private String companyId;
	private String companyName;

	public ComputerDto() {

	}
	public ComputerDto(String id, String name, String date_introduced, String date_discontinued) {
		this.id = id;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.companyId = null;
		this.companyName = null;
	}


	public ComputerDto(String id, String name, String date_introduced, String date_discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.companyId = String.valueOf(company.getId());
		this.companyName = company.getName();
	}

	public ComputerDto(String name, String date_introduced, String date_discontinued, String companyId,
			String companyName) {
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

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate_introduced(String date_introduced) {
		this.date_introduced = date_introduced;
	}

	public void setDate_discontinued(String date_discontinued) {
		this.date_discontinued = date_discontinued;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "ComputerDto [id=" + id + ", name=" + name + ", date_introduced=" + date_introduced
				+ ", date_discontinued=" + date_discontinued + ", companyId=" + companyId + ", companyName="
				+ companyName + "]";
	}
}
