package main.java.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.excilys.cdb.connectionmanager.SingletonConn;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

public enum DaoCompany implements Dao<Company> {
	INSTANCE;

	private final String queryGetAll = "SELECT id,name FROM company  ";
	private final String queryGetPage = "SELECT id, name FROM company LIMIT ? , ?";
	private final String QUERY_BY_ID = "SELECT name FROM company  WHERE id=?";

	final static Logger LOGGER = LogManager.getLogger(DaoCompany.class);

	DaoCompany() {

	}

	/**
	 * ======== REQUETE SQL RECUPERE LA LISTE DES COMPAGNIES ========
	 */
	@Override
	public ArrayList<Company> getAll() {
		Company company = null;
		ArrayList<Company> listCompany = new ArrayList<Company>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		try (Statement s = con.getConn().createStatement();) {
			ResultSet rs = s.executeQuery(queryGetAll);
			while (rs.next()) {
				company = new Company(rs.getLong("id"), rs.getString("name"));
				listCompany.add(company);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}
		return listCompany;
	}

	/**
	 * ======== REQUETE SQL RECUPERE COMPANY PAR PAGE ========
	 */
	@Override
	public ArrayList<Company> getPage(int offset, int limitPage) {
		Company company = null;
		ArrayList<Company> listCompany = new ArrayList<Company>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		try (PreparedStatement s = con.getConn().prepareStatement(queryGetPage);) {

			s.setInt(1, offset);// pas de optional car pas besoin de faire gaffe au requete vide mais plutot au
								// objet vide
			s.setInt(2, 10);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				company = new Company(rs.getLong("id"), rs.getString("name"));
				listCompany.add(company);
			}
			con.closeConn();
		} catch (SQLException e) {

			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}

		return listCompany;
	}

	/**
	 * ===== REQUETES SQL RECUPERE UN ORDINATEUR PASSER PAR PARAMETRE ===========
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Company> findById(int id) {
		Company company = null;
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_BY_ID)) {
			stat.setInt(1, id);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				company = new Company((long) id, rs.getString("name"));
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}
		Optional<Company> op = Optional.ofNullable(company);
		return op;
	}

}
