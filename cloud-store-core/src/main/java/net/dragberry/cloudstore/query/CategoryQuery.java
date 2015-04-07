package net.dragberry.cloudstore.query;

import java.io.Serializable;
import java.util.List;

import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.query.sort.SortItem;

public class CategoryQuery implements Serializable {

	private static final long serialVersionUID = -2705861994972353360L;

	private Long id;
    
    private String title;
    
    private List<SortItem> sortList;
    
    private Category parentCategory;
    
    

}
