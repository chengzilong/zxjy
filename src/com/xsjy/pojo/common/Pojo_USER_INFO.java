package com.xsjy.pojo.common;

import java.io.Serializable;

/**
 * @ClassName: SELECT_LIST
 * @Package:com.blank.pojo.common
 * @Description: 下拉列表键值对共通类
 * @author ljg
 * @date 2014年7月21日 上午9:12:55
 * @update
 */
public class Pojo_USER_INFO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value of the simple 用户名称 property. */
	private String USER_NAME;

	/** The value of the simple 消息数量 property. */
	private String MESS_CONT;

	/** The value of the simple 主级信息 property. */
	private String INFO_MAST;

	/** The value of the simple 次级信息 property. */
	private String INFO_SLAV;

	/** The value of the simple 消息菜单ID property. */
	private String INFO_MSID;

	/** The value of the simple 头像路径 property. */
	private String HEAD_PICS;


	/**
	 * Return the value of the 用户名称 column.
	 *
	 * @return java.lang.String
	 */
	public String getUSER_NAME() {
		return this.USER_NAME;
	}

	/**
	 * Return the value of the 消息数量 column.
	 *
	 * @return java.lang.String
	 */
	public String getMESS_CONT() {
		return this.MESS_CONT;
	}

	/**
	 * Return the value of the 主级信息 column.
	 *
	 * @return java.lang.String
	 */
	public String getINFO_MAST() {
		return this.INFO_MAST;
	}

	/**
	 * Return the value of the 次级信息 column.
	 *
	 * @return java.lang.String
	 */
	public String getINFO_SLAV() {
		return this.INFO_SLAV;
	}

	/**
	 * Return the value of the 消息菜单ID column.
	 *
	 * @return java.lang.String
	 */
	public String getINFO_MSID() {
		return this.INFO_MSID;
	}

	/**
	 * Return the value of the 头像路径 column.
	 *
	 * @return java.lang.String
	 */
	public String getHEAD_PICS() {
		return this.HEAD_PICS;
	}

	/**
	 * Set the value of the 用户名称 column.
	 *
	 * @param String
	 *            USER_NAME
	 */
	public void setUSER_NAME(String USER_NAME) {
		this.USER_NAME = USER_NAME;
	}

	/**
	 * Set the value of the 消息数量 column.
	 *
	 * @param String
	 *            MESS_CONT
	 */
	public void setMESS_CONT(String MESS_CONT) {
		this.MESS_CONT = MESS_CONT;
	}

	/**
	 * Set the value of the 主级信息 column.
	 *
	 * @param String
	 *            INFO_MAST
	 */
	public void setINFO_MAST(String INFO_MAST) {
		this.INFO_MAST = INFO_MAST;
	}

	/**
	 * Set the value of the 次级信息 column.
	 *
	 * @param String
	 *            INFO_SLAV
	 */
	public void setINFO_SLAV(String INFO_SLAV) {
		this.INFO_SLAV = INFO_SLAV;
	}

	/**
	 * Set the value of the 消息菜单ID column.
	 *
	 * @param String
	 *            INFO_MSID
	 */
	public void setINFO_MSID(String INFO_MSID) {
		this.INFO_MSID = INFO_MSID;
	}

	/**
	 * Set the value of the 头像路径 column.
	 *
	 * @param String
	 *            HEAD_PICS
	 */
	public void setHEAD_PICS(String HEAD_PICS) {
		this.HEAD_PICS = HEAD_PICS;
	}

}
