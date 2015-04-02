package net.dragberry.cloudstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.dragberry.cloudstore.domain.Product;

public class ProductEntityService implements ProductDao {
	
	@PersistenceContext(unitName = "cloudstore")
	private EntityManager entityManager;

	@Override
	public Product saveProduct(Product product) {
		entityManager.persist(product);
		return null;
	}

}
