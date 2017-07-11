package com.xsjy.pojo.Custom.pojo_1050000;

import com.xsjy.pojo.BaseTable.Pojo_LSBM;

public class Pojo1050110 extends Pojo_LSBM {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 课程名称 property. */
	private String KCMC;

	/** The value of the simple 是否回访 property. */
	private String SFHF;

	/**
	 * Simple constructor of Abstract BeanPojo1050110 instances.
	 */
	public Pojo1050110() {
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
	 * Return the value of the 是否回访 column.
	 * 
	 * @return java.lang.String
	 */
	public String getSFHF() {
		return this.SFHF;
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
	 * Set the value of the 是否回访 column.
	 * 
	 * @param String
	 *            SFHF
	 */
	public void setSFHF(String SFHF) {
		this.SFHF = SFHF;
	}
}
