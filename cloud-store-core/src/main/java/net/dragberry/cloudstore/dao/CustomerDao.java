package net.dragberry.cloudstore.dao;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Customer;

@Local
public interface CustomerDao {

	Customer fetchByCustomerName(String customerName);
}
