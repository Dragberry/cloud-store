package net.dragberry.cloudstore.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Category_;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;
import net.dragberry.cloudstore.query.ProductQuery;
import net.dragberry.cloudstore.query.sort.SortItem;

@Stateless
public class DeafaultProductDao extends AbstractEntityService<Product> implements ProductDao {
    
    private final static Log LOGGER = LogFactory.getLog(DefaultCategoryDao.class);
    
    private static final String PERCENT_QUOTE = "%";
	
	@Override
	public Product saveProduct(Product product) {
		return saveProduct(product);
	}

    @Override
    public List<Product> fetchProducts(ProductQuery productQuery) {
        LOGGER.info("Entering into DefaultProductDao.fetchProducts...");
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
        where = addRangeExpression(productQuery.getMinCost(), productQuery.getMaxCost(), Product_.cost, where, cb, root);
        
        Join<Product, Category> joinProductCategory = (Join<Product, Category>) root.fetch(Product_.categories, JoinType.LEFT);;
        if (!productQuery.getCategoryIdList().isEmpty()) {
            In<Long> categories = cb.in(joinProductCategory.get(Category_.id));
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
        
        Map<Class<?>, From<?, ?>>  sortMap = new HashMap<Class<?>, From<?,?>>();
        sortMap.put(Product.class, root);
        sortMap.put(Category.class, joinProductCategory);
        cq.orderBy(getOrders(productQuery.getSortList(), sortMap, cb));
        
        cq.distinct(true);
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Get the list of orders based on set of the sortList
     * @param sortList
     * @param cb
     * @param sortMap
     */
	private List<Order> getOrders(Set<SortItem> sortList, Map<Class<?>, From<?, ?>> sortMap, CriteriaBuilder cb) {
		List<Order> orders = new ArrayList<Order>();
		for (SortItem sortItem : sortList) {
            switch (sortItem.getDirection()) {
            case ASCENDING:
                orders.add(cb.asc(sortMap.get(sortItem.getType()).get(sortItem.getField())));
                break;
            case DESCENDING:
                orders.add(cb.desc(sortMap.get(sortItem.getType()).get(sortItem.getField())));
                break;
            }
        }
		return orders;
	}
    
    /**
     * Add range expression with 'AND' logical operator to WHERE clause.
     * Using with BigDecimal values.
     * 
     * @param min
     * @param max
     * @param attribute
     * @param where
     * @param cb
     * @param root
     * @return
     */
    private Predicate addRangeExpression(BigDecimal min, BigDecimal max, SingularAttribute<Product, BigDecimal> attribute, Predicate where, CriteriaBuilder cb, Root<Product> root) {
    	if (min != null) {
    		if (where != null) {
                where = cb.and(where, cb.ge(root.get(attribute), min));
            } else {
                where = cb.ge(root.get(attribute), min);
            }
    	}
    	if (max != null) {
    		if (where != null) {
                where = cb.and(where, cb.le(root.get(attribute), max));
            } else {
                where = cb.le(root.get(attribute), max);
            }
    	}
    	return where;
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
