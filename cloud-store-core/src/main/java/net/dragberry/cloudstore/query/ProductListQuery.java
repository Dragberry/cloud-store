package net.dragberry.cloudstore.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductListQuery extends SortableQuery implements Serializable {

    private static final long serialVersionUID = 2392814791170770444L;
    
    private Long id;
    
    private String searchRequest;
    
    private String title;

    private String description;

    private String fullDescription;
    
    private BigDecimal minCost;
    
    private BigDecimal maxCost;
    
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

    public String getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(String searchRequest) {
		this.searchRequest = searchRequest;
	}

}
