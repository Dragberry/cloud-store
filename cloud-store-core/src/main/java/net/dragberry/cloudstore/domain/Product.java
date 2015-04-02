package net.dragberry.cloudstore.domain;

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
@Table(name = "PRODUCT")
public class Product extends AbstractEntity {

    private static final long serialVersionUID = 8718806985762163348L;
    
    /**
     * A product title
     */
    @Column(name = "PRODUCT_NAME")
    private String productName;
    
    /**
     * A product can be associated with 0 or more categories.
     */
    @ManyToMany(mappedBy = "products")
    private Set<Category> category;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String title) {
        this.productName = title;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

}
