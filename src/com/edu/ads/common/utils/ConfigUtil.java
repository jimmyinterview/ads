package com.edu.ads.common.utils;

import java.util.*;

import com.edu.ads.bean.user.User;

public class ConfigUtil {
	private static ResourceBundle bundle = ResourceBundle.getBundle("Config");

	public static String getString(String prop) {

		return bundle.getString(prop);
	}

	public static String getProperty(String prop) {

		return getString(prop);
	}

	public static String getStringPath(Object obj, String prop) {
		String path = (obj.getClass().getResource("/").getPath()).replaceFirst(
				"/", "");
		int index = path.indexOf("WEB-INF");
		if (index == -1) {
			index = path.indexOf("classes");
		}
		if (index == -1) {
			index = path.indexOf("bin");
		}
		path = path.substring(0, index) + getString(prop);
		return path;
	}

	public static String updatePath(User user) {
		String path = System.getProperty("user.dir");
		int index = path.indexOf("bin");
		path = path.substring(0, index) + "\\webapps\\"+ getString("PROMGRAM_NAME")+"\\"
				+ getString("UPLOAD_IMG") + "\\" + user.getId();
		return path;
	}

}
