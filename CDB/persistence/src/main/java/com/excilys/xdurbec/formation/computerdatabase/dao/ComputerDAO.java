package com.excilys.xdurbec.formation.computerdatabase.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.excilys.xdurbec.formation.computerdatabase.model.Computer;
import com.excilys.xdurbec.formation.computerdatabase.model.ComputerAttributes;




@Repository
public class ComputerDAO extends EntityDAO implements EntityDAOComportment<Computer> { 

	@PersistenceContext
	private EntityManager em;
	private CriteriaBuilder cb;

	@PostConstruct
	public void init() {
		this.cb = em.getCriteriaBuilder();
	}


	public Computer getById(int id) throws ExceptionDAO {
		try {
			CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
			Root<Computer> model = criteriaQuery.from(Computer.class);
			criteriaQuery.where(cb.equal(model.get("id"), id));
			TypedQuery<Computer> query = em.createQuery(criteriaQuery);
			return query.getSingleResult();
		} catch (DataAccessException e) {
			log.error(e);
			throw new ExceptionDAO(ExceptionDAO.ID_COMPUTER_ERROR);
		}
	}

	@Override
	public List<Computer> getAll() throws ExceptionDAO {
		try {
			CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
			Root<Computer> model = criteriaQuery.from(Computer.class);
			TypedQuery<Computer> query = em.createQuery(criteriaQuery);
			return query.getResultList();
		}  catch (DataAccessException e) {
			log.error(e);
			throw new ExceptionDAO(ExceptionDAO.ID_COMPUTER_ERROR);
		}
	}

	public void create(Computer computer) throws ExceptionDAO {
		try {
			if (computer.getCompany() != null && computer.getCompany().getId() == 0) {
				computer.setCompany(null);
			}
			em.persist(computer);
		} catch (DataAccessException e) {
			log.error(e);
			throw new ExceptionDAO(ExceptionDAO.CREATE_ERROR);
		}
	}

	public void update(Computer computer) throws ExceptionDAO {
		try {			
			if (computer.getCompany() != null && computer.getCompany().getId() == 0) {
				computer.setCompany(null);
			}
			System.out.println("Computer :" + computer);
			CriteriaUpdate<Computer> update = cb.createCriteriaUpdate(Computer.class);
			Root<Computer> model = update.from(Computer.class);
			update.set(ComputerAttributes.NAME.sqlName, computer.getName());
			update.set(ComputerAttributes.INTRODUCED.sqlName, computer.getIntroduced());
			update.set(ComputerAttributes.DISCONTINUED.sqlName, computer.getDiscontinued());
			update.set(ComputerAttributes.COMPANY_NAME.sqlName, computer.getCompany());
			update.where(cb.equal(model.get(ComputerAttributes.ID.sqlName), computer.getId()));
			em.createQuery(update).executeUpdate();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			throw new ExceptionDAO(ExceptionDAO.CREATE_ERROR);
		}

	}


	public void deleteById(int id) throws ExceptionDAO {
		try {
			CriteriaDelete<Computer> delete = cb.createCriteriaDelete(Computer.class);
			Root<Computer> model = delete.from(Computer.class);
			delete.where(cb.equal(model.get(ComputerAttributes.ID.sqlName), id));
			em.createQuery(delete).executeUpdate();
		} catch (DataAccessException e) {
			log.error(e);
			throw new ExceptionDAO(ExceptionDAO.DELETE_ERROR);
		}
	}

	public List<Computer> getAllPage(int pageNumber, int nbEntityPerPage, String filter, ComputerAttributes orderBy, Boolean ascendingOrder) {
		CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
		Root<Computer> model = criteriaQuery.from(Computer.class);
		if (ascendingOrder) {
			if (orderBy.equals(ComputerAttributes.COMPANY_NAME)) {
				criteriaQuery.orderBy(cb.asc(model.get(orderBy.sqlName).get(ConstantStringDAO.NAME_OF_COMPANY)));
			} else {
				criteriaQuery.orderBy(cb.asc(model.get(orderBy.sqlName)));
			}
		} else {				
			if (orderBy.equals(ComputerAttributes.COMPANY_NAME)) {
				criteriaQuery.orderBy(cb.desc(model.get(orderBy.sqlName).get(ConstantStringDAO.NAME_OF_COMPANY)));
			} else {
				criteriaQuery.orderBy(cb.desc(model.get(orderBy.sqlName)));
			}
		}
		criteriaQuery.where(cb.like(model.get(ComputerAttributes.NAME.sqlName), "%" + filter + "%"));
		TypedQuery<Computer> query = em.createQuery(criteriaQuery);
		int firstPage = (pageNumber - 1) * nbEntityPerPage;
		firstPage = firstPage < 0 ? 0 : firstPage;
		query.setFirstResult(firstPage);
		query.setMaxResults(nbEntityPerPage);
		return query.getResultList();

	}

	public int getComputerNumber(String filter) {
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Computer> model = criteriaQuery.from(Computer.class);
		criteriaQuery.select(cb.count(model));
		criteriaQuery.where(cb.like(model.get(ComputerAttributes.NAME.sqlName), "%" + filter + "%"));
		TypedQuery<Long> query2 = em.createQuery(criteriaQuery);
		return query2.getSingleResult().intValue();

	}


	public void deleteByCompany(int companyId) throws ExceptionDAO {
		try {
			CriteriaDelete<Computer> delete = cb.createCriteriaDelete(Computer.class);
			Root<Computer> model = delete.from(Computer.class);
			delete.where(cb.equal(model.get(ComputerAttributes.COMPANY_NAME.sqlName).get(ConstantStringDAO.COMPANY_ID), companyId));
			em.createQuery(delete).executeUpdate();
		} catch (DataAccessException e) {
			log.error(e);
			throw new ExceptionDAO(ExceptionDAO.DELETE_ERROR);
		}
	}

}
