package net.dragberry.cloudstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.dragberry.cloudstore.domain.AbstractEntity;

public abstract class AbstractEntityService<T extends AbstractEntity> {

    @PersistenceContext(unitName = "cloudstore")
    private EntityManager entityManager;
    
    
    protected T createNewEntity(T entity) {
        this.entityManager.persist(entity);
        return entity;
    }
    
    protected T findOne(Class<T> type, Long id) {
        return getEntityManager().find(type, id);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
