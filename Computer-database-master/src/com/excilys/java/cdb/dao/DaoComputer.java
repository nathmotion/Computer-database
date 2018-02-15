package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.sound.sampled.AudioFileFormat.Type;

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
		Computer ic = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
		Statement s;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			s = con.getConn().createStatement();																											// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
			ResultSet rs=s.executeQuery(queryGetAll);
			// lecture par ligne du resultats de la requetes
			while(rs.next()) {
				ic =new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
				listComputer.add(ic);
			}
			con.closeConn();

		} catch (SQLException e) {

			System.err.println(" error requetes GET ALL : " + e.getMessage());
		}

		return listComputer;
	}




	/**
	 * REQUETES SQL 	AJOUTE UN ORDINATEUR PASSER PAR PARAMETRE 
	 * @param obj
	 * @return
	 */
	public boolean create(Computer obj) {
		PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {

			ps = con.getConn().prepareStatement(queryCreate);
			ps.setString(1, obj.getName());
			if( obj.getIntroduced()!=null) {
				ps.setTimestamp(2,obj.getIntroduced());
			}
			else {
				ps.setTimestamp(2, null);
			}
			if( obj.getIntroduced()!=null) {
				ps.setTimestamp(3, obj.getDiscontinued());
			}
			else {
				ps.setTimestamp(3,null);
			}

			ps.setLong(4,obj.getCompany_id());
			ps.executeUpdate();
			System.out.println("Ajout reussi ... ");

			con.closeConn();
			return true;

		} catch (SQLException e) {

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
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try {
			stat = con.getConn().prepareStatement(queryById);
			stat.setInt(1,id);																									// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
			ResultSet rs=stat.executeQuery();
			// lecture par ligne du resultats de la requetes
			while(rs.next()) {
				icomputer =new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
			}
			con.closeConn();
		} catch (SQLException e) {

			System.err.println(" error requetes GET ALL : " + e.getMessage());
		}
		Optional<Computer> op= Optional.ofNullable(icomputer);
		return op;
	}


}
