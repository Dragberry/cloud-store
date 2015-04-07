package net.dragberry.cloudstore.business;

import java.util.Iterator;
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
	public List<Category> buildCategoryTree(List<Category> categoryList) {
		TreeNode root = new TreeNode();
		Iterator<Category> iterator = categoryList.iterator();
		while (iterator.hasNext()) {
			Category category = iterator.next();
			if (!category.hasParent()) {
				root.add(new TreeNode(category));
			}
			iterator.remove();
		}

		for (TreeNode parentNode : root.children()) {
			
		}
		return categoryList;
	}

}
