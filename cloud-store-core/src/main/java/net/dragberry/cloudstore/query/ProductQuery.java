package net.dragberry.cloudstore.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import net.dragberry.cloudstore.domain.Product;
import net.dragberry.cloudstore.query.sort.SortItem;
import net.dragberry.cloudstore.query.sort.SortOrder;

public class ProductQuery implements Serializable {

    private static final long serialVersionUID = 2392814791170770444L;
    
    private Long id;
    
    private String title;

    private String description;

    private String fullDescription;
    
    private List<SortItem> sortList;
    
    private List<Long> categoryIdList = new ArrayList<Long>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
    
    public List<Long> getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(List<Long> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public List<SortItem> getSortList() {
        return sortList;
    }
    
    public void addSortItem(SingularAttribute<Product,String> attribute, SortOrder sortOrder) {
        if (sortList == null) {
            sortList = new ArrayList<SortItem>();
        }
        SortItem sortItem = new SortItem();
        sortItem.setDirection(sortOrder);
        sortItem.setAttribute(attribute);
        sortList.add(sortItem);
    }

}
