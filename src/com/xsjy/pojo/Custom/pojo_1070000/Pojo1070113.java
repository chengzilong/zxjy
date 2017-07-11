package com.xsjy.pojo.Custom.pojo_1070000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_XZQY;

public class Pojo1070113  extends Pojo_XZQY implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 课程类型名称 property.*/
	private String LXMC;


	/**
	* Simple constructor of Abstract Pojo1070113 instances.
	*/
	public Pojo1070113(){
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
	*Set the value of the 课程类型名称 column.
	*
	*@param String LXMC 
	*/
	public void setLXMC(String LXMC){
		this.LXMC=LXMC;
	}
	
	
}