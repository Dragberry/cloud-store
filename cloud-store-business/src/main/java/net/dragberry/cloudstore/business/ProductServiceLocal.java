package net.dragberry.cloudstore.business;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;
import net.dragberry.cloudstore.result.ProductList;

@Local
public interface ProductServiceLocal {
	
	ProductList fetchProducts(ProductListQuery query);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	Product createProduct(Product product);

}
