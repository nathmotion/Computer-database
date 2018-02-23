package main.java.excilys.cdb.dao;

public abstract class Page<T>  {

	public abstract int getNbElements();

	public abstract void setNbElements(int nbElements);

	public abstract void add(T t);
}
