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

public enum DaoCompany implements InterfaceDao<Company> {
	INSTANCE;

	private final String queryGetAll = "SELECT id,name FROM company  ";
	private final String queryGetPage = "SELECT id, name FROM company LIMIT ? , ?";

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

	@Override
	public int getNbElement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean create(Company t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Company t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Company t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Company> findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Company> getSearch(int offset, int limitPage, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbElementSearch(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

}
