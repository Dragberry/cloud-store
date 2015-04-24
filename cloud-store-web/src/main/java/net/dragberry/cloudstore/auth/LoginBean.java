package net.dragberry.cloudstore.auth;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.dragberry.cloudstore.business.CustomerServiceLocal;
import net.dragberry.cloudstore.domain.Customer;
import net.dragberry.cloudstore.navigation.MenuBean;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -8724570396594624664L;
	
	@Inject
	private CustomerServiceLocal customerService;
	
	@Inject
	private MenuBean menuBean;
	
	private Customer customer;
	
	private String username;
	
	private String password;
	
	@PostConstruct
	public void init() {
		customer = customerService.fetchByCustomerName(null);
		
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    try {
	      request.logout();
	      customer = customerService.fetchByCustomerName(null);
	    } catch (ServletException e) {
	      context.addMessage(null, new FacesMessage("Logout failed."));
	    }
	    menuBean.reloadMenu();
		return "/index?faces-redirect=true";
	}
	
	public String login() {
		
		
		
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    try {
	      request.login(username, password);
	      customer = customerService.fetchByCustomerName(username);
	    } catch (ServletException e) {
	      context.addMessage(null, new FacesMessage("Login failed."));
	      return "login_error";
	    }
	    menuBean.reloadMenu();
	    return "";
	}
	
	public String login(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    try {
	      request.login(this.username, this.password);
	      customer = customerService.fetchByCustomerName(username);
	    } catch (ServletException e) {
	      context.addMessage(null, new FacesMessage("Login failed."));
	      return "login_error";
	    }
	    menuBean.reloadMenu();
	    return viewId + "?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
