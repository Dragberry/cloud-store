package net.dragberry.cloudstore.util;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.cloudstore.menu.MenuItem;

public final class CustomFunctions {
	
	private CustomFunctions() {
	}
	
	public static String buildMenuPath(MenuItem menuItem) {
		if (StringUtils.isBlank(menuItem.getPath())) {
			return "/";
		}
		StringBuilder path = new StringBuilder("/faces/");
		path.append(menuItem.getPath());
		path.append(".xhtml");
		if (!menuItem.getParams().isEmpty()) {
			path.append("?");
			Iterator<Entry<String, String>> it = menuItem.getParams().entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				path.append(entry.getKey()).append("=").append(entry.getValue());
				if (it.hasNext()) {
					path.append("&");
				}
			}
		}
		return path.toString();
	}

}
