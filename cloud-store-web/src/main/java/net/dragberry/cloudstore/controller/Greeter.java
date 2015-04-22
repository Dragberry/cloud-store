/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dragberry.cloudstore.controller;

import net.dragberry.cloudstore.business.CategoryServiceLocal;
import net.dragberry.cloudstore.business.ImageServiceLocal;
import net.dragberry.cloudstore.business.ProductServiceLocal;
import net.dragberry.cloudstore.collections.TreeNode;
import net.dragberry.cloudstore.collections.TreeNodeCallback;
import net.dragberry.cloudstore.dataimport.ImageImporterLocal;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple managed bean that is used to invoke the GreeterEJB and store the
 * response. The response is obtained by invoking getMessage().
 * 
 * @author paul.robinson@redhat.com, 2011-12-21
 */
@Named("greeter")
@SessionScoped
public class Greeter implements Serializable {

	private static final long serialVersionUID = 9036218023267311356L;
	
	private static Log LOGGER = LogFactory.getLog(Greeter.class);
	
	private List<Product> productList = new ArrayList<Product>();
	
	private List<Category> categoryList = new ArrayList<Category>();
	
	private List<String> selectedCategoryIds = new  ArrayList<String>();
	
	@EJB
    private ProductServiceLocal productService;
    
    @EJB
    private CategoryServiceLocal categoryService;
    
    @EJB
    private ImageServiceLocal imageService;
    
    @EJB
    private ImageImporterLocal imageImporter;
    
    @PostConstruct
    public void fetchInitializationData() {
//        imageImporter.doImageImport("y:\\images");;
//    	categoryTree = categoryService.buildCategoryTree();
    	categoryList = categoryService.fetchCategories();
    }

    public void search(String searchRequest, String title, String description, String fullDescription, String minCost, String maxCost) {
    	categoryService.buildCategoryTree();
    	ProductListQuery p = new ProductListQuery();
    	p.setPageSize(3);
    	p.setPageNumber(1);
        p.setSearchRequest(searchRequest);
        p.setTitle(title);
    	p.setDescription(description);
    	p.setFullDescription(fullDescription);
    	List<Long> ids = new ArrayList<Long>();
    	for (String id : selectedCategoryIds) {
    		ids.add(Long.valueOf(id));
    	}
    	p.setCategoryIdList(ids);
    	p.setMinCost(StringUtils.isBlank(minCost) ? null : new BigDecimal(minCost));
    	p.setMaxCost(StringUtils.isBlank(maxCost) ? null : new BigDecimal(maxCost));
    	productList = productService.fetchProducts(p);
    	
    	for (Product product : productList) {
    		LOGGER.info(product.getTitle());
    		product.getCategoryTree().walkTree(new TreeNodeCallback<Category>() {

				@Override
				public int handleTreeNode(TreeNode<Category> node) {
					LOGGER.info(StringUtils.repeat("\t", node.getLevel()) + node.getReference().getTitle());
					return 0;
				}
			});
    	}
    }
    
    public List<Category> getChildren(Category category, Set<Category> categories) {
    	List<Category> children = new ArrayList<Category>();
    	for (Category cat : categories) {
    		if (cat.hasParent()) {
    			if (cat.getParentCategory().equals(category)) {
    				children.add(cat);
    			}
    		}
    	}
    	return children;
    }
    
    public List<Product> getProductList() {
        return productList;
    }
    
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<String> getSelectedCategoryIds() {
		return selectedCategoryIds;
	}

	public void setSelectedCategoryIds(List<String> selectedCategoryIds) {
		this.selectedCategoryIds = selectedCategoryIds;
	}

}
