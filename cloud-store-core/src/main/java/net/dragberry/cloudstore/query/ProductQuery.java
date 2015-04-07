package net.dragberry.cloudstore.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.dragberry.cloudstore.query.sort.SortItem;
import net.dragberry.cloudstore.query.sort.SortOrder;

public class ProductQuery implements Serializable {

    private static final long serialVersionUID = 2392814791170770444L;
    
    private Long id;
    
    private String title;

    private String description;

    private String fullDescription;
    
    private BigDecimal minCost;
    
    private BigDecimal maxCost;
    
    private Set<SortItem> sortList = new TreeSet<SortItem>();
    
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

    public BigDecimal getMinCost() {
		return minCost;
	}

	public void setMinCost(BigDecimal minCost) {
		this.minCost = minCost;
	}

	public BigDecimal getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(BigDecimal maxCost) {
		this.maxCost = maxCost;
	}

	public Set<SortItem> getSortList() {
        return sortList;
    }
    
    public void addSortItem(String field, SortOrder sortOrder, Class<?> type, Integer order) {
        SortItem sortItem = new SortItem();
        sortItem.setDirection(sortOrder);
        sortItem.setField(field);
        sortItem.setOrder(order);
        sortItem.setType(type);
        if (sortList == null) {
        	sortList = new TreeSet<SortItem>();
        }
        Log log = LogFactory.getLog(SortItem.class);
       sortList.add(sortItem);
    }

}
