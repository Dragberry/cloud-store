package net.dragberry.cloudstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.dragberry.cloudstore.domain.AbstractEntity;
import net.dragberry.cloudstore.query.PageableQuery;

public abstract class AbstractDao<T extends AbstractEntity> {

    @PersistenceContext(unitName = "cloudstore")
    private EntityManager entityManager;
    
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    protected void setPageableParams(PageableQuery pageableQuery, TypedQuery<T> query) {
		if (pageableQuery.getPageNumber() > -1 && pageableQuery.getPageSize() > 0) {
	        query.setFirstResult((pageableQuery.getPageNumber() - 1) * pageableQuery.getPageSize());
	        query.setMaxResults(pageableQuery.getPageSize());
        }
	}

}
