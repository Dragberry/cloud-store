package net.dragberry.cloudstore.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

@Named("translation")
@SessionScoped
public class Translation implements Serializable {

	private static final long serialVersionUID = 8412177741271256452L;
	
	private static final String REGEX = "\\{([^}]*)\\}";
    
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
    
    /**
     * Translate all keys in curly braces in string.
     * 
     * @param string
     * @return
     */
    public String formatStringWithKeys(String string) {
    	Map<String, String> words = new HashMap<String, String>();

		Pattern regex = Pattern.compile(REGEX);
		Matcher regexMatcher = regex.matcher(string);
		while (regexMatcher.find()) {
			String key = regexMatcher.group();
			words.put(key, translate(key.replace("{", StringUtils.EMPTY).replace("}", StringUtils.EMPTY)));
		}
		for (Entry<String, String> entry : words.entrySet()) {
			string = string.replace(entry.getKey(), entry.getValue());
		}
		return string;
    }
    
    

}
