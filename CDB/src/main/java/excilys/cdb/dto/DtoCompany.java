package main.java.excilys.cdb.dto;

public class DtoCompany {
	public String id;
	public String name ;

	public DtoCompany() {
		
	}
	
	public DtoCompany(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
