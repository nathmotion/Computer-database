package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;

public class DaoCompany extends Dao<Company>{

		final static String queryGetAll ="SELECT id,name FROM company  ";

	
	/**
	 * 		REQUETE SQL    RECUPERE LA LISTE DES COMPAGNIES
	 */
	@Override
	public ArrayList<Company>getAll() {
		// TODO Auto-generated method stub
		Company ic = null ;
		ArrayList<Company> listCompany = new ArrayList<Company>();
			// requeste SQL 
		 	Statement s;
			//  Singleton connection manager
		 	SingletonConn con= SingletonConn.INSTANCE;		
			con.initConn();
			try {
							// preparation de la requete dans l'instance de connection
				s = con.getConn().createStatement();										// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
				ResultSet rs=s.executeQuery(queryGetAll);
									// fermeture de connexion									
					// lecture par ligne du resultats de la requetes
				while(rs.next()) {
					ic =new Company(rs.getLong("id"),rs.getString("name"));
					listCompany.add(ic);
				}
				con.closeConn();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.err.println(" error requetes GET ALL : " + e.getMessage());
			}
			
				
			
	// pas sur du retour
		return listCompany;
	}




}
