package com.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	/**
     * BigDecimal型の対象値を文字列に変換する。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String valueOf(BigDecimal value) {
        if (null == value) {
            return "";
        }
        return value.toPlainString();
    }

    /**
     * Long型の対象値を文字列に変換する。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String valueOf(Long value) {
        if (null == value) {
            return "";
        }
        return value.toString();
    }

    /**
     * Integer型の対象値を文字列に変換する。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String valueOf(Integer value) {
        if (null == value) {
            return "";
        }
        return value.toString();
    }

    /**
     * バイト配列型の対象値を文字列に変換する。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String valueOf(byte[] value) {
        if (null == value) {
            return "";
        }
        try {
            return new String(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Date型の日付をyyyy/MM/dd形式の文字列に変換する。
     *
     * @param value Date型の日付
     * @return 変換後の値
     */
    public static String valueOf(Date value) {
        if (null == value) {
            return "";
        }
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(value);
    }

}
