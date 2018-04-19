package com.excilys.xdurbec.formation.computerdatabase.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.excilys.xdurbec.formation.computerdatabase.model.Computer;

public class ComputerMapperDTO {
	
	private ComputerMapperDTO() { }

	public static Computer toComputer(ComputerDTO computerDTO) {
		Computer computer = new Computer();
		computer.setName(computerDTO.getName()); 
		if (computerDTO.getIntroduced() != "") {
			computer.setIntroduced(Date.valueOf(computerDTO.getIntroduced())); 
		} 
		if (computerDTO.getDiscontinued() != "") {
			computer.setDiscontinued(Date.valueOf(computerDTO.getDiscontinued())); 
		}
		if (computerDTO.getCompany() != null) {
			computer.setCompany(CompanyMapperDTO.toCompany((computerDTO.getCompany())));
		}
		computer.setId(computerDTO.getId());
		return computer;

	}

	public static ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());

		if (computer.getIntroduced() != null) {
			computerDTO.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		}
		if (computer.getCompany() != null) {
			computerDTO.setCompany(CompanyMapperDTO.toCompanyDTO(computer.getCompany()));
		}
		return computerDTO;
	}

	public static List<Computer> toComputerList(List<ComputerDTO> computerDTOList) {
		List<Computer> computerList = new ArrayList<>();
		for (ComputerDTO computerDTO:computerDTOList) {
			computerList.add(toComputer(computerDTO));
		}
		return computerList;
	}

	public static List<ComputerDTO> toComputerDTOList(List<Computer> computerList) {
		List<ComputerDTO> computerDTOList = new ArrayList<>();

		for (Computer computer : computerList) {
			computerDTOList.add(toComputerDTO(computer));
		}
		return computerDTOList;
	}
}
