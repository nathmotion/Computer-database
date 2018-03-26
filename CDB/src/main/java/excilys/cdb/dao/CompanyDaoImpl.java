package main.java.excilys.cdb.dao;

import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_DELETE_BY_COMPANY;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_DELETE_COMPANY;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_ALL_COMPANY;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_GET_PAGE_COMPANY;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

@Repository
@Qualifier("InterfaceDao")
public class CompanyDaoImpl implements InterfaceDao<Company> {

	
	private final JdbcTemplate jdbcTemplate;
	// final static Logger LOGGER = LogManager.getLogger(CompanyDaoImpl.class);
	
	public CompanyDaoImpl(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate=jdbcTemplate;
	}
	
	/**
	 * ======== REQUETE SQL : RECUPERE LA LISTE DES COMPAGNIES ========
	 */
	@Override
	public ArrayList<Company> getAll() {

		RowMapper<Company> beanPropertyRowMapper = (rs, rowNum) -> new Company(rs.getLong("id"), rs.getString("name"));
		try {
		List<Company> listeCompany = jdbcTemplate.query(QUERY_GET_ALL_COMPANY, beanPropertyRowMapper);
		return (ArrayList<Company>) listeCompany;
		} catch(DataAccessException e) {
			System.out.println(" Requet erreur sur recuperation de tout la liste des company !");
		}
		return null;
		
	}

	/**
	 * ======== REQUETE SQL : RECUPERE COMPANY PAR PAGE ========
	 */
	@Override
	public ArrayList<Company> getPage(Page<Company> page) {

		RowMapper<Company> beanPropertyRowMapper = (rs, rowNum) -> new Company(rs.getLong("id"), rs.getString("name"));
		List<Company> listeCompany = jdbcTemplate.query(QUERY_GET_PAGE_COMPANY, beanPropertyRowMapper);
		return (ArrayList<Company>) listeCompany;
	}

	@Override
	public int getNbElement() {
		return 0;
	}

	@Override
	public void create(Company t) {
	}

	@Override
	public void update(Company t) {

	}

	/**
	 * ====== SUPPRIME UNE COMPANY =======
	 */
	@Override
	public void delete(Company company) {

		jdbcTemplate.update(QUERY_DELETE_BY_COMPANY, company.getName());
		jdbcTemplate.update(QUERY_DELETE_COMPANY, company.getId());
		// LOGGER.info("Suppression Company" + company.getName() + " reussi ");
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
	public ArrayList<Company> getPageSort(Page<Computer> page, String critere, Boolean order) {
		return null;
	}

}
