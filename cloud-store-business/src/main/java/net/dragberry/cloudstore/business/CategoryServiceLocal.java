package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Local;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.query.CategoryListQuery;

@Local
public interface CategoryServiceLocal {
	
	Category fetchSingleCategory(CategoryListQuery categoryQuery);

	List<Category> fetchCategories();
	
	TreeNode<Category> buildCategoryTree();
	
	TreeNode<Category> fetchCategoriesForProduct(Long productId);
	
}
