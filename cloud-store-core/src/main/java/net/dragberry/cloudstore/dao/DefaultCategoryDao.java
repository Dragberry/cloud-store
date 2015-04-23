package net.dragberry.cloudstore.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.dao.util.EntityServiceUtils;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Category_;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;
import net.dragberry.cloudstore.query.CategoryListQuery;
import net.dragberry.cloudstore.result.CategoryList;

@Stateless
public class DefaultCategoryDao extends AbstractDao<Category> implements CategoryDao {
	
	private final static Log LOGGER = LogFactory.getLog(DefaultCategoryDao.class);
	
	@Override
	public Category fetchCategoryByTitle(String title) {
		LOGGER.info("Entering into DefaultCategoryDao.fetchCategoryByTitle...");
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Predicate where = cb.equal(categoryRoot.get(Category_.title), title);
		cq.where(where);
		List<Category> resultList = getEntityManager().createQuery(cq).getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
	public Category fetchCategoryByCode(String code) {
		LOGGER.info("Entering into DefaultCategoryDao.fetchCategoryByCode...");
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Predicate where = cb.equal(categoryRoot.get(Category_.code), code);
		cq.where(where);
		cb.equal(categoryRoot.get(Category_.code), code);
		List<Category> resultList = getEntityManager().createQuery(cq).getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
	@Override
	public CategoryList fetchCategories(CategoryListQuery categoryQuery) {
		LOGGER.info("Entering into DefaultCategoryDao.fetchCategories...");
		
		CategoryList resultList = new CategoryList();
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Predicate where = null;
        if (categoryQuery.getId() != null) {
            where = cb.equal(categoryRoot.get(Category_.id), categoryQuery.getId());
        }
        where = EntityServiceUtils.addAndEqualExpression(categoryQuery.getCode(), Category_.code, where, cb, categoryRoot);
        where = EntityServiceUtils.addAndLikeExpression(categoryQuery.getTitle(), Category_.title, where, cb, categoryRoot);
		if (where != null) {
			cq.where(where);
		}
		TypedQuery<Category> query = getEntityManager().createQuery(cq);
        setPageableParams(categoryQuery, query);
		List<Category> categoryList = query.getResultList();
		resultList.setList(categoryList);
//        resultList.setCount(count);
        resultList.setPageNumber(categoryQuery.getPageNumber());
        resultList.setPageSize(categoryQuery.getPageSize());
        
        return resultList;
	}
	
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
