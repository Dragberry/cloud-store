package net.dragberry.cloudstore.dao;
import java.util.List;

import net.dragberry.cloudstore.domain.Category;

public interface CategoryDao {
	
	List<Category> fetchCategories();

}
