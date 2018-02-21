package main.java.excilys.cdb.dao;

import java.util.ArrayList;

import main.java.excilys.cdb.model.Company;

public class PageCompany extends Page<Company>{
	private ArrayList<Company> companies;

	private int nbElement= 10 ;

	@Override
	public int getNbElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNbElements(int nbElements) {
		// TODO Auto-generated method stub

	}
	public ArrayList<Company> getComputers() {
		return companies;
	}

	public void setComputers(ArrayList<Company> computers) {
		this.companies = computers;
	}

	@Override
	public void add(Company company) {
		// TODO Auto-generated method stub
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
