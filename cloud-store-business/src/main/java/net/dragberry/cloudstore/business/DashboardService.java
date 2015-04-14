package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.dragberry.cloudstore.dao.DashboardDao;
import net.dragberry.cloudstore.domain.DashboardItem;

@Stateless
public class DashboardService implements DashboardServiceLocal {
	
	@Inject
	private DashboardDao defaultDashboardDao;

	@Override
	public List<DashboardItem> fetchDashboardItems() {
		return defaultDashboardDao.fetchDashboardItems();
	}

}
