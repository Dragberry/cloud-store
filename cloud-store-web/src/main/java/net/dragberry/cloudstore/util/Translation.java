package net.dragberry.cloudstore.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("translation")
@SessionScoped
public class Translation implements Serializable {

    private static final long serialVersionUID = 8412177741271256452L;
    
    private static ResourceBundle getResourceBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
    }

    public String translate(String key) {
        return getResourceBundle().getString(key);
    }

    public String translate(String key, Object... params) {
        return MessageFormat.format(getResourceBundle().getString(key), params);
    }
    
    public String translateAll(String key, String... keys) {
    	ResourceBundle resourceBundle = getResourceBundle();
    	Object[] params = new String[keys.length];
    	for (int i = 0; i < keys.length; i++) {
    		params[i] = resourceBundle.getString(keys[i]);
    	}
        return MessageFormat.format(getResourceBundle().getString(key), params);
    }
    
    

}
