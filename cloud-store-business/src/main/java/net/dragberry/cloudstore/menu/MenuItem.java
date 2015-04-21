package net.dragberry.cloudstore.menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public class MenuItem  implements Serializable {
	
	private static final long serialVersionUID = 7807348440821573536L;

	private List<MenuItem> subMenu;
	
	private String titleKey;
	
	private String path;
	
	private Map<String, String> params = new HashedMap<String, String>();

	public List<MenuItem> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<MenuItem> subMenu) {
		this.subMenu = subMenu;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
	
	public void addSubMenuItem(MenuItem item) {
		subMenu.add(item);
	}
	
	public void addParam(String name, String value) {
		params.put(name, value);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
