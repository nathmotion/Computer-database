package main.java.excilys.cdb.dao;

import java.util.ArrayList;

public class Page<T>  {
	
	private int offset=5;
	private	ArrayList<T> page;
		
	public int getNbElements() {
		return 0;
	}

	public void setNbElements(int nbElements) {
	}

	public void add(T t) {
	}
}
