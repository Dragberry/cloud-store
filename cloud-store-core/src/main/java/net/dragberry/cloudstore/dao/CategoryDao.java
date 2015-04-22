package net.dragberry.cloudstore.dao;
import java.util.List;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.query.CategoryListQuery;
import net.dragberry.cloudstore.result.CategoryList;

public interface CategoryDao {
	
	Category fetchSingleCategory(CategoryListQuery categoryQuery);
	
	CategoryList fetchCategories(CategoryListQuery categoryQuery);
	
	List<Category> fetchCategories();
	
	TreeNode<Category> fetchCategoriesForProduct(Long productId);
	
	TreeNode<Category> getCategoryTree();

}
