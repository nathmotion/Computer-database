package main.java.excilys.cdb.model;

import java.util.ArrayList;

public class Page<T> {

	public int offset = 5;
	public ArrayList<T> page;
	public int limit = 10;
	public int current;

	public Page(int offset, ArrayList<T> page) {
		this.offset = offset;
		this.page = page;
	}

	public Page() {

	}

	public int atPage(int numPage) {
		return (numPage - 1) * limit;

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

	public ArrayList<T> getPage() {
		return page;
	}

	public void setPage(ArrayList<T> list) {
		this.page = list;
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

}
