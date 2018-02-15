package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;


public class DaoComputer extends Dao<Computer>{

		
	final static String queryGetAll ="SELECT id, name, introduced, discontinued, company_id FROM computer  ";
	final static String queryById = "SELECT id, name, introduced, discontinued, company_id FROM computer  WHERE id=?";
	final static String queryUpdate = "UPDATE computer SET name= ?, instroduced=? , discontinued=? ,company_id= ? WHERE id =?";
	final static String queryCreate= "INSERT INTO computer ( name, introduced, discontinued ,company_id) VALUES (?, ?, ?, ?)";
	final static String queryDelete= "DELETE FROM computer WHERE id =?";

	/**
	 * REQUETES SQL 	RECUPERE LA LISTE DES COMPUTER
	 */
	@Override
	public ArrayList<Computer> getAll() {
		// TODO Auto-generated method stub
		Computer ic = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
		 	Statement s;
			//  Singleton connection manager
		 	SingletonConn con= SingletonConn.INSTANCE;		
			con.initConn();
			try {
							// preparation de la requete dans l'instance de connection
				s = con.getConn().createStatement();																											// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
				ResultSet rs=s.executeQuery(queryGetAll);
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
	
	
	
	
	/**
	 * REQUETES SQL 	AJOUTE UN ORDINATEUR PASSER PAR PARAMETRE 
	 * @param obj
	 * @return
	 */
	public boolean create(Computer obj) {
		// TODO Auto-generated method stub
		
		
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			
			ps = con.getConn().prepareStatement(queryCreate);
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
	
	
	/**
	 * 	REQUETES SQL 	MIS A JOUR UN ORDINATEUR PASSER PAR PARAMETRE 	
	 * @param obj
	 * @return
	 */
	public boolean update(Computer obj) {
		// TODO Auto-generated method stub
		
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			
			ps = con.getConn().prepareStatement(queryUpdate);
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

	/**
	 * REQUETES SQL 	SUPPRIMER UN ORDINATEUR PASSER PAR PARAMETRE 
	 * @param obj
	 * @return
	 */
	public boolean delete(Computer computer) {
		// TODO Auto-generated method stub
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			
			ps = con.getConn().prepareStatement(queryDelete);
			ps.setLong(1,computer.getId());
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
	
	/**
	 * REQUETES SQL 	RECUPERE UN ORDINATEUR PASSER PAR PARAMETRE 
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id){
		Computer icomputer = null;
		PreparedStatement stat;
		//  Singleton connection manager
	 	SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
						// preparation de la requete dans l'instance de connection
			stat = con.getConn().prepareStatement(queryById);
			stat.setInt(1,id);																									// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
			ResultSet rs=stat.executeQuery();
			
								// fermeture de connexion
							
				// lecture par ligne du resultats de la requetes
			while(rs.next()) {
				icomputer =new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
			}
			con.closeConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.err.println(" error requetes GET ALL : " + e.getMessage());
		}
		Optional<Computer> op= Optional.ofNullable(icomputer);
		return op;
	}

	
}
