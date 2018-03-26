package main.java.excilys.cdb.dao;

import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_BY_ID_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_BY_NAME;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_CREATE_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_DELETE_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_ALL_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_PAGE_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_NB_ELEMENT_BY_NAME;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_NB_ELEMENT_COMPUTER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_ORDER;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_UPDATE_COMPUTER;

import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

@Repository
@Qualifier("InterfaceDao")
public class ComputerDaoImpl implements InterfaceDao<Computer> {

	// public final static Logger LOGGER =
	// LogManager.getLogger(ComputerDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Computer> myRowMapper = (rs, rowNum) -> {
		Company company = new Company(rs.getLong("company_id"), rs.getString("company.name"));
		return new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
				rs.getTimestamp("discontinued"), company);
	};

	/**
	 * ====== REQUETES SQL : RECUPERE LA LISTE DES COMPUTER =======
	 */
	@Override
	public List<Computer> getAll() {

		List<Computer> listeComputer = jdbcTemplate.query(QUERY_GET_ALL_COMPUTER, myRowMapper);
		return listeComputer;
	}

	/**
	 * ======= RECUPERATION D'UNE PAGE DES COMPUTER =======
	 */
	@Override
	public List<Computer> getPage(Page<Computer> page) {

		List<Computer> listeComputer = jdbcTemplate.query(QUERY_GET_PAGE_COMPUTER, myRowMapper, page.offset,
				page.limit);

		return listeComputer;
	}

	/**
	 * ====== RECUPERE LE NOMBRE D'ELEMENT DANS LA TABLE COMPUTER ========
	 * 
	 * @return
	 */
	public int getNbElement() {
		return jdbcTemplate.queryForObject(QUERY_NB_ELEMENT_COMPUTER, Integer.class);
	}

	/**
	 * ======= REQUETES SQL AJOUTE UN ORDINATEUR PASSER PAR PARAMETRE ========
	 * 
	 * @param computer
	 * @return
	 */
	public void create(Computer computer) {
		Timestamp introduced, discontinued;
		long companyId;
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced();
		} else {
			introduced = null;
		}

		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued();
		} else {
			discontinued = null;
		}

		if (computer.getCompany().getId() != 0 && computer.getCompany().getId() != null) {
			companyId = computer.getCompany().getId();
			jdbcTemplate.update(QUERY_CREATE_COMPUTER, computer.getName(), introduced, discontinued, companyId);
		} else {
			jdbcTemplate.update(QUERY_CREATE_COMPUTER, computer.getName(), introduced, discontinued, null);

		}
		// LOGGER.debug(" Creation de l'ordinateur");
	}

	/**
	 * ======== REQUETES SQL MIS A JOUR UN ORDINATEUR PASSER PAR PARAMETRE =========
	 * 
	 * @param computer
	 * @return
	 */
	public void update(Computer computer) {

		Timestamp introduced, discontinued;
		long companyId;
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced();
		} else {
			introduced = null;
		}

		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued();
		} else {
			discontinued = null;
		}

		if (computer.getCompany().getId() != 0 && computer.getCompany().getId() != null) {
			companyId = computer.getCompany().getId();
		} else {
			companyId = 0;
		}
		jdbcTemplate.update(QUERY_UPDATE_COMPUTER, computer.getName(), introduced, discontinued, companyId);
		// LOGGER.debug(" Update de l'ordinateur");
	}

	/**
	 * ====== REQUETES SQL SUPPRIMER UN ORDINATEUR PASSER PAR PARAMETRE ===========
	 * 
	 * @param computer
	 * @return
	 */
	public void delete(Computer computer) {
		
		jdbcTemplate.update(QUERY_DELETE_COMPUTER, computer.getId());
	}

	/**
	 * ===== REQUETES SQL RECUPERE UN ORDINATEUR PASSER PAR PARAMETRE =========
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_BY_ID_COMPUTER, myRowMapper, id));
	}

	/**
	 * ======== RECUPER LA PAGE SUIVANT LA RECHERCHE PAR UNE CHAINE DE CHARACTER
	 * ========
	 */
	@Override
	public List<Computer> getSearch(Page<Computer> page, String name) {
		return jdbcTemplate.query(QUERY_BY_NAME, myRowMapper, name + '%', name + '%', page.offset, page.limit);
	}

	/**
	 * ======= NB D' ELEMENTS DE LA RECHERCHE ===========
	 */
	@Override
	public int getNbElementSearch(String name) {
		return jdbcTemplate.queryForObject(QUERY_NB_ELEMENT_BY_NAME, Integer.class, name + '%', name + '%');
	}

	public List<Computer> getPageSort(Page<Computer> page, String critere, Boolean orderAsc) {

		String stringOrder = "DESC";
		if (orderAsc) {
			stringOrder = "ASC";
		}
		return jdbcTemplate.query((String.format(QUERY_ORDER, critere, stringOrder)), myRowMapper, page.offset,
				page.limit);
	}

}
