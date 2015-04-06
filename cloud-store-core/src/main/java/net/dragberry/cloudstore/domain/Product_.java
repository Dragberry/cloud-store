package net.dragberry.cloudstore.domain;

import java.math.BigDecimal;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
public class Product_ extends AbstractEntity_ {
    
    public static volatile SingularAttribute<Product, String> title;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, String> fullDescription;
    public static volatile SingularAttribute<Product, BigDecimal> cost;
    public static volatile SetAttribute<Product, Category> categories;

}
