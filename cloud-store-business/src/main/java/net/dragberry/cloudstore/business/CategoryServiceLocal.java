package net.dragberry.cloudstore.business;

import java.util.List;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Category;

@Local
public interface CategoryServiceLocal {

	List<Category> fetchCategories();
	
}
