package net.dragberry.cloudstore.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.dragberry.cloudstore.business.ProductServiceLocal;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

@Named("quickSearchBean")
@RequestScoped
public class QuickSearchBean implements Serializable {

	private static final long serialVersionUID = 6412463385906685122L;
	
	private String searchRequest;
	
	@Inject
	private ProductServiceLocal productService;
	@Inject
	private Greeter greeter;
	
	public String doQuickSearch() {
		ProductListQuery query = new ProductListQuery();
		query.setSearchRequest(searchRequest);
		List<Product> productList = productService.fetchProducts(query).getList();
		greeter.setProductList(productList);
		return "/faces/searchResult?faces-redirect=true";
	}

	public String getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(String searchRequest) {
		this.searchRequest = searchRequest;
	}

}
