package com.excilys.xdurbec.formation.computerdatabase.service;

public class ExceptionService extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String ID_COMPANY_ERROR = "No company exist with this id.";
	public static final String ID_COMPUTER_ERROR = "No computer exist with this id.";
	public static final String CONNECTION_ERROR = "Connection fail with BDD.";
	public static final String CREATE_ERROR = "Creation fail.";
	public static final String UPDATE_ERROR = "Update fail.";
	public static final String DELETE_ERROR = "Delete fail.";
	public static final String STATEMENT_ERROR = "Statement initialisation fail.";
	public static final String DOES_EXIST_ERROR = "Does exist fail.";
	public static final String GET_ALL_ERROR = "Get all Service fail.";
	public static final String GET_ALL_ERROR_PAGE = "Get all page fail.";
	public static final String COMPUTER_NUMBER_ERROR = "Computer number error";
	public static final String GET_COMPANY_BY_ID = "Get company id error";
	public static final String DATE_ERROR = "Date of computer are not legal.";
	public static final String DELETE_COMPANY_ERROR = "Delete company by id error.";


	ExceptionService(String msg) {
		super(msg);
	}
}
