package com.excilys.xdurbec.formation.computerdatabase.model;

import java.util.ArrayList;
import java.util.List;


public class ComputerPage {

	private int pageNumber;
	private int nbComputerPerPage;
	private String filter;
	private List<Computer> computerList = new ArrayList<>();
	private Boolean ascendingOrder;
	private ComputerAttributes orderBy;
	
	public ComputerPage(int pageNumber, int nbComputerPerPage, String filter, ComputerAttributes ordresBy, Boolean ascendingOrder) {
		this.pageNumber = pageNumber;
		this.nbComputerPerPage = nbComputerPerPage;
		this.filter = filter;
		this.orderBy = ordresBy;
		this.ascendingOrder = ascendingOrder;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getNbComputerPerPage() {
		return nbComputerPerPage;
	}
	public void setNbComputerPerPage(int nbComputerPerPage) {
		this.nbComputerPerPage = nbComputerPerPage;
	}
	public List<Computer> getComputerList() {
		return computerList;
	}
	
	public String getFilter() {
		return this.filter;
	}
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
	

	public Boolean getAscendingOrder() {
		return ascendingOrder;
	}

	public void setAscendingOrder(Boolean ascendingOrder) {
		this.ascendingOrder = ascendingOrder;
	}

	public ComputerAttributes getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(ComputerAttributes orderBy) {
		this.orderBy = orderBy;
	}

	public void setComputerList(List<Computer> computerList) {
		this.computerList = computerList;
	}

	@Override
	public String toString() {
		return "ComputerPage [pageNumber=" + pageNumber + ", nbComputerPerPage=" + nbComputerPerPage + ", filter="
				+ filter + ", computerList=" + computerList + ", ascendingOrder=" + ascendingOrder + ", orderBy="
				+ orderBy + "]";
	}

	
	
}	
