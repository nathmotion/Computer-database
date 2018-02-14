package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;

public class DaoComputer extends Dao<Computer>{

	
	@Override
	public ArrayList<Computer> getAll() {
		// TODO Auto-generated method stub
		Computer ic = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
			// requeste SQL 
			String query = "SELECT id, name, introduced, discontinued, company_id FROM computer  ";
		 	Statement s;
			//  Singleton connection manager
		 	SingletonConn con= SingletonConn.INSTANCE;		
			con.initConn();
			try {
							// preparation de la requete dans l'instance de connection
				s = con.getConn().createStatement();
																													// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
				ResultSet rs=s.executeQuery(query);
				
									// fermeture de connexion
				
												
					// lecture par ligne du resultats de la requetes
				
				while(rs.next()) {
					ic =new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
					listComputer.add(ic);
				}
				con.closeConn();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.err.println(" error requetes GET ALL : " + e.getMessage());
			}
			
				
			
	// return la liste
		return listComputer;
		
		
	}
	public boolean create(Computer obj) {
		// TODO Auto-generated method stub
		
		String query= "INSERT INTO computer ( name, introduced, discontinued ,company_id) VALUES (?, ?, ?, ?)";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			
			ps = con.getConn().prepareStatement(query);
			ps.setString(1, obj.getName());
			ps.setTimestamp(2,obj.getIntroduced());
			ps.setTimestamp(3, obj.getDiscontinued());
			ps.setLong(4,obj.getCompany_id());
			ps.executeUpdate();
			
			con.closeConn();
			return true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(" error requete CREATE  : " + e.getMessage());
		}
		
			
		
		return false;
	}

	
	public boolean update(Computer obj) {
		// TODO Auto-generated method stub
		String query = "UPDATE computer SET name= ?, instroduced=? , discontinued=? ,company_id= ? WHERE id =?";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			
			ps = con.getConn().prepareStatement(query);
			ps.setString(1, obj.getName());
			ps.setTimestamp(2,obj.getIntroduced());
			ps.setTimestamp(3, obj.getDiscontinued());
			ps.setLong(4,obj.getCompany_id());
			ps.setLong(5,obj.getId());
			ps.executeUpdate();
			
			con.closeConn();
			return true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(" error requete Update  : " + e.getMessage());
		}
		return false;
	}

	
	public boolean delete(Computer obj) {
		// TODO Auto-generated method stub
		String query = "DELETE FROM computer WHERE id =?";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			
			ps = con.getConn().prepareStatement(query);
			ps.setLong(1,obj.getId());
			ps.executeUpdate();
			
			con.closeConn();
			return true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(" error requete DELETE  : " + e.getMessage());
		}
		return false;
	}


	
}
