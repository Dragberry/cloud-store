package net.dragberry.cloudstore.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.dragberry.cloudstore.collections.TreeNode;

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
     * A product description
     */
    @Column(name = "description")
    private String description;
    
    /**
     * A product full description
     */
    @Column(name = "full_description")
    private String fullDescription;
    
    /**
     * A product cost
     */
    @Column(name = "cost")
    private BigDecimal cost;
    
    /**
     * A main product image
     */
    @ManyToOne
    @JoinColumn(name = "main_image_id", referencedColumnName = "id")
    private Image mainImage;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY_PRODUCT", 
        joinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"), 
        inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID"))
    private Set<Category> categories;
    
    @Transient
    private TreeNode<Category> categoryTree;
    
    public List<Category> getCategoryList() {
    	return categories == null ? new ArrayList<Category>() : new ArrayList<Category>(categories);
    }

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

	public Image getMainImage() {
		return mainImage;
	}

	public void setMainImage(Image mainImage) {
		this.mainImage = mainImage;
	}

	public TreeNode<Category> getCategoryTree() {
		return categoryTree;
	}

	public void setCategoryTree(TreeNode<Category> categoryTree) {
		this.categoryTree = categoryTree;
	}

}
