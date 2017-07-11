package com.xsjy.pojo.Custom.pojo_2020000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_BCXX;

public class Pojo2020110  extends Pojo_BCXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 开始时间 property.*/
	private String BEGIN;
	
	/**The value of the simple 结束时间 property.*/
	private String END;
	
	/**The value of the simple 课程名称 property.*/
	private String KCMC;
	
	/**The value of the simple 班次状态 property.*/
	private String BCZT;
	/**
	* Simple constructor of Abstract Pojo2020110 instances.
	*/
	public Pojo2020110(){
	}

	/**
	*Return the value of the 开始时间 column.
	*
	*@return java.lang.String
	*/
	public String getBEGIN(){
		return this.BEGIN;
	}
	
	/**
	*Return the value of the 结束时间 column.
	*
	*@return java.lang.String
	*/
	public String getEND(){
		return this.END;
	}
	
	/**
	*Return the value of the 课程名称 column.
	*
	*@return java.lang.String
	*/
	public String getKCMC(){
		return this.KCMC;
	}
	
	/**
	*Return the value of the 班次状态 column.
	*
	*@return java.lang.String
	*/
	public String getBCZT(){
		return this.BCZT;
	}
	
	/**
	*Set the value of the 开始时间 column.
	*
	*@param String BEGIN
	*/
	public void setBEGIN(String BEGIN){
		this.BEGIN=BEGIN;
	}
	
	/**
	*Set the value of the 结束时间 column.
	*
	*@param String END
	*/
	public void setEND(String END){
		this.END=END;
	}
	
	/**
	*Set the value of the 课程名称 column.
	*
	*@param String KCMC
	*/
	public void setKCMC(String KCMC){
		this.KCMC=KCMC;
	}
	
	/**
	*Set the value of the 班次状态 column.
	*
	*@param String BCZT
	*/
	public void setBCZT(String BCZT){
		this.BCZT=BCZT;
	}
}