package net.dragberry.cloudstore.dao;

import java.util.List;

import net.dragberry.cloudstore.domain.DashboardItem;

public interface DashboardDao {

	List<DashboardItem> fetchDashboardItems();
}
