package main.java.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import main.java.excilys.cdb.dto.DtoComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

@Component
public class MapperComputer {

	public Computer mapToEntity(DtoComputer dtoComputer) {
		Company company = new Company();
		company.setId(Long.parseLong(dtoComputer.companyId));
		company.setName(dtoComputer.companyName);
		Computer computer = new Computer();
		if (dtoComputer.date_introduced == null || dtoComputer.date_introduced.equals("")) {
			computer.setIntroduced(null);
		} else {
			computer.setIntroduced(Timestamp.valueOf(LocalDate.parse(dtoComputer.date_introduced).atStartOfDay()));
		}

		if (dtoComputer.date_discontinued == null || dtoComputer.date_discontinued.equals("")) {
			computer.setDiscontinued(null);
		} else {
			LocalDate parseTmpDate = LocalDate.parse(dtoComputer.date_discontinued);
			computer.setDiscontinued(Timestamp.valueOf((parseTmpDate.atStartOfDay())));

		}

		if (dtoComputer.id != null) {
			computer.setId(Long.parseLong(dtoComputer.id));
		} else {
			computer.setId((long) 0);
		}

		computer.setName(dtoComputer.name);
		computer.setCompany(company);
		return computer;
	}

	public DtoComputer mapToDto(Computer computer) {
		DtoComputer dtoComputer = new DtoComputer();
		dtoComputer.name = computer.getName();
		dtoComputer.id = computer.getId().toString();
		Optional<Timestamp> timeIntroOptional = Optional.ofNullable(computer.getIntroduced());
		Optional<Timestamp> timeDiscOptional = Optional.ofNullable(computer.getDiscontinued());

		if (timeIntroOptional.isPresent()) {
			dtoComputer.date_introduced = String.valueOf(computer.getIntroduced().toLocalDateTime().toLocalDate());
		} else {
			dtoComputer.date_introduced = "";
		}

		if (timeDiscOptional.isPresent()) {
			dtoComputer.date_discontinued = String.valueOf(computer.getDiscontinued().toLocalDateTime().toLocalDate());
		} else {
			dtoComputer.date_discontinued = "";
		}
		dtoComputer.companyId = (computer.getCompany().getId().toString());
		dtoComputer.companyName = computer.getCompany().getName();
		return dtoComputer;
	}
}
