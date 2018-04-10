package main.java.excilys.cdb.constantes;

public class ConstanteRequeteSql {
		// Computer 
	public final static String QUERY_GET_ALL_COMPUTER = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name, company.id FROM computer LEFT JOIN company ON company_id = company.id ";
	public final static String QUERY_BY_ID_COMPUTER = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id  WHERE computer.id=?";
	public final static String QUERY_UPDATE_COMPUTER = "UPDATE computer SET name= ?, introduced=? , discontinued=? ,company_id= ? WHERE id =?";
	public final static String QUERY_CREATE_COMPUTER = "INSERT INTO computer ( name, introduced, discontinued ,company_id) VALUES (?, ?, ?, ?)";
	public final static String QUERY_DELETE_COMPUTER = "DELETE FROM computer WHERE id =?";
	public final static String QUERY_GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id  LIMIT ? , ?";
	public final static String QUERY_NB_ELEMENT_COMPUTER = "SELECT count(*) as nbcomputer FROM computer";
	public final static String QUERY_BY_NAME = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ?1 OR company.name LIKE ?1 LIMIT ? , ?";
	public final static String QUERY_NB_ELEMENT_BY_NAME = "SELECT count(*) as nbcomputer FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ?1 OR company.name LIKE ?1";
	public final static String QUERY_ORDER= "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY %s.name %s LIMIT ? , ? ";
	public final static String QUERY_DELETE_BY_COMPANY ="DELETE FROM computer WHERE company_id= ? ";

	// Company
	public static final String QUERY_GET_ALL_COMPANY = "SELECT id,name FROM company  ";
	public static final String QUERY_GET_PAGE_COMPANY = "SELECT id, name FROM company LIMIT ? , ?";
	public final static String QUERY_GET_BY_ID_COMPANY ="SELECT id FROM company WHERE id= ?";
	public final static String QUERY_DELETE_COMPANY = "DELETE FROM company WHERE id =?";
	public final static String QUERY_UPDATE_COMPANY = "UPDATE computer SET name= ?, introduced=? , discontinued=? ,company_id= ? WHERE id =?";
}
