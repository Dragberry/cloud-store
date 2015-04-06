package net.dragberry.cloudstore.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * This class is a general product model.
 * 
 * @author Maksim Drahun
 *
 */
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

    private static final long serialVersionUID = 8718806985762163348L;
    
    /**
     * A product title
     */
    @Column(name = "title")
    private String title;
    
    /**
     * A product title
     */
    @Column(name = "description")
    private String description;
    
    /**
     * A product title
     */
    @Column(name = "full_description")
    private String fullDescription;
    
    /**
     * A product title
     */
    @Column(name = "cost")
    private BigDecimal cost;
    
    /**
     * A product can be associated with 0 or more categories.
     */
    @ManyToMany(mappedBy = "products")
    private Set<Category> categories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

}
