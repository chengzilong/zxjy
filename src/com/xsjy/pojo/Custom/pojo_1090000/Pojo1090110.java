package com.xsjy.pojo.Custom.pojo_1090000;

import com.xsjy.pojo.BaseTable.Pojo_KCXX;

public class Pojo1090110 extends Pojo_KCXX {
	private static final long serialVersionUID = 1L;
	
	/**The value of the simple 阶段名称 property.*/
	private String JDMC;

	/**The value of the simple 科目名称 property.*/
	private String KMMC;

	/**The value of the simple 阶段科目全称 property.*/
	private String JDKMQC;

	/**The value of the simple 删除标志 property.*/
	private String SCBZ;

	/**
	* Simple constructor of Abstract BeanPojo1090110 instances.
	*/
	public Pojo1090110(){
	}

	/**
	*Return the value of the 阶段名称 column.
	*
	*@return java.lang.String
	*/
	public String getJDMC(){
		return this.JDMC;
	}

	/**
	*Return the value of the 科目名称 column.
	*
	*@return java.lang.String
	*/
	public String getKMMC(){
		return this.KMMC;
	}

	/**
	*Return the value of the 阶段科目全称 column.
	*
	*@return java.lang.String
	*/
	public String getJDKMQC(){
		return this.JDKMQC;
	}

	/**
	*Return the value of the 删除标志 column.
	*
	*@return java.lang.String
	*/
	public String getSCBZ(){
		return this.SCBZ;
	}

	/**
	*Set the value of the 阶段名称 column.
	*
	*@param String JDMC 
	*/
	public void setJDMC(String JDMC){
		this.JDMC=JDMC;
	}

	/**
	*Set the value of the 科目名称 column.
	*
	*@param String KMMC 
	*/
	public void setKMMC(String KMMC){
		this.KMMC=KMMC;
	}

	/**
	*Set the value of the 阶段科目全称 column.
	*
	*@param String JDKMQC 
	*/
	public void setJDKMQC(String JDKMQC){
		this.JDKMQC=JDKMQC;
	}

	/**
	*Set the value of the 删除标志 column.
	*
	*@param String SCBZ 
	*/
	public void setSCBZ(String SCBZ){
		this.SCBZ=SCBZ;
	}
}
