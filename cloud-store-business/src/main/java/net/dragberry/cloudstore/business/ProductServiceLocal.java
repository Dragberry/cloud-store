package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductQuery;

@Local
public interface ProductServiceLocal {
	
	Product createProduct(Product product);
	
	List<Product> fetchProducts(ProductQuery query);

}
