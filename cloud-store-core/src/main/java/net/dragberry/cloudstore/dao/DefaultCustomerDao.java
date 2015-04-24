package net.dragberry.cloudstore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.domain.Customer;
import net.dragberry.cloudstore.domain.Customer_;

@Stateless
public class DefaultCustomerDao extends AbstractDao<Customer> implements CustomerDao {
	
	private final static Log LOGGER = LogFactory.getLog(DefaultCustomerDao.class);
	
	public Customer fetchByCustomerName(String customerName) {
		LOGGER.info("Entering into DefaultCustomerDao.fetchByCustomerName...");
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> categoryRoot = cq.from(Customer.class);
		Predicate where = cb.equal(categoryRoot.get(Customer_.customerName), customerName);
		cq.where(where);
		List<Customer> resultList = getEntityManager().createQuery(cq).getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

}
