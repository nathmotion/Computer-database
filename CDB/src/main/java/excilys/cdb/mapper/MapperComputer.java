package main.java.excilys.cdb.mapper;

import java.sql.Timestamp;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.model.Computer;

public enum MapperComputer {
	INSTANCE;

	Computer mapToEntity(DtoComputer dtoComputer) {
		Computer computer = new Computer(Long.valueOf(dtoComputer.id),dtoComputer.name, Timestamp.valueOf(dtoComputer.date_introduced),Timestamp.valueOf(dtoComputer.date_discontinued),Long.valueOf(dtoComputer.company_id));
		return computer;
	}
	
	DtoComputer mapToDto(Computer computer) {
		DtoComputer dtoComputer = new DtoComputer();
		dtoComputer.name=computer.getName();
		dtoComputer.id=String.valueOf(computer.getId());
		dtoComputer.date_introduced =String.valueOf(computer.getIntroduced());
		dtoComputer.date_discontinued=String.valueOf(computer.getDiscontinued());
		dtoComputer.company_id=String.valueOf(computer.getCompany_id());
		return  dtoComputer ;
	}
}
