package main.java.excilys.cdb.dto;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DtoCompany {
	
	
	public String id;
	
	@NonNull
	public String name;

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
