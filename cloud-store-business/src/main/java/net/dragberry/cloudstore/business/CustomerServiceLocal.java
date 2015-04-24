package net.dragberry.cloudstore.business;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Customer;

@Local
public interface CustomerServiceLocal {

	Customer fetchByCustomerName(String customerName);

	boolean isCustomerInRoles(Customer customer, String... roles);
}
