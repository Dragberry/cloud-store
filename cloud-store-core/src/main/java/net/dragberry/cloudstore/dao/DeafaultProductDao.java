package net.dragberry.cloudstore.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;
import net.dragberry.cloudstore.query.ProductQuery;
import net.dragberry.cloudstore.query.sort.SortItem;

@Stateless
public class DeafaultProductDao extends AbstractEntityService<Product> implements ProductDao {
    
    private static final String PERCENT_QUOTE = "%";
	
	@Override
	public Product saveProduct(Product product) {
		return saveProduct(product);
	}

    @Override
    public List<Product> fetchProducts(ProductQuery productQuery) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        
        Predicate where = null;
        if (productQuery.getId() != null) {
            where = cb.equal(root.get(Product_.id), productQuery.getId());
        }
        where = addLikeExpression(productQuery.getTitle(), Product_.title, where, cb, root);
        where = addLikeExpression(productQuery.getDescription(), Product_.description, where, cb, root);
        where = addLikeExpression(productQuery.getFullDescription(), Product_.fullDescription, where, cb, root);
        
        if (!productQuery.getCategoryIdList().isEmpty()) {
            Join<Product, Category> joinProductCategory =  root.join("categories", JoinType.LEFT);
            Path<Long> categoryIds = joinProductCategory.get("id");
            In<Long> categories = cb.in(categoryIds);
            for (Long categoryId : productQuery.getCategoryIdList()) {
                categories.value(categoryId);
            }
            if (where != null) {
                where = cb.and(where, categories);
            } else {
                where = categories;
            }
        }
        if (where != null) {
            cq.where(where);
        }
        List<Order> orders = new ArrayList<Order>();
        for (SortItem sortItem : productQuery.getSortList()) {
            switch (sortItem.getDirection()) {
            case ASCENDING:
                orders.add(cb.asc(root.get(sortItem.getAttribute())));
                break;
            case DESCENDING:
                orders.add(cb.desc(root.get(sortItem.getAttribute())));
                break;
            }
        }
        cq.distinct(true);
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /**
     * Add like expression with 'AND' logical operator to WHERE clause.
     * 
     * @param field
     * @param attribute
     * @param where
     * @param cb
     * @param root
     * @return
     */
    private Predicate addLikeExpression(String field, SingularAttribute<Product, String> attribute, Predicate where, CriteriaBuilder cb, Root<Product> root) {
        if (StringUtils.isNotBlank(field)) {
            if (where != null) {
                where = cb.and(where, cb.like(root.get(attribute), wrap(field)));
            } else {
                where = cb.like(root.get(attribute), wrap(field));
            }
        }
        return where;
    }
    
    /**
     * Wrap string in percent quotes: %string%
     * @param str
     * @return
     */
    private String wrap(String str) {
        return new StringBuilder(PERCENT_QUOTE).append(str).append(PERCENT_QUOTE).toString();
    }

}
