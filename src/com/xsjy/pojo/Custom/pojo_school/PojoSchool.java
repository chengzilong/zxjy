package com.xsjy.pojo.Custom.pojo_school;

import com.xsjy.pojo.BaseTable.Pojo_JSXX;

public class PojoSchool extends Pojo_JSXX {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 教师ID property. */
	private String JSID;

	/** The value of the simple 学校教师 property. */
	private String XXJS;

	/**
	 * Simple constructor of Abstract BeanPojoSchool instances.
	 */
	public PojoSchool() {
	}

	/**
	 * Return the value of the 教师ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getJSID() {
		return this.JSID;
	}

	/**
	 * Return the value of the 学校教师 column.
	 * 
	 * @return java.lang.String
	 */
	public String getXXJS() {
		return this.XXJS;
	}

	/**
	 * Set the value of the 教师ID column.
	 * 
	 * @param String
	 *            JSID
	 */
	public void setJSID(String JSID) {
		this.JSID = JSID;
	}

	/**
	 * Set the value of the 学校教师 column.
	 * 
	 * @param String
	 *            XXJS
	 */
	public void setXXJS(String XXJS) {
		this.XXJS = XXJS;
	}
}
