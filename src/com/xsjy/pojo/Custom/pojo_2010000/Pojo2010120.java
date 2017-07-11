package com.xsjy.pojo.Custom.pojo_2010000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_WTXX;

public class Pojo2010120  extends Pojo_WTXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 课程名称 property.*/
	private String KCMC;

	/**The value of the simple 有效时间 property.*/
	private String YXSJ;
	
	/**The value of the simple 回答数量 property.*/
	private String HDSL;
	
	/**The value of the simple 原提问积分 property.*/
	private String TWJF;
	
	/**
	* Simple constructor of Abstract Pojo2010120 instances.
	*/
	public Pojo2010120(){
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
	*Return the value of the 有效时间 column.
	*
	*@return java.lang.String
	*/
	public String getYXSJ(){
		return this.YXSJ;
	}
	
	/**
	*Return the value of the 回答数量 column.
	*
	*@return java.lang.String
	*/
	public String getHDSL(){
		return this.HDSL;
	}

	/**
	*Return the value of the 原提问积分 column.
	*
	*@return java.lang.String
	*/
	public String getTWJF(){
		return this.TWJF;
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
	*Set the value of the 有效时间 column.
	*
	*@param String YXSJ
	*/
	public void setYXSJ(String YXSJ){
		this.YXSJ=YXSJ;
	}
	
	/**
	*Set the value of the 回答数量 column.
	*
	*@param String HDSL
	*/
	public void setHDSL(String HDSL){
		this.HDSL=HDSL;
	}
	
	/**
	*Set the value of the 原提问积分 column.
	*
	*@param String TWJF
	*/
	public void setTWJF(String TWJF){
		this.TWJF=TWJF;
	}
}