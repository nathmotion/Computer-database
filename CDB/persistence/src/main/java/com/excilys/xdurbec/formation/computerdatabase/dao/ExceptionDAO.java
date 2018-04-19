package com.excilys.xdurbec.formation.computerdatabase.dao;

public class ExceptionDAO extends Exception {

	private static final long serialVersionUID = 1L;
	public static final String ID_COMPANY_ERROR = "No company exist with this id.";
	public static final String ID_COMPUTER_ERROR = "No computer exist with this id.";
	public static final String CONNECTION_ERROR = "Connection fail with BDD.";
	public static final String CREATE_ERROR = "Creation fail.";
	public static final String UPDATE_ERROR = "Update fail.";
	public static final String DELETE_ERROR = "Delete fail.";
	public static final String STATEMENT_ERROR = "Statement initialisation fail.";
	public static final String DOES_EXIST_ERROR = "Does exist fail.";
	public static final String GET_ALL_ERROR = "Get all fail.";
	public static final String CLASS_NOT_FOUND_ERROR = "Driver for connection not found.";
	public static final String COMPUTER_NUMBER_ERROR = "Computer number error";
	public static final String GET_BY_ID_COMPANY = "Get company error";
	public ExceptionDAO(String msg) {
		super(msg);
	}
}
