package com.xsjy.pojo.Custom.pojo_school;

import java.io.Serializable;

import com.xsjy.pojo.BaseTable.Pojo_KCFY;

public class PojoClass extends Pojo_KCFY implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 班次ID property. */
	private String BCID;

	/** The value of the simple 班次名称 property. */
	private String BCMC;

	/** The value of the simple 课程ID property. */
	private String KCID;

	/** The value of the simple 课程名称 property. */
	private String KCMC;

	/** The value of the simple 学时 property. */
	private String XS;

	/** The value of the simple 开课时间 property. */
	private String KSSJ;

	/** The value of the simple 结束时间 property. */
	private String JSSJ;

	/** The value of the simple 时段ID property. */
	private String SDID;

	/** The value of the simple 时段名称 property. */
	private String SDMC;

	/** The value of the simple 费用 property. */
	private String FY;

	/** The value of the simple 教师ID property. */
	private String JSID;

	/** The value of the simple 教师姓名 property. */
	private String JSXM;

	/** The value of the simple 区域ID property. */
	private String SKQYID;

	/** The value of the simple 区域名称 property. */
	private String SKQYMC;

	/** The value of the simple 类型ID property. */
	private String LXID;

	/** The value of the simple 类型名称 property. */
	private String LXMC;

	/** The value of the simple 班次状态 property. */
	private String BCZT;

	/** The value of the simple 条件教学学段 property. */
	private String PARAJXXD;

	/** The value of the simple 条件求教学科 property. */
	private String PARAQJXK;

	/** The value of the simple 条件课程类型 property. */
	private String PARAKCLX;

	/** The value of the simple 条件上课时段 property. */
	private String PARASKSD;

	/** The value of the simple 条件上课区域 property. */
	private String PARASKQY;

	/** The value of the simple 条件课程费用 property. */
	private String PARAKCFY;
	
	/**The value of the simple 是否报名 property.*/
	private String BMED;

	/**
	 * Return the value of the 课程名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getBMED() {
		return this.BMED;
	}

	/**
	 * Simple constructor of Abstract BeanPojoClass instances.
	 */
	public PojoClass() {
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
	 * Return the value of the 班次名称 column.
	 *
	 * @return java.lang.String
	 */
	public String getBCMC() {
		return this.BCMC;
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
	 * Return the value of the 学时 column.
	 *
	 * @return java.lang.String
	 */
	public String getXS() {
		return this.XS;
	}

	/**
	 * Return the value of the 开课时间 column.
	 *
	 * @return java.lang.String
	 */
	public String getKSSJ() {
		return this.KSSJ;
	}

	/**
	 * Return the value of the 结束时间 column.
	 *
	 * @return java.lang.String
	 */
	public String getJSSJ() {
		return this.JSSJ;
	}

	/**
	 * Return the value of the 时段ID column.
	 *
	 * @return java.lang.String
	 */
	public String getSDID() {
		return this.SDID;
	}

	/**
	 * Return the value of the 时段名称 column.
	 *
	 * @return java.lang.String
	 */
	public String getSDMC() {
		return this.SDMC;
	}

	/**
	 * Return the value of the 费用 column.
	 *
	 * @return java.lang.String
	 */
	public String getFY() {
		return this.FY;
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
	 * Return the value of the 教师姓名 column.
	 *
	 * @return java.lang.String
	 */
	public String getJSXM() {
		return this.JSXM;
	}

	/**
	 * Return the value of the 区域ID column.
	 *
	 * @return java.lang.String
	 */
	public String getSKQYID() {
		return this.SKQYID;
	}

	/**
	 * Return the value of the 区域名称 column.
	 *
	 * @return java.lang.String
	 */
	public String getSKQYMC() {
		return this.SKQYMC;
	}

	/**
	 * Return the value of the 类型ID column.
	 *
	 * @return java.lang.String
	 */
	public String getLXID() {
		return this.LXID;
	}

	/**
	 * Return the value of the 类型名称 column.
	 *
	 * @return java.lang.String
	 */
	public String getLXMC() {
		return this.LXMC;
	}

	/**
	 * Return the value of the 班次状态 column.
	 *
	 * @return java.lang.String
	 */
	public String getBCZT() {
		return this.BCZT;
	}

	/**
	 * Return the value of the 条件教学学段 column.
	 *
	 * @return java.lang.String
	 */
	public String getPARAJXXD() {
		return this.PARAJXXD;
	}

	/**
	 * Return the value of the 条件求教学科 column.
	 *
	 * @return java.lang.String
	 */
	public String getPARAQJXK() {
		return this.PARAQJXK;
	}

	/**
	 * Return the value of the 条件课程类型 column.
	 *
	 * @return java.lang.String
	 */
	public String getPARAKCLX() {
		return this.PARAKCLX;
	}

	/**
	 * Return the value of the 条件上课时段 column.
	 *
	 * @return java.lang.String
	 */
	public String getPARASKSD() {
		return this.PARASKSD;
	}

	/**
	 * Return the value of the 条件上课区域 column.
	 *
	 * @return java.lang.String
	 */
	public String getPARASKQY() {
		return this.PARASKQY;
	}

	/**
	 * Return the value of the 条件课程费用 column.
	 *
	 * @return java.lang.String
	 */
	public String getPARAKCFY() {
		return this.PARAKCFY;
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

	/**
	 * Set the value of the 班次名称 column.
	 *
	 * @param String
	 *            BCMC
	 */
	public void setBCMC(String BCMC) {
		this.BCMC = BCMC;
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
	 * Set the value of the 学时 column.
	 *
	 * @param String
	 *            XS
	 */
	public void setXS(String XS) {
		this.XS = XS;
	}

	/**
	 * Set the value of the 开课时间 column.
	 *
	 * @param String
	 *            KSSJ
	 */
	public void setKSSJ(String KSSJ) {
		this.KSSJ = KSSJ;
	}

	/**
	 * Set the value of the 结束时间 column.
	 *
	 * @param String
	 *            JSSJ
	 */
	public void setJSSJ(String JSSJ) {
		this.JSSJ = JSSJ;
	}

	/**
	 * Set the value of the 时段ID column.
	 *
	 * @param String
	 *            SDID
	 */
	public void setSDID(String SDID) {
		this.SDID = SDID;
	}

	/**
	 * Set the value of the 时段名称 column.
	 *
	 * @param String
	 *            SDMC
	 */
	public void setSDMC(String SDMC) {
		this.SDMC = SDMC;
	}

	/**
	 * Set the value of the 费用 column.
	 *
	 * @param String
	 *            FY
	 */
	public void setFY(String FY) {
		this.FY = FY;
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
	 * Set the value of the 教师姓名 column.
	 *
	 * @param String
	 *            JSXM
	 */
	public void setJSXM(String JSXM) {
		this.JSXM = JSXM;
	}

	/**
	 * Set the value of the 区域ID column.
	 *
	 * @param String
	 *            SKQYID
	 */
	public void setSKQYID(String SKQYID) {
		this.SKQYID = SKQYID;
	}

	/**
	 * Set the value of the 区域名称 column.
	 *
	 * @param String
	 *            SKQYMC
	 */
	public void setSKQYMC(String SKQYMC) {
		this.SKQYMC = SKQYMC;
	}

	/**
	 * Set the value of the 类型ID column.
	 *
	 * @param String
	 *            LXID
	 */
	public void setLXID(String LXID) {
		this.LXID = LXID;
	}

	/**
	 * Set the value of the 类型名称 column.
	 *
	 * @param String
	 *            LXMC
	 */
	public void setLXMC(String LXMC) {
		this.LXMC = LXMC;
	}

	/**
	 * Set the value of the 班次状态 column.
	 *
	 * @param String
	 *            BCZT
	 */
	public void setBCZT(String BCZT) {
		this.BCZT = BCZT;
	}

	/**
	 * Set the value of the 条件教学学段 column.
	 *
	 * @param String
	 *            PARAJXXD
	 */
	public void setPARAJXXD(String PARAJXXD) {
		this.PARAJXXD = PARAJXXD;
	}

	/**
	 * Set the value of the 条件求教学科 column.
	 *
	 * @param String
	 *            PARAQJXK
	 */
	public void setPARAQJXK(String PARAQJXK) {
		this.PARAQJXK = PARAQJXK;
	}

	/**
	 * Set the value of the 条件课程类型 column.
	 *
	 * @param String
	 *            PARAKCLX
	 */
	public void setPARAKCLX(String PARAKCLX) {
		this.PARAKCLX = PARAKCLX;
	}

	/**
	 * Set the value of the 条件上课时段 column.
	 *
	 * @param String
	 *            PARASKSD
	 */
	public void setPARASKSD(String PARASKSD) {
		this.PARASKSD = PARASKSD;
	}

	/**
	 * Set the value of the 条件上课区域 column.
	 *
	 * @param String
	 *            PARASKQY
	 */
	public void setPARASKQY(String PARASKQY) {
		this.PARASKQY = PARASKQY;
	}

	/**
	 * Set the value of the 条件课程费用 column.
	 *
	 * @param String
	 *            PARAKCFY
	 */
	public void setPARAKCFY(String PARAKCFY) {
		this.PARAKCFY = PARAKCFY;
	}
	
	/**
	*Set the value of the 是否报名 column.
	*
	*@param String KCFY 
	*/
	public void setBMED(String BMED){
		this.BMED=BMED;
	}

}
