package net.dragberry.cloudstore.business;

import javax.ejb.Local;

import net.dragberry.cloudstore.query.ProductListQuery;
import net.dragberry.cloudstore.result.ProductList;

@Local
public interface ProductServiceLocal {
	
	ProductList fetchProducts(ProductListQuery query);

}
