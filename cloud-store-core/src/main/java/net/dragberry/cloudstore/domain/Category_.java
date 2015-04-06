package net.dragberry.cloudstore.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Category.class)
public class Category_ extends AbstractEntity_ {
	
    public static volatile SingularAttribute<Category, String> title;

}
