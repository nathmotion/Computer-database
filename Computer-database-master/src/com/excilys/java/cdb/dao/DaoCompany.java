package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;

public enum DaoCompany implements Dao<Company>{
	INSTANCE;

	private final  String queryGetAll ="SELECT id,name FROM company  ";
	private final  String queryGetPage= "SELECT id, name FROM company LIMIT ? , ?";
	private final  Logger logger = Logger.getLogger(DaoCompany.class);

	DaoCompany(){

	}
	/**
	 * 								========	REQUETE SQL    RECUPERE LA LISTE DES COMPAGNIES	========
	 */
	@Override
	public ArrayList<Company>getAll() {
		Company company = null ;
		ArrayList<Company> listCompany = new ArrayList<Company>();
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(Statement s = con.getConn().createStatement();) {
			ResultSet rs=s.executeQuery(queryGetAll);
			while(rs.next()) {
				company =new Company(rs.getLong("id"),rs.getString("name"));
				listCompany.add(company);
			}
			con.closeConn();
		} catch (SQLException e) {
			logger.error(" error requetes GET ALL : " + e.getMessage());
		}
		return listCompany;
	}

	/**
	 *												 ========	REQUETE SQL    RECUPERE COMPANY PAR PAGE	========
	 */
	@Override
	public ArrayList<Company> getPage(int offset) {
		Company company = null ;
		ArrayList<Company> listCompany = new ArrayList<Company>();
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(PreparedStatement s= con.getConn().prepareStatement(queryGetPage);){

			s.setInt(1, offset);// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
			s.setInt(2,10);
			ResultSet rs=s.executeQuery();
			while(rs.next()) {
				company =new Company(rs.getLong("id"),rs.getString("name"));
				listCompany.add(company);
			}
			con.closeConn();
		} catch (SQLException e) {

			logger.error(" error requetes GET ALL : " + e.getMessage());
		}

		return listCompany;
	}

}
