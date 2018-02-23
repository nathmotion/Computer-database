package main.java.excilys.cdb.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.log4j.Logger;

import main.java.excilys.cdb.connectionmanager.SingletonConn;
import main.java.excilys.cdb.model.Computer;



public enum DaoComputer implements Dao<Computer>{
	INSTANCE;

	final static Logger LOGGER = Logger.getLogger(DaoComputer.class);
	final static String QUERY_GET_ALL ="SELECT id, name, introduced, discontinued, company_id FROM computer  ";
	final static String QUERY_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer  WHERE id=?";
	final static String QUERY_UPDATE = "UPDATE computer SET name= ?, introduced=? , discontinued=? ,company_id= ? WHERE id =?";
	final static String QUERY_CREATE= "INSERT INTO computer ( name, introduced, discontinued ,company_id) VALUES (?, ?, ?, ?)";
	final static String QUERY_DELETE= "DELETE FROM computer WHERE id =?";
	final static String QUERY_GET_PAGE= "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? , ?";
	final static String QUERY_NB_ELEMENT="SELECT count(*) as nbcomputer FROM computer";
	/**
	 *              						======== REQUETES SQL 	RECUPERE LA LISTE DES COMPUTER   ==============
	 */
	@Override
	public ArrayList<Computer> getAll() {
		Computer iComputer = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();

		try(Statement stat = con.getConn().createStatement()) {
			ResultSet rs=stat.executeQuery(QUERY_GET_ALL);

			while(rs.next()) {
				iComputer=new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
				listComputer.add(iComputer);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}
		return listComputer;
	}
	/**
	 * 														=======	PAGINATION	Computer =======
	 */
	@Override
	public ArrayList<Computer> getPage(int offset) {
		Computer iComputer = null ;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();

		try(PreparedStatement stat = con.getConn().prepareStatement(QUERY_GET_PAGE)) {
			stat.setInt(1, offset);
			stat.setInt(2,10);
			ResultSet rs=stat.executeQuery();

			while(rs.next()) {
				iComputer=new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
				listComputer.add(iComputer);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
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

		try(Statement stat = con.getConn().createStatement()) {
			ResultSet rs=stat.executeQuery(QUERY_NB_ELEMENT);

			while(rs.next()) {
				nbComputer=rs.getInt("nbcomputer");
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}
		System.out.println(" nbComputer =" +nbComputer);
		return nbComputer;
	}
	/**
	 *                         			 ======= REQUETES SQL AJOUTE UN ORDINATEUR PASSER PAR PARAMETRE ============= 
	 * @param computer
	 * @return
	 */
	public boolean create(Computer computer) {

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();

		try(PreparedStatement ps= con.getConn().prepareStatement(QUERY_CREATE)) {
			ps.setString(1, computer.getName());		

			if( computer.getIntroduced()!=null) {

				ps.setTimestamp(2,computer.getIntroduced());
			}
			else {
				ps.setTimestamp(2, null);
			}
			if( computer.getDiscontinued()!=null) {
				ps.setTimestamp(3, computer.getDiscontinued());
			}
			else {
				ps.setTimestamp(3,null);
			}
			if(computer.getCompany_id()!=0) {
				ps.setLong(4, computer.getCompany_id());
			}else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.executeUpdate();
			System.out.println("Ajout reussi ... ");
			con.closeConn();
			return true;
		} catch (SQLException e) {
			LOGGER.error(" error requete CREATE  : " + e.getMessage());
		}
		return false;
	}

	/**
	 * 										========== REQUETES SQL 	MIS A JOUR UN ORDINATEUR PASSER PAR PARAMETRE  ============ 	
	 * @param computer
	 * @return
	 */
	public boolean update(Computer computer) {

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();

		try(PreparedStatement ps=con.getConn().prepareStatement(QUERY_UPDATE)) {
			ps.setString(1, computer.getName());

			if( computer.getIntroduced()!=null) {

				ps.setTimestamp(2,computer.getIntroduced());
			}
			else {
				ps.setTimestamp(2, null);
			}
			if( computer.getDiscontinued()!=null) {
				ps.setTimestamp(3, computer.getDiscontinued());
			}
			else {
				ps.setTimestamp(3,null);
			}
			if(computer.getCompany_id()!=0) {
				ps.setLong(4, computer.getCompany_id());
			}else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setLong(5,computer.getId());
			ps.executeUpdate();
			con.closeConn();
			System.out.println("mise a jour reussi ... ");
			return true;
		} catch (SQLException e) {
			LOGGER.error(" error requete Update  : " + e.getMessage());
		}
		return false;
	}

	/**
	 * 								    	====== 	REQUETES SQL 	SUPPRIMER UN ORDINATEUR PASSER PAR PARAMETRE ===========
	 * @param computer
	 * @return
	 */
	public boolean delete(Computer computer) {

		SingletonConn con= SingletonConn.INSTANCE;		
		con.initConn();

		try(PreparedStatement ps=con.getConn().prepareStatement(QUERY_DELETE)){
			ps.setLong(1,computer.getId());
			ps.executeUpdate();
			con.closeConn();
			System.out.println("Suppression reussi ... ");
			return true;
		} catch (SQLException e) {
			LOGGER.error(" error requete DELETE  : " + e.getMessage());
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

		try(PreparedStatement stat= con.getConn().prepareStatement(QUERY_BY_ID)){
			stat.setInt(1,id);																									
			ResultSet rs=stat.executeQuery();

			while(rs.next()) {
				icomputer =new Computer(rs.getLong("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),rs.getLong("company_id"));
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}
		Optional<Computer> op= Optional.ofNullable(icomputer);
		return op;
	}
}