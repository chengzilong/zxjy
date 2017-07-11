package com.xsjy.pojo.Custom.pojo_3010000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_WTXX;

public class Pojo3010110  extends Pojo_WTXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 课程名称 property.*/
	private String KCMC;

	/**The value of the simple 有效时间 property.*/
	private String YXSJ;
	
	/**The value of the simple 回答数量 property.*/
	private String HDSL;
	
	/**The value of the simple 提问人 property.*/
	private String TWR;
	
	/**
	* Simple constructor of Abstract Pojo3010110 instances.
	*/
	public Pojo3010110(){
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
	*Return the value of the 提问人 column.
	*
	*@return java.lang.String
	*/
	public String getTWR(){
		return this.TWR;
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
	*Set the value of the 提问人 column.
	*
	*@param String TWR
	*/
	public void setTWR(String TWR){
		this.TWR=TWR;
	}
}