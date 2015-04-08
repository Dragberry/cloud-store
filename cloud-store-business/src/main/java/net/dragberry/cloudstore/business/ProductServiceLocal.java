package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

@Local
public interface ProductServiceLocal {
	
	List<Product> fetchProducts(ProductListQuery query);

}
