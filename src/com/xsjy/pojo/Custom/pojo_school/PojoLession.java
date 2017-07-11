package com.xsjy.pojo.Custom.pojo_school;

import java.io.Serializable;

import com.xsjy.pojo.BaseTable.Pojo_KCFY;

public class PojoLession extends Pojo_KCFY  implements Serializable{
	private static final long serialVersionUID = 1L;

	/** The value of the simple 课程名称 property. */
	private String KCMC;

	/** The value of the simple 课程类型名称 property. */
	private String LXMC;

	/** The value of the simple 上课时段 property. */
	private String SDMC;

	/** The value of the simple 教师姓名 property. */
	private String JSXM;
	
	/**The value of the simple 教学学段 property.*/
	private String JXXD;

	/**The value of the simple 求教学科 property.*/
	private String QJXK;

	/**The value of the simple 课程类型 property.*/
	private String KCLX;

	/**The value of the simple 上课时段 property.*/
	private String SKSD;

	/**The value of the simple 授课区域 property.*/
	private String SKQY;

	/**The value of the simple 课程费用 property.*/
	private String KCFY;
	
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
	 * Return the value of the 课程名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getKCMC() {
		return this.KCMC;
	}

	/**
	 * Return the value of the 课程类型名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getLXMC() {
		return this.LXMC;
	}

	/**
	 * Return the value of the 上课时段 column.
	 * 
	 * @return java.lang.String
	 */
	public String getSDMC() {
		return this.SDMC;
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
	 * Set the value of the 课程名称 column.
	 * 
	 * @param String
	 *            KCMC
	 */
	public void setKCMC(String KCMC) {
		this.KCMC = KCMC;
	}

	/**
	 * Set the value of the 课程类型名称 column.
	 * 
	 * @param String
	 *            LXMC
	 */
	public void setLXMC(String LXMC) {
		this.LXMC = LXMC;
	}

	/**
	 * Set the value of the 上课时段 column.
	 * 
	 * @param String
	 *            SDMC
	 */
	public void setSDMC(String SDMC) {
		this.SDMC = SDMC;
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
	*Return the value of the 教学学段 column.
	*
	*@return java.lang.String
	*/
	public String getJXXD(){
		return this.JXXD;
	}

	/**
	*Return the value of the 求教学科 column.
	*
	*@return java.lang.String
	*/
	public String getQJXK(){
		return this.QJXK;
	}

	/**
	*Return the value of the 课程类型 column.
	*
	*@return java.lang.String
	*/
	public String getKCLX(){
		return this.KCLX;
	}

	/**
	*Return the value of the 上课时段 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD(){
		return this.SKSD;
	}

	/**
	*Return the value of the 授课区域 column.
	*
	*@return java.lang.String
	*/
	public String getSKQY(){
		return this.SKQY;
	}

	/**
	*Return the value of the 课程费用 column.
	*
	*@return java.lang.String
	*/
	public String getKCFY(){
		return this.KCFY;
	}

	/**
	*Set the value of the 教学学段 column.
	*
	*@param String JXXD 
	*/
	public void setJXXD(String JXXD){
		this.JXXD=JXXD;
	}

	/**
	*Set the value of the 求教学科 column.
	*
	*@param String QJXK 
	*/
	public void setQJXK(String QJXK){
		this.QJXK=QJXK;
	}

	/**
	*Set the value of the 课程类型 column.
	*
	*@param String KCLX 
	*/
	public void setKCLX(String KCLX){
		this.KCLX=KCLX;
	}

	/**
	*Set the value of the 上课时段 column.
	*
	*@param String SKSD 
	*/
	public void setSKSD(String SKSD){
		this.SKSD=SKSD;
	}

	/**
	*Set the value of the 授课区域 column.
	*
	*@param String SKQY 
	*/
	public void setSKQY(String SKQY){
		this.SKQY=SKQY;
	}

	/**
	*Set the value of the 课程费用 column.
	*
	*@param String KCFY 
	*/
	public void setKCFY(String KCFY){
		this.KCFY=KCFY;
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
