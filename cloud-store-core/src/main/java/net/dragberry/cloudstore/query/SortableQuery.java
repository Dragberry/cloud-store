package net.dragberry.cloudstore.query;

import java.util.Set;
import java.util.TreeSet;

import net.dragberry.cloudstore.query.sort.SortItem;
import net.dragberry.cloudstore.query.sort.SortOrder;

public class SortableQuery extends PageableQuery {
	
	private Set<SortItem> sortList = new TreeSet<SortItem>();
	
	public void addSortItem(String field, SortOrder sortOrder, Class<?> type, Integer order) {
        SortItem sortItem = new SortItem();
        sortItem.setDirection(sortOrder);
        sortItem.setField(field);
        sortItem.setOrder(order);
        sortItem.setType(type);
        if (sortList == null) {
        	sortList = new TreeSet<SortItem>();
        }
       sortList.add(sortItem);
    }

	public Set<SortItem> getSortList() {
        return sortList;
    }
}
