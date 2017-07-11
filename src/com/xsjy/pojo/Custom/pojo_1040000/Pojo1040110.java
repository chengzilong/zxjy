package com.xsjy.pojo.Custom.pojo_1040000;

import java.io.Serializable;

public class Pojo1040110 implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 课程ID property. */
	private String KCID;

	/** The value of the simple 课程名称 property. */
	private String KCMC;

	/** The value of the simple 报名人数 property. */
	private String BMRS;

	/**
	 * Simple constructor of Abstract BeanPojo1040110 instances.
	 */
	public Pojo1040110() {
	}

	/**
	 * Return the value of the 课程ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getKCID() {
		return this.KCID;
	}

	/**
	 * Return the value of the 课程名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getKCMC() {
		return this.KCMC;
	}

	/**
	 * Return the value of the 报名人数 column.
	 * 
	 * @return java.lang.String
	 */
	public String getBMRS() {
		return this.BMRS;
	}

	/**
	 * Set the value of the 课程ID column.
	 * 
	 * @param String
	 *            KCID
	 */
	public void setKCID(String KCID) {
		this.KCID = KCID;
	}

	/**
	 * Set the value of the 课程名称 column.
	 * 
	 * @param String
	 *            KCMC
	 */
	public void setKCMC(String KCMC) {
		this.KCMC = KCMC;
	}

	/**
	 * Set the value of the 报名人数 column.
	 * 
	 * @param String
	 *            BMRS
	 */
	public void setBMRS(String BMRS) {
		this.BMRS = BMRS;
	}
}
