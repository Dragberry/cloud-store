package net.dragberry.cloudstore.dao;

import java.util.List;

import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

public interface ProductDao {
	
	Product saveProduct(Product product);
	
	List<Product> fetchProducts(ProductListQuery query);
	
}
