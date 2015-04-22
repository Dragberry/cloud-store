package net.dragberry.cloudstore.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -8724570396594624664L;

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index?faces-redirect=true";
	}
}
