package main.java.excilys.cdb.dao;

import java.util.ArrayList;

public class Page<T> {

	private int offset = 5;
	private ArrayList<T> page;

	public Page(int offset, ArrayList<T> page) {
		this.offset = offset;
		this.page = page;
	}

	public ArrayList<T> getPage() {
		return page;
	}

	public int getNbElements() {
		return offset;
	}

	public void setNbElements(int nbElements) {
		offset = nbElements;
	}

	public void add(T t) {
	}
}
