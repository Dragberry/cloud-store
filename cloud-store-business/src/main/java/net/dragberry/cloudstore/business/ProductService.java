package net.dragberry.cloudstore.business;

import javax.ejb.Stateless;
import javax.inject.Inject;
import net.dragberry.cloudstore.dao.ProductDao;
import net.dragberry.cloudstore.domain.Product;

@Stateless
public class ProductService implements ProductServiceLocal {
	
	@Inject
	private ProductDao productEntityService;
	

	@Override
	public Product createProduct(Product product) {
		productEntityService.saveProduct(product);
		return null;
	}

}
