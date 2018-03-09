package main.java.excilys.cdb.dao;

import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.excilys.cdb.connectionmanager.SingletonConn;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

public enum DaoComputer implements InterfaceDao<Computer> {
	INSTANCE;

	public final static Logger LOGGER = LogManager.getLogger(DaoComputer.class);

	/**
	 * ====== REQUETES SQL : RECUPERE LA LISTE DES COMPUTER =======
	 */
	@Override
	public ArrayList<Computer> getAll() {
		Computer iComputer = null;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();

		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (Statement stat = con.getConn().createStatement()) {
			ResultSet rs = stat.executeQuery(QUERY_GET_ALL_COMPUTER);

			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), company);
				System.out.println("computer " + iComputer.toString());
				listComputer.add(iComputer);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET ALL : " + e.getMessage());
		}
		return listComputer;
	}

	/**
	 * ======= RECUPERATION D'UNE PAGE DES COMPUTER =======
	 */
	@Override
	public ArrayList<Computer> getPage(int offset, int limitPage) {
		Computer iComputer = null;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_GET_PAGE_COMPUTER)) {
			stat.setInt(1, offset);
			stat.setInt(2, limitPage);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"), company);
				listComputer.add(iComputer);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET Page : " + e.getMessage());
		}
		return listComputer;
	}

	/**
	 * ====== RECUPERE LE NOMBRE D'ELEMENT DANS LA TABLE COMPUTER ========
	 * 
	 * @return
	 */
	public int getNbElement() {
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		int nbComputer = 0;

		try (Statement stat = con.getConn().createStatement()) {
			ResultSet rs = stat.executeQuery(QUERY_NB_ELEMENT_COMPUTER);

			while (rs.next()) {
				nbComputer = rs.getInt("nbcomputer");
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET nbComputer : " + e.getMessage());
		}
		return nbComputer;
	}

	/**
	 * ======= REQUETES SQL AJOUTE UN ORDINATEUR PASSER PAR PARAMETRE ========
	 * 
	 * @param computer
	 * @return
	 */
	public boolean create(Computer computer) {

		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement ps = con.getConn().prepareStatement(QUERY_CREATE_COMPUTER)) {
			ps.setString(1, computer.getName());

			if (computer.getIntroduced() != null) {

				ps.setTimestamp(2, computer.getIntroduced());
			} else {
				ps.setTimestamp(2, null);
			}
			if (computer.getDiscontinued() != null) {
				ps.setTimestamp(3, computer.getDiscontinued());
			} else {
				ps.setTimestamp(3, null);
			}
			if (!computer.getCompany().equals(null) && computer.getCompany().getId() != 0
					&& computer.getCompany().getId() != null) {
				ps.setLong(4, computer.getCompany().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.executeUpdate();
			con.closeConn();
			LOGGER.info("Ajout Computer " + computer.getName() + " reussi !");
			return true;
		} catch (SQLException e) {
			LOGGER.error(" error CREATE computer  : " + e.getMessage());
		}
		return false;
	}

	/**
	 * ======== REQUETES SQL MIS A JOUR UN ORDINATEUR PASSER PAR PARAMETRE =========
	 * 
	 * @param computer
	 * @return
	 */
	public void update(Computer computer) {
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		try (PreparedStatement ps = con.getConn().prepareStatement(QUERY_UPDATE_COMPUTER)) {
			ps.setString(1, computer.getName());

			if (computer.getIntroduced() != null) {

				ps.setTimestamp(2, computer.getIntroduced());
			} else {
				ps.setTimestamp(2, null);
			}
			if (computer.getDiscontinued() != null) {
				ps.setTimestamp(3, computer.getDiscontinued());
			} else {
				ps.setTimestamp(3, null);
			}
			if (!computer.getCompany().equals(null) && computer.getCompany().getId() != 0
					&& computer.getCompany().getId() != null) {
				ps.setLong(4, computer.getCompany().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setLong(5, computer.getId());
			ps.executeUpdate();
			con.closeConn();
			LOGGER.info("Update Computer " + computer.getName() + "[" + computer.getId() + "] Reussi");
		} catch (SQLException e) {
			LOGGER.error(" error requete Update  : " + e.getMessage());
		}
	}

	/**
	 * ====== REQUETES SQL SUPPRIMER UN ORDINATEUR PASSER PAR PARAMETRE ===========
	 * 
	 * @param computer
	 * @return
	 */
	public void delete(Computer computer) {

		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement ps = con.getConn().prepareStatement(QUERY_DELETE_COMPUTER)) {
			ps.setLong(1, computer.getId());
			ps.executeUpdate();
			con.closeConn();
			LOGGER.info("Suppression Computer " + computer.getName() + " Reussi");
		} catch (SQLException e) {
			LOGGER.error(" error  DELETE  Computer : " + e.getMessage());
		}
	}

	/**
	 * ===== REQUETES SQL RECUPERE UN ORDINATEUR PASSER PAR PARAMETRE =========
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id) {
		Computer iComputer = null;
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_BY_ID_COMPUTER)) {
			stat.setInt(1, id);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("computer.id"), rs.getString("computer.name"),
						rs.getTimestamp("introduced"), rs.getTimestamp("discontinued"), company);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET by ID : " + e.getMessage());
		}
		Optional<Computer> op = Optional.ofNullable(iComputer);
		return op;
	}

	/**
	 * ======== RECUPER LA PAGE SUIVANT LA RECHERCHE PAR UNE CHAINE DE CHARACTER ========
	 */
	@Override
	public ArrayList<Computer> getSearch(int offset, int limitPage, String name) {
		Computer iComputer = null;
		ArrayList<Computer> listComputer = new ArrayList<Computer>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_BY_NAME)) {
			stat.setString(1, name + '%');
			stat.setString(2, name + '%');
			stat.setInt(3, offset);
			stat.setInt(4, limitPage);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), company);
				listComputer.add(iComputer);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error GET Page Computer : " + e.getMessage());
		}
		return listComputer;
	}

	/**
	 * ======= NB D' ELEMENTS DE LA RECHERCHE ===========
	 */
	@Override
	public int getNbElementSearch(String name) {
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		int nbComputer = 0;
		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_NB_ELEMENT_BY_NAME)) {
			stat.setString(1, name + '%');
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				nbComputer = rs.getInt("nbcomputer");
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error GET nbComputer : " + e.getMessage());
		}
		return nbComputer;
	}
	
}