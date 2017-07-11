package com.xsjy.pojo.Custom.pojo_9010000;

import com.xsjy.pojo.BaseTable.Pojo_MENU;

public class Pojo9010130 extends Pojo_MENU {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The value of the simple 是否关联 property. */
	private String MENU_SFGL;

	/** The value of the simple 关联状态 property. */
	private String MENU_GLZT;

	/** The value of the simple 菜单类型 property. */
	private String CDLX;

	/**
	 * Simple constructor of Abstract BeanPojo_MENU instances.
	 */
	public Pojo9010130() {
	}

	/**
	 * Return the value of the 是否关联 column.
	 * 
	 * @return java.lang.String
	 */
	public String getMENU_SFGL() {
		return this.MENU_SFGL;
	}

	/**
	 * Return the value of the 关联状态 column.
	 * 
	 * @return java.lang.String
	 */
	public String getMENU_GLZT() {
		return this.MENU_GLZT;
	}

	/**
	 * Return the value of the 菜单类型 column.
	 * 
	 * @return java.lang.String
	 */
	public String getCDLX() {
		return this.CDLX;
	}

	/**
	 * Set the value of the 是否关联 column.
	 * 
	 * @param String
	 *            MENU_SFGL
	 */
	public void setMENU_SFGL(String MENU_SFGL) {
		this.MENU_SFGL = MENU_SFGL;
	}

	/**
	 * Set the value of the 关联状态 column.
	 * 
	 * @param String
	 *            MENU_GLZT
	 */
	public void setMENU_GLZT(String MENU_GLZT) {
		this.MENU_GLZT = MENU_GLZT;
	}

	/**
	 * Set the value of the 菜单类型 column.
	 * 
	 * @param String
	 *            CDLX
	 */
	public void setCDLX(String CDLX) {
		this.CDLX = CDLX;
	}
}
