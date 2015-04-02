package net.dragberry.cloudstore.business;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Product;

@Local
public interface ProductServiceLocal {
	
	Product createProduct(Product product);

}
