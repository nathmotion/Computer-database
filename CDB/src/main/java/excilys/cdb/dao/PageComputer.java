package main.java.excilys.cdb.dao;

import java.util.ArrayList;

import main.java.excilys.cdb.model.Computer;

public class PageComputer extends Page<Computer> {
	
	ArrayList<Computer> computers;
	
	int nbElement= 10 ;
	
	
	@Override
	public int getNbElements() {
		return nbElement;
	}

	@Override
	public void setNbElements(int nbElements) {
	}

	public ArrayList<Computer> getComputers() {
		return computers;
	}

	public void setComputers(ArrayList<Computer> computers) {
		this.computers = computers;
	}

	@Override
	public void add(Computer computer) {
		if(computer == null) {
		computers.add(computer);
		}
	}

	@Override
	public String toString() {
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
		return "\n....";
	}

}
