package net.dragberry.cloudstore.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Category_;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;

@Stateless
public class DefaultCategoryDao extends AbstractDao<Category> implements CategoryDao {
	
	@Override
	public TreeNode<Category> getCategoryTree() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Predicate where = cb.isNull(categoryRoot.get(Category_.parentCategory));
		cq.where(where);
		List<Category> cs = getEntityManager().createQuery(cq).getResultList();
		TreeNode<Category> root = new TreeNode<Category>(new Category());
		root.getReference().setSubCategories(new HashSet<Category>(cs));
		addChildren(root, cs);
		return root;
	}
	
	private void addChildren(TreeNode<Category> root, Collection<Category> subcategories) {
		if (root.getReference().hasChildren()) {
			for (Category subcategory : subcategories) {
				TreeNode<Category> node = new TreeNode<Category>(subcategory);
				root.addChildNode(node);
				addChildren(node, subcategory.getSubCategories());
			}
		}
	}

	@Override
	public List<Category> fetchCategories() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Predicate where = cb.isNull(categoryRoot.get(Category_.parentCategory));
		cq.where(where);
		List<Category> cs = getEntityManager().createQuery(cq).getResultList();
		for (Category c : cs) {
			fetchSubCategories(c);
		}
		return cs;
	}
	
	private void fetchSubCategories(Category category) {
		if (category.hasChildren()) {
			for (Category c : category.getSubCategories()) {
				fetchSubCategories(c);
			}
		}
	}
	
	private List<Category> fetchCategories(Category parentCategory) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Predicate where = cb.equal(categoryRoot.get(Category_.parentCategory), parentCategory);
		cq.where(where);
		List<Category> cs = getEntityManager().createQuery(cq).getResultList();
		for (Category c : cs) {
			if (c.hasChildren()) {
				c.setSubCategories(new HashSet<Category>(fetchCategories(c)));
			}
		}
		return cs;
	}
	
	@Override
	public TreeNode<Category> fetchCategoriesForProduct(Long productId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> root = cq.from(Category.class);
		Join<Category, Product> joinProductCategory = root.join(Category_.products);
		Predicate where = cb.equal(joinProductCategory.get(Product_.id), productId);
		cq.where(where);
		List<Category> cs = getEntityManager().createQuery(cq).getResultList();
		TreeNode<Category> tree = new TreeNode<Category>(new Category());
		Set<Category> fullCategorySet = new HashSet<Category>();
		for (Category c : cs) {
			addParentCategoryToSet(c, fullCategorySet);
		}
		
		while (!fullCategorySet.isEmpty()) {
			Iterator<Category> it = fullCategorySet.iterator();
			while (it.hasNext()) {
				Category c = it.next();
				if (!c.hasParent()) {
					tree.addChildNode(new TreeNode<Category>(c));
					it.remove();
				}
			}
			findChildren(tree, fullCategorySet);
		}
		
		return tree;
	}
	
	private void findChildren(TreeNode<Category> parentNode, Set<Category> fullCategorySet) {
		for (TreeNode<Category> node : parentNode.getChildren()) {
			Iterator <Category> it = fullCategorySet.iterator();
			while (it.hasNext()) {
				Category c = it.next();
				if (node.getReference().equals(c.getParentCategory())) {
					node.addChildNode(new TreeNode<Category>(c));
					it.remove();
				}
			}
			if (!node.getChildren().isEmpty()) {
				findChildren(node, fullCategorySet);
			}
		}
	}

	private void addParentCategoryToSet(Category category, Set<Category> fullCategorySet) {
		fullCategorySet.add(category);
		if (category.hasParent()) {
			addParentCategoryToSet(category.getParentCategory(), fullCategorySet);
		}
		
	}

}
