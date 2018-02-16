package com.excilys.java.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.sound.sampled.AudioFileFormat.Type;

import org.apache.log4j.Logger;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;


public class DaoComputer extends Dao<Computer>{

	final static Logger logger = Logger.getLogger(DaoComputer.class);

	final static String queryGetAll ="SELECT id, name, introduced, discontinued, company_id FROM computer  ";
	final static String queryById = "SELECT id, name, introduced, discontinued, company_id FROM computer  WHERE id=?";
	final static String queryUpdate = "UPDATE computer SET name= ?, instroduced=? , discontinued=? ,company_id= ? WHERE id =?";
	final static String queryCreate= "INSERT INTO computer ( name, introduced, discontinued ,company_id) VALUES (?, ?, ?, ?)";
	final static String queryDelete= "DELETE FROM computer WHERE id =?";
	final static String queryGetPage= "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? , ?";
	final static String queryNbComputer="SELECT count(*) as nbcomputer FROM computer";
	/**
	 *              						======== REQUETES SQL 	RECUPERE LA LISTE DES COMPUTER   ==============
	 */
	@Override
	public ArrayList<Computer> getAll() {
		Computer icomputer = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(Statement s = con.getConn().createStatement()) {
			ResultSet rs=s.executeQuery(queryGetAll);
			while(rs.next()) {
				icomputer=new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
				listComputer.add(icomputer);
			}
			con.closeConn();
		} catch (SQLException e) {

			logger.error(" error requetes GET ALL : " + e.getMessage());
		}

		return listComputer;
	}


	/**
	 * 														=======	PAGINATION	Computer =======
	 */
	@Override
	public ArrayList<Computer> getPage(int offset) {
		Computer icomputer = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(PreparedStatement s = con.getConn().prepareStatement(queryGetPage)) {

			s.setInt(1, offset);
			s.setInt(2,10);
			ResultSet rs=s.executeQuery();
			while(rs.next()) {
				icomputer=new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
				listComputer.add(icomputer);
			}
			con.closeConn();
		} catch (SQLException e) {

			logger.error(" error requetes GET ALL : " + e.getMessage());
		}

		return listComputer;



	}
	/**
	 * 
	 * @return
	 */
	public int getNbComputer() {
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		int nbComputer=0;
		System.out.println("coouocu");
		try(Statement s = con.getConn().createStatement()) {
			ResultSet rs=s.executeQuery(queryNbComputer);
			while(rs.next()) {
				nbComputer=rs.getInt("nbcomputer");
			}
			con.closeConn();
		} catch (SQLException e) {

			logger.error(" error requetes GET ALL : " + e.getMessage());
		}
		System.out.println(" nbComputer =" +nbComputer);
		return nbComputer;

	}

	/**
	 *                         			 ======= REQUETES SQL AJOUTE UN ORDINATEUR PASSER PAR PARAMETRE ============= 
	 * @param obj
	 * @return
	 */
	public boolean create(Computer obj) {

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(PreparedStatement ps= con.getConn().prepareStatement(queryCreate)) {

			ps.setString(1, obj.getName());		
			if( obj.getIntroduced()!=null) {
				ps.setTimestamp(2,obj.getIntroduced());
			}
			else {
				ps.setTimestamp(2, null);
			}
			if( obj.getDiscontinued()!=null) {
				ps.setTimestamp(3, obj.getDiscontinued());
			}
			else {
				ps.setTimestamp(3,null);
			}
			if(obj.getCompany_id()!=0) {
				ps.setLong(4, obj.getCompany_id());
			}else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.executeUpdate();
			System.out.println("Ajout reussi ... ");
			con.closeConn();
			return true;

		} catch (SQLException e) {

			logger.error(" error requete CREATE  : " + e.getMessage());
		}



		return false;
	}


	/**
	 * 										========== REQUETES SQL 	MIS A JOUR UN ORDINATEUR PASSER PAR PARAMETRE  ============ 	
	 * @param obj
	 * @return
	 */
	public boolean update(Computer obj) {

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(PreparedStatement ps=con.getConn().prepareStatement(queryUpdate)) {
			ps.setString(1, obj.getName());
			ps.setTimestamp(2,obj.getIntroduced());
			ps.setTimestamp(3, obj.getDiscontinued());
			ps.setLong(4,obj.getCompany_id());
			ps.setLong(5,obj.getId());
			ps.executeUpdate();
			con.closeConn();
			System.out.println("mise a jour reussi ... ");
			return true;
		} catch (SQLException e) {
			logger.error(" error requete Update  : " + e.getMessage());
		}
		return false;
	}

	/**
	 * 								    	====== 	REQUETES SQL 	SUPPRIMER UN ORDINATEUR PASSER PAR PARAMETRE ===========
	 * @param obj
	 * @return
	 */
	public boolean delete(Computer computer) {

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(PreparedStatement ps=con.getConn().prepareStatement(queryDelete)){
			ps.setLong(1,computer.getId());
			ps.executeUpdate();
			con.closeConn();
			System.out.println("Suppression reussi ... ");
			return true;
		} catch (SQLException e) {
			logger.error(" error requete DELETE  : " + e.getMessage());
		}
		return false;
	}

	/**
	 * 										===== REQUETES SQL 	RECUPERE UN ORDINATEUR PASSER PAR PARAMETRE ===========
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id){
		Computer icomputer = null;
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();
		try(PreparedStatement stat= con.getConn().prepareStatement(queryById)){
			stat.setInt(1,id);																									// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au objet vide
			ResultSet rs=stat.executeQuery();
			while(rs.next()) {
				icomputer =new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
			}
			con.closeConn();
		} catch (SQLException e) {
			logger.error(" error requetes GET ALL : " + e.getMessage());
		}
		Optional<Computer> op= Optional.ofNullable(icomputer);
		return op;
	}





}
