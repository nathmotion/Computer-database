package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;

public class DaoCompany extends Dao<Company>{

	private String defaultquery = " Select id, name from company";
	
	private ResultSet results;
		
	
	// requete sql pour trouver une Compagnie grace a l'id indiqué
	@Override
	public Optional<Company>  findById(Long id) {
		// TODO Auto-generated method stub
		Company ic = null ;
		
			// requeste SQL 
			String queryfindbyId = "SELECT id ,name FROM company WHERE id=?";
		 	PreparedStatement ps;
			//  Singleton connection manager
		 	SingletonConn con= SingletonConn.INSTANCE;		
			
			try {
							// preparation de la requete dans l'instance de connection
				ps = con.getConn().prepareStatement(queryfindbyId);
				ps.setLong(1, id);
																													// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
				ResultSet rs=ps.executeQuery();
				
									// fermeture de connexion
				con.closeConn();
												
					// lecture par ligne du resultats de la requetes
				ic =new Company(rs.getLong("id"),rs.getString("name"));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.err.println(" error requetes FIND : " + e.getMessage());
			}
			
				
			
	// pas sur du retour
		return Optional.ofNullable(ic);
	}

	@Override
	public boolean create(Company obj) {
		// TODO Auto-generated method stub
		
		String queryfindbyId = "INSERT INTO company (id, name) VALUES (?, ?)";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		
		try {
			
			ps = con.getConn().prepareStatement(queryfindbyId);
			ps.setLong(1,obj.getId());
			ps.setString(2, obj.getName());
			ps.executeUpdate();
			
			con.closeConn();
			
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(" error requete CREATE  : " + e.getMessage());
		}
		
			
		
		return false;
	}

	@Override
	public boolean update(Company obj) {
		// TODO Auto-generated method stub
		String queryfindbyId = "INSERT INTO company (id, name) VALUES (?, ?)";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		
		return false;
	}

	@Override
	public boolean delete(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}


}
