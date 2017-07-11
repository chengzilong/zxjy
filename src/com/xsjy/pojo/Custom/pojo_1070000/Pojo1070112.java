package com.xsjy.pojo.Custom.pojo_1070000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_JSKM;

public class Pojo1070112  extends Pojo_JSKM implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 课程类型名称 property.*/
	private String LXMC;

	/**The value of the simple 上课时段 property.*/
	private String SDMC;
	
	/**The value of the simple 教师姓名 property.*/
	private String JSMC;

	/**
	* Simple constructor of Abstract Pojo1070112 instances.
	*/
	public Pojo1070112(){
	}

	/**
	*Return the value of the 课程类型名称 column.
	*
	*@return java.lang.String
	*/
	public String getLXMC(){
		return this.LXMC;
	}

	/**
	*Return the value of the 上课时段 column.
	*
	*@return java.lang.String
	*/
	public String getSDMC(){
		return this.SDMC;
	}
	
	/**
	*Return the value of the 教师姓名 column.
	*
	*@return java.lang.String
	*/
	public String getJSMC(){
		return this.JSMC;
	}
	
	/**
	*Set the value of the 课程类型名称 column.
	*
	*@param String LXMC 
	*/
	public void setLXMC(String LXMC){
		this.LXMC=LXMC;
	}
	
	/**
	*Set the value of the 上课时段 column.
	*
	*@param String SDMC 
	*/
	public void setSDMC(String SDMC){
		this.SDMC=SDMC;
	}
	
	/**
	*Set the value of the 教师姓名 column.
	*
	*@param String JSMC 
	*/
	public void setJSMC(String JSMC){
		this.JSMC=JSMC;
	}
}