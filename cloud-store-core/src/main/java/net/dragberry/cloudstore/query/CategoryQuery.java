package net.dragberry.cloudstore.query;

import java.io.Serializable;
import net.dragberry.cloudstore.domain.Category;

public class CategoryQuery  extends SortableQuery implements Serializable {

	private static final long serialVersionUID = -2705861994972353360L;

	private Long id;
    
    private String title;
    
    private Category parentCategory;

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

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
    
}
