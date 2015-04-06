package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.dragberry.cloudstore.dao.ProductDao;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductQuery;

@Stateless
public class ProductService implements ProductServiceLocal {
    
	@Inject
	private ProductDao defaultProductDao;
	

	@Override
	public Product createProduct(Product product) {
	    defaultProductDao.saveProduct(product);
		return product;
	}


    @Override
    public List<Product> fetchProducts(ProductQuery query) {
        return defaultProductDao.fetchProducts(query);
    }

}
