package net.dragberry.cloudstore.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.dragberry.cloudstore.business.DashboardServiceLocal;
import net.dragberry.cloudstore.domain.DashboardItem;

@Named("dashboardBean")
@RequestScoped
public class DashboardBean implements Serializable {
	
	private static final long serialVersionUID = 8546268836399403957L;
	
	@Inject
	private DashboardServiceLocal dashboardService;
	
	private List<DashboardItem> items;
	
	@PostConstruct
	public void initialiseDashboard() {
		items = dashboardService.fetchDashboardItems();
	}

	public List<DashboardItem> getItems() {
		return items;
	}
	
}
