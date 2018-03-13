package main.java.excilys.cdb.dao;

import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.*;

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
import main.java.excilys.cdb.model.Page;

public enum DaoCompany implements InterfaceDao<Company> {
	INSTANCE;

	final static Logger LOGGER = LogManager.getLogger(DaoCompany.class);

	/**
	 * ======== REQUETE SQL : RECUPERE LA LISTE DES COMPAGNIES ========
	 */
	@Override
	public ArrayList<Company> getAll() {
		Company company = null;
		ArrayList<Company> listCompany = new ArrayList<Company>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (Statement s = con.getConn().createStatement();) {
			ResultSet rs = s.executeQuery(QUERY_GET_ALL_COMPANY);

			while (rs.next()) {
				company = new Company(rs.getLong("id"), rs.getString("name"));
				listCompany.add(company);
			}
		} catch (SQLException e) {
			LOGGER.error(" Erreur de la recuperation des données de Company : \n " + e.getMessage());
		} finally {
			con.closeConn();
		}
		return listCompany;
	}

	/**
	 * ======== REQUETE SQL : RECUPERE COMPANY PAR PAGE ========
	 */
	@Override
	public ArrayList<Company> getPage(Page<Company> page) {
		Company company = null;
		ArrayList<Company> listCompany = new ArrayList<Company>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement s = con.getConn().prepareStatement(QUERY_GET_PAGE_COMPANY);) {
			s.setInt(1, page.offset);
			s.setInt(2, 10);
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				company = new Company(rs.getLong("id"), rs.getString("name"));
				listCompany.add(company);
			}
		} catch (SQLException e) {

			LOGGER.error(" Erreur de la recuperation des données de Company par page : \n " + e.getMessage());
		} finally {
			con.closeConn();
		}
		return listCompany;
	}

	@Override
	public int getNbElement() {
 		return 0;
	}

	@Override
	public boolean create(Company t) {
		return false;
	}

	@Override
	public void update(Company t) {

	}

	/**
	 * ====== SUPPRIME UNE COMPANY =======
	 */
	@Override
	public void delete(Company company) {
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		try {
			con.getConn().setAutoCommit(false);
			PreparedStatement ps = con.getConn().prepareStatement(QUERY_DELETE_BY_COMPANY);
			ps.setLong(1, company.getId());
			ps.executeUpdate();
			ps = con.getConn().prepareStatement(QUERY_DELETE_COMPANY);
			ps.setLong(1, company.getId());
			ps.executeUpdate();
			con.getConn().commit();
			LOGGER.info("Suppression Company" + company.getName() + " reussi ");
			ps.close();
		} catch (SQLException e) {
			LOGGER.error(" Erreur suppression de Company : \n" + e.getMessage());
			try {
				con.getConn().rollback();
			} catch (SQLException e1) {
				LOGGER.error(" Erreur du Rollback : \n " + e1.getMessage());
			}
		} finally {
			con.closeConn();
		}
	}

	@Override
	public Optional<Company> findById(int id) {
		return null;
	}

	@Override
	public ArrayList<Company> getSearch(Page<Company> company, String name) {
		return null;
	}

	@Override
	public int getNbElementSearch(String name) {
		return 0;
	}

	@Override
	public ArrayList<Company> getPageSort(Page<Computer> page,String critere, Boolean order) {
		// TODO Auto-generated method stub
		return null;
	}

}
