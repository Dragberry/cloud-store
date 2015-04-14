package net.dragberry.cloudstore.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.dragberry.cloudstore.business.DashboardServiceLocal;
import net.dragberry.cloudstore.business.ProductServiceLocal;
import net.dragberry.cloudstore.domain.DashboardItem;
import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.ProductListQuery;

@Named("dashboardBean")
@RequestScoped
public class DashboardBean implements Serializable {
	
	private static final long serialVersionUID = 8546268836399403957L;
	
	@Inject
	private DashboardServiceLocal dashboardService;
	
	@Inject
	private ProductServiceLocal productService;
	
	private List<DashboardItem> items;
	
	@PostConstruct
	public void initialiseDashboard() {
		items = dashboardService.fetchDashboardItems();
	}

	public List<DashboardItem> getItems() {
		return items;
	}
	
	public String doCommonSearch(String request) {
		ProductListQuery query = new ProductListQuery();
		query.setSearchRequest(request);
		List<Product> products = productService.fetchProducts(query);
		return "/shop/productList.xhtml";
	}

}
