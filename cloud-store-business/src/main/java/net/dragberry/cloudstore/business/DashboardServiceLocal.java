package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.DashboardItem;

@Local
public interface DashboardServiceLocal {
	
	List<DashboardItem> fetchDashboardItems();

}
