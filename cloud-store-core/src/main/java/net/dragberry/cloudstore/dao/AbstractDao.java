package net.dragberry.cloudstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.dragberry.cloudstore.domain.AbstractEntity;

public abstract class AbstractDao<T extends AbstractEntity> {

    @PersistenceContext(unitName = "cloudstore")
    private EntityManager entityManager;
    
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
