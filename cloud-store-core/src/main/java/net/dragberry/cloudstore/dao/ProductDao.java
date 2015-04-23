package net.dragberry.cloudstore.dao;

import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;
import net.dragberry.cloudstore.result.ProductList;

public interface ProductDao {
	
	Product createProduct(Product product);
	
	ProductList fetchProducts(ProductListQuery query);
	
}
