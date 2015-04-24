package net.dragberry.cloudstore.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = -2332005891064076014L;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role", 
        joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "ID"), 
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "ID"))
    private Set<CustomerRole> roles;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<CustomerRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<CustomerRole> roles) {
		this.roles = roles;
	}
	
}
