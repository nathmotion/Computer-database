package main.java.excilys.cdb.model;

import java.util.List;

public class Page<T> {

	public int offset = 5;
	public List<T> elementsPage;
	public int limit = 10;
	public int current;
	private String search;
	private String typeOrder;
	private int orderCmp;

	public Page(int offset, List<T> page) {
		this.offset = offset;
		this.elementsPage = page;
	}

	public Page() {

	}

	public Page(int current, int offset, int limit) {
		this.offset = offset;
		this.current = current;
		this.limit = limit;
	}

	public int atPage(int numPage) {
		current = (numPage - 1) * limit;
		return current;

	}

	// getters and setters
	public Page(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getElementsPage() {
		return elementsPage;
	}

	public void setElementsPage(List<T> list) {
		this.elementsPage = list;
	}

	public int getNbElements() {
		return offset;
	}

	public void setNbElements(int nbElements) {
		offset = nbElements;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	
	public String getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(String typeOrder) {
		this.typeOrder = typeOrder;
	}

	public int getOrderCmp() {
		return orderCmp;
	}

	public void setOrderCmp(int orderCmp) {
		this.orderCmp = orderCmp;
	}

}
