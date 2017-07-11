package com.xsjy.pojo.Custom.pojo_1110000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_YGXX;

public class Pojo1110110  extends Pojo_YGXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 用户ID property.*/
	private String YHID;
	
	/**The value of the simple 用户名称 property.*/
	private String YHMC;
	
	/**
	* Simple constructor of Abstract Pojo1110110 instances.
	*/
	public Pojo1110110(){
	}

	/**
	*Return the value of the 用户ID column.
	*
	*@return java.lang.String
	*/
	public String getYHID(){
		return this.YHID;
	}
	
	/**
	*Return the value of the 用户名称 column.
	*
	*@return java.lang.String
	*/
	public String getYHMC(){
		return this.YHMC;
	}
	

	/**
	*Set the value of the 用户ID column.
	*
	*@param String YHID
	*/
	public void setYHID(String YHID){
		this.YHID=YHID;
	}
	
	/**
	*Set the value of the 用户名称 column.
	*
	*@param String YHMC
	*/
	public void setYHMC(String YHMC){
		this.YHMC=YHMC;
	}
}