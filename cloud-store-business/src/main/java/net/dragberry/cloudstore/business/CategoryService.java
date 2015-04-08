package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.dao.CategoryDao;
import net.dragberry.cloudstore.domain.Category;

@Stateless
public class CategoryService implements CategoryServiceLocal {
	
	@Inject
	private CategoryDao defaultCategoryDao;

	@Override
	public List<Category> fetchCategories() {
		return defaultCategoryDao.fetchCategories();
	}
	
	@Override
	public TreeNode<Category> buildCategoryTree() {
		return defaultCategoryDao.getCategoryTree();
	}

}
