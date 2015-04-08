package net.dragberry.cloudstore.dao;
import java.util.List;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.domain.Category;

public interface CategoryDao {
	
	List<Category> fetchCategories();
	
	List<Category> fetchCategoriesForProduct(Long productId);
	
	TreeNode<Category> getCategoryTree();

}
