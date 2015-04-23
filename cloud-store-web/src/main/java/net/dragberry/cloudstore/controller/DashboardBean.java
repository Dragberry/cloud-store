package net.dragberry.cloudstore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.dragberry.cloudstore.business.DashboardServiceLocal;
import net.dragberry.cloudstore.dataimport.ProductImporterLocal;
import net.dragberry.cloudstore.domain.DashboardItem;

@Named("dashboardBean")
@RequestScoped
public class DashboardBean implements Serializable {
	
	private static final long serialVersionUID = 8546268836399403957L;
	
	@Inject
	private DashboardServiceLocal dashboardService;
	@Inject
	private ProductImporterLocal excelProductImporter;
	
	private List<DashboardItem> items;
	
	@PostConstruct
	public void initialiseDashboard() {
		items = dashboardService.fetchDashboardItems();
	}

	public List<DashboardItem> getItems() {
		return items;
	}
	
	public void doImport() throws Exception {
		File file = new File("D:\\Шары.xls");
        FileInputStream is = new FileInputStream(file);
        try {
        	excelProductImporter.doImport(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
	}
	
}
