package net.dragberry.cloudstore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Category_;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;

@Stateless
public class DefaultCategoryDao extends AbstractEntityService<Category> implements CategoryDao {

	@Override
	public List<Category> fetchCategories() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		cq.from(Category.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	@Override
	public List<Category> fetchCategoriesForProduct(Long productId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> root = cq.from(Category.class);
		Join<Category, Product> joinProductCategory = root.join(Category_.products);
		Predicate where = cb.equal(joinProductCategory.get(Product_.id), productId);
		cq.where(where);
		return getEntityManager().createQuery(cq).getResultList();
	}

}
