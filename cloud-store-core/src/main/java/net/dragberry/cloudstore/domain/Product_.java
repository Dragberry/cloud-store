package net.dragberry.cloudstore.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
public class Product_ {
    
    public static volatile SingularAttribute<Product,Long> id;
    public static volatile SingularAttribute<Product,String> title;
    public static volatile SingularAttribute<Product,String> description;
    public static volatile SingularAttribute<Product,String> fullDescription;

}
