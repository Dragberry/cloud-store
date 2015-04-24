package net.dragberry.cloudstore.business;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.dragberry.cloudstore.dao.CustomerDao;
import net.dragberry.cloudstore.domain.Customer;
import net.dragberry.cloudstore.domain.CustomerRole;

@Stateless
public class CustomerService implements CustomerServiceLocal {

	private static final String ANONYMOUS = "anonymous";
	
	@Inject
	private CustomerDao defaultCustomerDao;
	
	@Override
	public Customer fetchByCustomerName(String customerName) {
		Customer customer = defaultCustomerDao.fetchByCustomerName(customerName);
		if (customer == null) {
			customer = defaultCustomerDao.fetchByCustomerName(ANONYMOUS);
		}
		return customer;
	}

	@Override
	public boolean isCustomerInRoles(Customer customer, String... roles) {
		boolean hasRoles = true;
		for (CustomerRole role : customer.getRoles()) {
			for (String roleName : roles) {
				if (roleName == null || !roleName.equals(role.getRoleName())) {
					hasRoles = false;
					break;
				}
			}
			if (!hasRoles) {
				break;
			}
		}
		return hasRoles;
	}

}
