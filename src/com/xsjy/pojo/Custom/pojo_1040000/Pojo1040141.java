package com.xsjy.pojo.Custom.pojo_1040000;

import com.xsjy.pojo.BaseTable.Pojo_XSXX;

public class Pojo1040141 extends Pojo_XSXX {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 阶段年级 property. */
	private String JDNJ;

	/** The value of the simple 班次ID property. */
	private String BCID;

	/**
	 * Simple constructor of Abstract BeanPojo1040141 instances.
	 */
	public Pojo1040141() {
	}

	/**
	 * Return the value of the 阶段年级 column.
	 * 
	 * @return java.lang.String
	 */
	public String getJDNJ() {
		return this.JDNJ;
	}

	/**
	 * Return the value of the 班次ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getBCID() {
		return this.BCID;
	}

	/**
	 * Set the value of the 阶段年级 column.
	 * 
	 * @param String
	 *            JDNJ
	 */
	public void setJDNJ(String JDNJ) {
		this.JDNJ = JDNJ;
	}

	/**
	 * Set the value of the 班次ID column.
	 * 
	 * @param String
	 *            BCID
	 */
	public void setBCID(String BCID) {
		this.BCID = BCID;
	}
}
