package net.dragberry.cloudstore.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Customer.class)
public class Customer_ extends AbstractEntity_ {
	
	public static volatile SingularAttribute<Customer, String> customerName;

}
