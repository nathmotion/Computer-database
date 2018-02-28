package main.java.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.util.Optional;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

public enum MapperComputer {
	INSTANCE;

	public Computer mapToEntity(DtoComputer dtoComputer) {
		Company company = new Company();
		company.setId(Long.valueOf(dtoComputer.companyId));
		company.setName(dtoComputer.companyName);
		System.out.println(dtoComputer);
		Computer computer = new Computer();
		if( (dtoComputer.date_introduced!=null || dtoComputer.date_discontinued!=null) ) {
			computer.setDiscontinued(null);
			computer.setIntroduced(null);
		}
		else {
			computer.setDiscontinued(Timestamp.valueOf(dtoComputer.date_discontinued));
			computer.setIntroduced(Timestamp.valueOf(dtoComputer.date_introduced));
		}
		if(dtoComputer.id!=null) {
			computer.setId(Long.valueOf(dtoComputer.id));
		}else {
			computer.setId((long)0);
		}
		System.out.println("    name "+dtoComputer.name);
		computer.setName(dtoComputer.name);
		computer.setCompany(company);
			return computer;
	}

	public DtoComputer mapToDto(Computer computer) {
		DtoComputer dtoComputer = new DtoComputer();
		dtoComputer.name=computer.getName();
		dtoComputer.id=String.valueOf(computer.getId());
		Optional<Timestamp> timeIntroOptional = Optional.ofNullable(computer.getIntroduced());
		Optional<Timestamp> timeDiscOptional = Optional.ofNullable(computer.getDiscontinued());
		if(timeIntroOptional.isPresent()) {
			dtoComputer.date_introduced=String.valueOf(computer.getIntroduced().toLocalDateTime().toLocalDate());

		}
		else {
			dtoComputer.date_introduced="";

		}
		if(timeDiscOptional.isPresent()) {
			dtoComputer.date_discontinued=String.valueOf(computer.getDiscontinued().toLocalDateTime().toLocalDate());
		}else {
			dtoComputer.date_discontinued="";

		}
		dtoComputer.companyId=String.valueOf(computer.getCompany().getId());
		dtoComputer.companyName=computer.getCompany().getName();
		return  dtoComputer ;
	}
}
