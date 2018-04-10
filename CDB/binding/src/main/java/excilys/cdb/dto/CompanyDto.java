package main.java.excilys.cdb.dto;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyDto {
	
	
	public String id;
	
	@NotNull
	public String name;

	public CompanyDto() {

	}

	public CompanyDto(String id, String name) {
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
