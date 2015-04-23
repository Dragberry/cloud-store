package net.dragberry.cloudstore.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.dragberry.cloudstore.navigation.MenuBean;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -8724570396594624664L;
	
	@Inject
	private MenuBean menuBean;
	
	private String username;
	
	private String password;

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    try {
	      request.logout();
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
	      request.login(this.username, this.password);
	    } catch (ServletException e) {
	      context.addMessage(null, new FacesMessage("Login failed."));
	      return "error";
	    }
	    menuBean.reloadMenu();
	    return "";
	}
	
	public String login(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    try {
	      request.login(this.username, this.password);
	    } catch (ServletException e) {
	      context.addMessage(null, new FacesMessage("Login failed."));
	      return "error";
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
	
}
