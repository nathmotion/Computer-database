package main.java.excilys.cdb.dto;

public class DtoComputer {
	public String id;
	public String name ;
	public String date_introduced;
	public String date_discontinued;
	public String company_id;

	public DtoComputer() {
		
	}
	
	public DtoComputer(String id, String name, String date_introduced, String date_discontinued, String company_id) {
		this.id = id;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.company_id = company_id;
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

	public String getCompany_id() {
		return company_id;
	}

}

