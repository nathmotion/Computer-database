package com.excilys.xdurbec.formation.computerdatabase.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.excilys.xdurbec.formation.computerdatabase.model.Company;

/**
 * Crude gestion of Company.
 * @author excilys
 *
 */

@Repository
public class CompanyDAO extends EntityDAO implements EntityDAOComportment<Company> {

	@PersistenceContext
	private EntityManager em;
	private CriteriaBuilder cb;



	@PostConstruct
	public void init() {
		this.cb = em.getCriteriaBuilder();
	}


	@Override
	public List<Company>  getAll()  throws  ExceptionDAO {
		try {
			CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
			Root<Company> model = criteriaQuery.from(Company.class);
			TypedQuery<Company> query = em.createQuery(criteriaQuery);
			return query.getResultList();
		} catch (DataAccessException e) {
			log.error(e);
			throw new ExceptionDAO(ExceptionDAO.GET_ALL_ERROR);
		}
	}


	public Boolean doesExist(int id) {
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Company> model = criteriaQuery.from(Company.class);
		criteriaQuery.select(cb.count(model));
		criteriaQuery.where(cb.equal(model.get("id"), id));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return 0 < query.getSingleResult().intValue();
	}


	public Company getById(int id) throws ExceptionDAO {
		try {
			CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
			Root<Company> model = criteriaQuery.from(Company.class);
			criteriaQuery.where(cb.equal(model.get("id"), id));
			TypedQuery<Company> query = em.createQuery(criteriaQuery);
			return query.getSingleResult();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			throw new ExceptionDAO(ExceptionDAO.ID_COMPUTER_ERROR);
		}
	}


	public void delete(int id) {
		CriteriaDelete<Company> delete = cb.createCriteriaDelete(Company.class);
		Root<Company> model = delete.from(Company.class);
		delete.where(cb.equal(model.get("id"), id));
		em.createQuery(delete).executeUpdate();
		

	}

}
