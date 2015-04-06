package net.dragberry.cloudstore.query.sort;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import net.dragberry.cloudstore.domain.AbstractEntity;

public interface SortableQuery<T extends AbstractEntity> {
	
    public List<SortItem> getSortList();
    
    public void addSortItem(SingularAttribute<T, String> attribute, SortOrder sortOrder);

}
