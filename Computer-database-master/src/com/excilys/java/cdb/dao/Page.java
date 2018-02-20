package com.excilys.java.cdb.dao;

import java.util.ArrayList;

import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;

public abstract class Page<T>  {

	public abstract int getNbElements();

	public abstract void setNbElements(int nbElements);

	public abstract void add(T t);
}
