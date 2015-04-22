package net.dragberry.cloudstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
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
import net.dragberry.cloudstore.result.ProductList;

@Stateless
public class DeafaultProductDao extends AbstractDao<Product> implements ProductDao {
    
    private final static Log LOGGER = LogFactory.getLog(DefaultCategoryDao.class);
    
    @Override
    public ProductList fetchProducts(ProductListQuery productQuery) {
        LOGGER.info("Entering into DefaultProductDao.fetchProducts...");
        
        ProductList resultList = new ProductList();
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> productRoot = cq.from(Product.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        
        Join<Product, Category> joinProductCategory = productRoot.join(Product_.categories, JoinType.LEFT);
        Join<Product, Category> joinProductCategoryCount = countRoot.join(Product_.categories, JoinType.LEFT);
        
        Predicate where = getWhereClause(productRoot, cb, productQuery, joinProductCategory);
        Predicate whereCount = getWhereClause(countRoot, cb, productQuery, joinProductCategoryCount);
        
        countQuery.select(cb.count(countRoot));
        
        if (where != null) {
            cq.where(where);
            countQuery.where(whereCount);
        }
        cq.distinct(true);
        countQuery.distinct(true);
        
        Map<Class<?>, From<?, ?>>  sortMap = new HashMap<Class<?>, From<?,?>>();
        sortMap.put(Product.class, productRoot);
        sortMap.put(Category.class, joinProductCategory);
        cq.orderBy(EntityServiceUtils.getOrders(productQuery.getSortList(), sortMap, cb));
        
        TypedQuery<Product> query = getEntityManager().createQuery(cq);
        setPageableParams(productQuery, query);
        
        List<Product> productList = query.getResultList();
        Long count = getEntityManager().createQuery(countQuery).getSingleResult();
        
        resultList.setList(productList);
        resultList.setCount(count);
        resultList.setPageNumber(productQuery.getPageNumber());
        resultList.setPageSize(productQuery.getPageSize());
        
        return resultList;
    }
    
    private Predicate getWhereClause(Root<Product> root, CriteriaBuilder cb, ProductListQuery productQuery, Join<Product, Category> joinProductCategory) {
    	Predicate where = null;
        if (productQuery.getId() != null) {
            where = cb.equal(root.get(Product_.id), productQuery.getId());
        }
        where = EntityServiceUtils.addAndLikeExpression(productQuery.getTitle(), Product_.title, where, cb, root);
        where = EntityServiceUtils.addAndLikeExpression(productQuery.getDescription(), Product_.description, where, cb, root);
        where = EntityServiceUtils.addAndLikeExpression(productQuery.getFullDescription(), Product_.fullDescription, where, cb, root);
        where = EntityServiceUtils.addRangeExpression(productQuery.getMinCost(), productQuery.getMaxCost(), Product_.cost, where, cb, root);
        
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
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Product_.title, searchRequestWhere, cb, root);
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Product_.description, searchRequestWhere, cb, root);
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Product_.fullDescription, searchRequestWhere, cb, root);
        		searchRequestWhere = EntityServiceUtils.addOrLikeExpression(word, Category_.title, searchRequestWhere, cb, joinProductCategory);
        	}
        	where = EntityServiceUtils.addAndLikeExpression(where, searchRequestWhere, cb);
        }
        return where;
    }
    

}
