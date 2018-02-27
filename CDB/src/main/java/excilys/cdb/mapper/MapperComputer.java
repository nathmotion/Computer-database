package main.java.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.util.Optional;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.model.Computer;

public enum MapperComputer {
	INSTANCE;

	public Computer mapToEntity(DtoComputer dtoComputer) {
		Computer computer = new Computer(Long.valueOf(dtoComputer.id),dtoComputer.name, Timestamp.valueOf(dtoComputer.date_introduced),Timestamp.valueOf(dtoComputer.date_discontinued),Long.valueOf(dtoComputer.company_id));
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
		dtoComputer.company_id=String.valueOf(computer.getCompany_id());
		return  dtoComputer ;
	}
}
