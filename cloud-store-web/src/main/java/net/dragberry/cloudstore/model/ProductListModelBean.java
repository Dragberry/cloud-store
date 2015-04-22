package net.dragberry.cloudstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

@Named("productListModelBean")
@SessionScoped
public class ProductListModelBean implements Serializable {

	private static final long serialVersionUID = -7295319947493213658L;
	
	private boolean initialized = false;
	
	private List<Category> categoryList = new ArrayList<Category>();
	
	private List<Product> productList = new ArrayList<Product>(); 
	
	private ProductListQuery productListQuery = new ProductListQuery();
	
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public ProductListQuery getProductListQuery() {
		return productListQuery;
	}

	public void setProductListQuery(ProductListQuery productListQuery) {
		this.productListQuery = productListQuery;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

}
