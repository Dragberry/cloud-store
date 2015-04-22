package net.dragberry.cloudstore.dao;

import net.dragberry.cloudstore.query.ProductListQuery;
import net.dragberry.cloudstore.result.ProductList;

public interface ProductDao {
	
	ProductList fetchProducts(ProductListQuery query);
	
}
