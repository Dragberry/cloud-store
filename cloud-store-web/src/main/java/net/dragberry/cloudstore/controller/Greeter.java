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

import net.dragberry.cloudstore.business.ProductService;
import net.dragberry.cloudstore.business.ProductServiceLocal;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.domain.Product_;
import net.dragberry.cloudstore.query.ProductQuery;
import net.dragberry.cloudstore.query.sort.SortItem;
import net.dragberry.cloudstore.query.sort.SortOrder;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;
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
	
	private List<Product> productList = new ArrayList<Product>();

	/**
     * Injected GreeterEJB client
     */
    @EJB
    private ProductServiceLocal productService;

    /**
     * Stores the response from the call to greeterEJB.sayHello(...)
     */
    private String message;

    /**
     * Invoke greeterEJB.sayHello(...) and store the message
     * 
     * @param name
     *            The name of the person to be greeted
     */
    public void search(String title, String description, String fullDescription) {
        ProductQuery p = new ProductQuery();
    	p.setTitle(title);
    	p.setDescription(description);
    	p.setFullDescription(fullDescription);
    	p.addSortItem(Product_.description, SortOrder.DESCENDING);
    	List<Long> categoryIds = new ArrayList<Long>();
    	categoryIds.add(1L);
    	categoryIds.add(3L);
    	p.setCategoryIdList(categoryIds);
    	productList = productService.fetchProducts(p);
    }
    
    public List<Product> getProductList() {
        return productList;
    }
    
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Get the greeting message, customized with the name of the person to be
     * greeted.
     * 
     * @return message. The greeting message.
     */
    public String getMessage() {
        return message;
    }

}
