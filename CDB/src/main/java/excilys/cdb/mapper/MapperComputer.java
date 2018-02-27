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
		
		Computer computer = new Computer(Long.valueOf(dtoComputer.id),dtoComputer.name, Timestamp.valueOf(dtoComputer.date_introduced),Timestamp.valueOf(dtoComputer.date_discontinued),company);
		return computer;
	}
	
	public DtoComputer mapToDto(Computer computer) {
		DtoComputer dtoComputer = new DtoComputer();
		dtoComputer.name=computer.getName();
		dtoComputer.id=String.valueOf(computer.getId());
		Optional<Timestamp> timeIntroOptional = Optional.ofNullable(computer.getIntroduced());
		Optional<Timestamp> timeDiscOptional = Optional.ofNullable(computer.getDiscontinued());
		Timestamp timeIntro;
		Timestamp timeDisc;
		if(timeIntroOptional.isPresent()) {
			timeIntro= timeIntroOptional.get();
			dtoComputer.date_introduced=String.valueOf(computer.getIntroduced().toLocalDateTime().toLocalDate());

		}
		else {
			dtoComputer.date_introduced="";
			
		}
		if(timeDiscOptional.isPresent()) {
			timeDisc= timeDiscOptional.get();
			dtoComputer.date_discontinued=String.valueOf(computer.getDiscontinued().toLocalDateTime().toLocalDate());

		}else {
			dtoComputer.date_discontinued="";

		}
		dtoComputer.companyId=String.valueOf(computer.getCompany().getId());
		dtoComputer.companyName=computer.getCompany().getName();
		return  dtoComputer ;
	}
}
