package com.framework.util;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils extends StringUtils {

	/** 
	 * @Method: safeToString 
	 * @Description: 安全返回字符串 
	 * @param arg
	 * @return 返回值说明
	 * @author misty
	 * @date 2014年5月19日 下午9:44:40 
	 */
	public static String safeToString(Object arg) {
		if (arg == null)
			return "";
		else
			return String.valueOf(arg);
	}

}
