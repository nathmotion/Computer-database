package main.java.excilys.cdb.dao;

import java.util.ArrayList;

import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

public abstract class Page<T>  {

	public abstract int getNbElements();

	public abstract void setNbElements(int nbElements);

	public abstract void add(T t);
}
