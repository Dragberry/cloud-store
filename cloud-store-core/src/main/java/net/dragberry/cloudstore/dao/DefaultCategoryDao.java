package net.dragberry.cloudstore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import net.dragberry.cloudstore.domain.Category;

@Stateless
public class DefaultCategoryDao extends AbstractEntityService<Category> implements CategoryDao {

	@Override
	public List<Category> fetchCategories() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		cq.from(Category.class);
		return getEntityManager().createQuery(cq).getResultList();
	}

}
