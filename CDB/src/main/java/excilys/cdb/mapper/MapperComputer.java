package main.java.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import main.java.excilys.cdb.dto.ComputerDto;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

@Component
public class MapperComputer {

	public Computer mapToEntity(ComputerDto dtoComputer) {
		Company company = new Company();
		company.setId(Long.parseLong(dtoComputer.getCompanyId()));
		company.setName(dtoComputer.getCompanyName());
		Computer computer = new Computer();
		if (dtoComputer.getDate_introduced() == null || dtoComputer.getDate_introduced().equals("")) {
			computer.setIntroduced(null);
		} else {
			computer.setIntroduced(LocalDate.parse(dtoComputer.getDate_introduced()));
		}

		if (dtoComputer.getDate_discontinued() == null || dtoComputer.getDate_discontinued().equals("")) {
			computer.setDiscontinued(null);
		} else {
			LocalDate parseTmpDate = LocalDate.parse(dtoComputer.getDate_discontinued());
			computer.setDiscontinued(parseTmpDate);

		}

		if (dtoComputer.getId() != null) {
			computer.setId(Long.parseLong(dtoComputer.getId()));
		} else {
			computer.setId((long) 0);
		}

		computer.setName(dtoComputer.getName());
		computer.setCompany(company);
		return computer;
	}

	public ComputerDto mapToDto(Computer computer) {
		ComputerDto dtoComputer = new ComputerDto();
		dtoComputer.setName(computer.getName());
		dtoComputer.setId(computer.getId().toString());
		Optional<LocalDate> timeIntroOptional = Optional.ofNullable(computer.getIntroduced());
		Optional<LocalDate> timeDiscOptional = Optional.ofNullable(computer.getDiscontinued());

		if (timeIntroOptional.isPresent()) {
			dtoComputer.setDate_introduced(String.valueOf(computer.getIntroduced()));
		} else {
			dtoComputer.setDate_introduced("");
		}

		if (timeDiscOptional.isPresent()) {
			dtoComputer.setDate_discontinued(String.valueOf(computer.getDiscontinued()));
		} else {
			dtoComputer.setDate_discontinued("");
		}
		dtoComputer.setCompanyId((computer.getCompany().getId().toString()));
		dtoComputer.setCompanyName(computer.getCompany().getName());
		return dtoComputer;
	}
}
