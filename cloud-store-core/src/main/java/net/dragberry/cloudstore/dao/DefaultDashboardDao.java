package net.dragberry.cloudstore.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import net.dragberry.cloudstore.domain.DashboardItem;

public class DefaultDashboardDao extends AbstractDao<DashboardItem> implements DashboardDao {

	@Override
	public List<DashboardItem> fetchDashboardItems() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<DashboardItem> cq = cb.createQuery(DashboardItem.class);
		cq.from(DashboardItem.class);
		return getEntityManager().createQuery(cq).getResultList();
	}

}
