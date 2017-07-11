package com.xsjy.pojo.Custom.pojo_1040000;

import com.xsjy.pojo.BaseTable.Pojo_JSXX;

public class Pojo1040131 extends Pojo_JSXX {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 班次ID property. */
	private String BCID;
	
	/**The value of the simple 教师性别 property.*/
	private String JSXB;
	
	/**The value of the simple 是否认证 property.*/
	private String SFRZ;
	
	/**The value of the simple 是否有课 property.*/
	private String SFYK;
	
	/** The value of the simple 授课区域 property. */
	private String SKQY;

	/** The value of the simple 授课科目 property. */
	private String SKKM;

	/**
	 * Simple constructor of Abstract BeanPojo1040141 instances.
	 */
	public Pojo1040131() {
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
	*Return the value of the 教师性别 column.
	*
	*@return java.lang.String
	*/
	public String getJSXB(){
		return this.JSXB;
	}
	
	/**
	*Return the value of the 是否认证 column.
	*
	*@return java.lang.String
	*/
	public String getSFRZ(){
		return this.SFRZ;
	}
	
	/**
	*Return the value of the 是否有课 column.
	*
	*@return java.lang.String
	*/
	public String getSFYK(){
		return this.SFYK;
	}
	
	/**
	 * Return the value of the 授课区域 column.
	 * 
	 * @return java.lang.String
	 */
	public String getSKQY() {
		return this.SKQY;
	}

	/**
	 * Return the value of the 授课科目 column.
	 * 
	 * @return java.lang.String
	 */
	public String getSKKM() {
		return this.SKKM;
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
	*Set the value of the 教师性别 column.
	*
	*@param String JSXB 
	*/
	public void setJSXB(String JSXB){
		this.JSXB=JSXB;
	}
	
	/**
	*Set the value of the 是否认证 column.
	*
	*@param String SFRZ 
	*/
	public void setSFRZ(String SFRZ){
		this.SFRZ=SFRZ;
	}
	
	/**
	*Set the value of the 是否有课 column.
	*
	*@param String SFYK 
	*/
	public void setSFYK(String SFYK){
		this.SFYK=SFYK;
	}
	
	/**
	 * Set the value of the 授课区域 column.
	 * 
	 * @param String
	 *            SKQY
	 */
	public void setSKQY(String SKQY) {
		this.SKQY = SKQY;
	}

	/**
	 * Set the value of the 授课科目 column.
	 * 
	 * @param String
	 *            SKKM
	 */
	public void setSKKM(String SKKM) {
		this.SKKM = SKKM;
	}
}
