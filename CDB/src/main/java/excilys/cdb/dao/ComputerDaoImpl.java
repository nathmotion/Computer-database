package main.java.excilys.cdb.dao;

import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_BY_ID_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_BY_NAME;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_CREATE_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_DELETE_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_ALL_COMPANY;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_ALL_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_PAGE_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_NB_ELEMENT_BY_NAME;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_NB_ELEMENT_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_ORDER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_UPDATE_COMPUTER;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.connectionmanager.SingletonConn;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

@Repository
@Qualifier("InterfaceDao")
public class ComputerDaoImpl implements InterfaceDao<Computer> {

	public final static Logger LOGGER = LogManager.getLogger(DaoComputer.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * ====== REQUETES SQL : RECUPERE LA LISTE DES COMPUTER =======
	 */
	@Override
	public ArrayList<Computer> getAll() {

		RowMapper<Computer> beanPropertyRowMapper = (rs, rowNum) -> {
			Company company = new Company(rs.getLong("company_id"), rs.getString("company.name"));
			return new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
					rs.getTimestamp("discontinued"), company);
		};
		List<Computer> listeComputer = jdbcTemplate.query(QUERY_GET_ALL_COMPUTER, beanPropertyRowMapper);
		return (ArrayList<Computer>) listeComputer;
	}

	/**
	 * ======= RECUPERATION D'UNE PAGE DES COMPUTER =======
	 */
	@Override
	public ArrayList<Computer> getPage(Page<Computer> page) {
		Computer iComputer = null;
		ArrayList<Computer> list = new ArrayList<Computer>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_GET_PAGE_COMPUTER)) {
			stat.setInt(1, page.offset);
			stat.setInt(2, page.limit);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), company);
				list.add(iComputer);
			}
			con.closeConn();
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET Page : " + e.getMessage());
		}
		return list;
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
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET nbComputer : " + e.getMessage());
		} finally {
			con.closeConn();
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
			LOGGER.info("Ajout Computer " + computer.getName() + " reussi !");
			return true;
		} catch (SQLException e) {
			LOGGER.error(" error CREATE computer  : " + e.getMessage());
		} finally {
			con.closeConn();
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
			LOGGER.info("Update Computer " + computer.getName() + "[" + computer.getId() + "] Reussi");
		} catch (SQLException e) {
			LOGGER.error(" error requete Update  : " + e.getMessage());
		} finally {
			con.closeConn();
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
			LOGGER.info("Suppression Computer " + computer.getName() + " Reussi");
		} catch (SQLException e) {
			LOGGER.error(" error  DELETE  Computer : " + e.getMessage());
		} finally {
			con.closeConn();
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
		} catch (SQLException e) {
			LOGGER.error(" error requetes GET by ID : " + e.getMessage());
		} finally {
			con.closeConn();
		}
		Optional<Computer> op = Optional.ofNullable(iComputer);
		return op;
	}

	/**
	 * ======== RECUPER LA PAGE SUIVANT LA RECHERCHE PAR UNE CHAINE DE CHARACTER
	 * ========
	 */
	@Override
	public ArrayList<Computer> getSearch(Page<Computer> page, String name) {
		Computer iComputer = null;
		ArrayList<Computer> list = new ArrayList<Computer>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();

		try (PreparedStatement stat = con.getConn().prepareStatement(QUERY_BY_NAME)) {
			stat.setString(1, name + '%');
			stat.setString(2, name + '%');
			stat.setInt(3, page.offset);
			stat.setInt(4, page.limit);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), company);
				list.add(iComputer);
			}
		} catch (SQLException e) {
			LOGGER.error(" error GET Page Computer : " + e.getMessage());
		} finally {
			con.closeConn();
		}
		return list;
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
			stat.setString(2, name + '%');
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				nbComputer = rs.getInt("nbcomputer");
			}
		} catch (SQLException e) {
			LOGGER.error(" error GET nbComputer : " + e.getMessage());
		} finally {
			con.closeConn();
		}
		return nbComputer;
	}

	public ArrayList<Computer> getPageSort(Page<Computer> page, String critere, Boolean orderAsc) {

		Computer iComputer = null;
		ArrayList<Computer> list = new ArrayList<Computer>();
		SingletonConn con = SingletonConn.INSTANCE;
		con.initConn();
		String stringOrder = "DESC";
		if (orderAsc) {
			stringOrder = "ASC";
		}

		try (PreparedStatement stat = con.getConn()
				.prepareStatement(String.format(QUERY_ORDER, critere, stringOrder))) {
			stat.setInt(1, page.offset);
			stat.setInt(2, page.limit);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setName(rs.getString("company.name"));
				company.setId(rs.getLong("company_id"));
				iComputer = new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), company);
				list.add(iComputer);
			}
		} catch (SQLException e) {
			LOGGER.error(" error requetes Page triee : " + e.getMessage());
		} finally {
			con.closeConn();
		}
		return list;
	}

}
