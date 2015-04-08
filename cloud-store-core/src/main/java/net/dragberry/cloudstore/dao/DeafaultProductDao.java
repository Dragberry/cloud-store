package net.dragberry.cloudstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.dao.util.EntityServiceUtils;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Category_;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;
import net.dragberry.cloudstore.query.ProductListQuery;

@Stateless
public class DeafaultProductDao extends AbstractDao<Product> implements ProductDao {
    
    private final static Log LOGGER = LogFactory.getLog(DefaultCategoryDao.class);
    
    @Override
    public List<Product> fetchProducts(ProductListQuery productQuery) {
        LOGGER.info("Entering into DefaultProductDao.fetchProducts...");
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        
        Predicate where = null;
        if (productQuery.getId() != null) {
            where = cb.equal(productRoot.get(Product_.id), productQuery.getId());
        }
        where = EntityServiceUtils.addAndLikeExpression(productQuery.getTitle(), Product_.title, where, cb, productRoot);
        where = EntityServiceUtils.addAndLikeExpression(productQuery.getDescription(), Product_.description, where, cb, productRoot);
        where = EntityServiceUtils.addAndLikeExpression(productQuery.getFullDescription(), Product_.fullDescription, where, cb, productRoot);
        where = EntityServiceUtils.addRangeExpression(productQuery.getMinCost(), productQuery.getMaxCost(), Product_.cost, where, cb, productRoot);
        
		Join<Product, Category> joinProductCategory = productRoot.join(Product_.categories, JoinType.LEFT);
        if (!productQuery.getCategoryIdList().isEmpty()) {
            In<Long> categories = cb.in(joinProductCategory.get(Category_.id));
            for (Long categoryId : productQuery.getCategoryIdList()) {
                categories.value(categoryId);
            }
            where = EntityServiceUtils.addAndLikeExpression(where, categories, cb);
        }
        // General search. Split the request string and search words in main fields
        if (StringUtils.isNotBlank(productQuery.getSearchRequest())) {
        	String[] words = productQuery.getSearchRequest().split("\\s+");
        	Predicate searchRequestWhere = null;
        	for (String word : words) {
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Product_.title, searchRequestWhere, cb, productRoot);
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Product_.description, searchRequestWhere, cb, productRoot);
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Product_.fullDescription, searchRequestWhere, cb, productRoot);
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Category_.title, searchRequestWhere, cb, joinProductCategory);
        	}
        	where = EntityServiceUtils.addAndLikeExpression(where, searchRequestWhere, cb);
        }
        
        if (where != null) {
            cq.where(where);
        }
        
        Map<Class<?>, From<?, ?>>  sortMap = new HashMap<Class<?>, From<?,?>>();
        sortMap.put(Product.class, productRoot);
        sortMap.put(Category.class, joinProductCategory);
        cq.orderBy(EntityServiceUtils.getOrders(productQuery.getSortList(), sortMap, cb));
        
        cq.distinct(true);
        return getEntityManager().createQuery(cq).getResultList();
    }

}
