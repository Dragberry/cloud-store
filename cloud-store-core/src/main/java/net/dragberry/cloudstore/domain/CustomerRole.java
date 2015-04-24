package net.dragberry.cloudstore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class CustomerRole extends AbstractEntity {

	private static final long serialVersionUID = -5218616447300324876L;
	
	public static final String ROLE_ADMIN = "admin";
	
	public static final String ROLE_ANONYMOUS = "anonymous";
	
	public static final String ROLE_CUSTOMER = "customer";
	
	@Column(name = "role_name")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
