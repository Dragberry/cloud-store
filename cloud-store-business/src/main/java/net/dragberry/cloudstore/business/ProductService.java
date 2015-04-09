package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.dao.CategoryDao;
import net.dragberry.cloudstore.dao.ProductDao;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

@Stateless
public class ProductService implements ProductServiceLocal {
    
	@Inject
	private ProductDao defaultProductDao;
	@Inject
	private CategoryDao defaultCategoryDao;
	
    @Override
    public List<Product> fetchProducts(ProductListQuery query) {
    	List<Product> products = defaultProductDao.fetchProducts(query);
    	for (Product product : products) {
    		TreeNode<Category> categoryTree = defaultCategoryDao.fetchCategoriesForProduct(product.getId());
    		product.setCategoryTree(categoryTree);
    	}
        return products;
    }

}
