package main.java.excilys.cdb.dao;

import java.util.ArrayList;

import main.java.excilys.cdb.model.Company;

public class PageCompany extends Page<Company>{
	private ArrayList<Company> companies;

	@Override
	public int getNbElements() {
		return 0;
	}

	@Override
	public void setNbElements(int nbElements) {
	}
	public ArrayList<Company> getComputers() {
		return companies;
	}

	public void setComputers(ArrayList<Company> computers) {
		this.companies = computers;
	}

	@Override
	public void add(Company company) {
		companies.add(company);
	}

	@Override
	public String toString() {
		String chaine = "";
	
		for(Company company : companies) {
			chaine+=company.toString();
		}
		 return chaine;
	}
}
