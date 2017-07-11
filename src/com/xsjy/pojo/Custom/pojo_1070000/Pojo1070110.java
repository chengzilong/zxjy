package com.xsjy.pojo.Custom.pojo_1070000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_JSXX;

public class Pojo1070110  extends Pojo_JSXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 性别 property.*/
	private String JSXX_XBMC;
	
	/**The value of the simple 教师资格 property.*/
	private String JSXX_ZGMC;
	
	/**The value of the simple 身份证图片 property.*/
	private String SFZ;
	
	/**The value of the simple 资格证图片 property.*/
	private String ZGZ;

	/**
	* Simple constructor of Abstract Pojo1070110 instances.
	*/
	public Pojo1070110(){
	}

	/**
	*Return the value of the 性别 column.
	*
	*@return java.lang.String
	*/
	public String getJSXX_XBMC(){
		return this.JSXX_XBMC;
	}
	
	/**
	*Return the value of the 教师资格 column.
	*
	*@return java.lang.String
	*/
	public String getJSXX_ZGMC(){
		return this.JSXX_ZGMC;
	}
	
	/**
	*Return the value of the 身份证图片 column.
	*
	*@return java.lang.String
	*/
	public String getSFZ(){
		return this.SFZ;
	}
	
	/**
	*Return the value of the 资格证图片 column.
	*
	*@return java.lang.String
	*/
	public String getZGZ(){
		return this.ZGZ;
	}
	
	/**
	*Set the value of the 性别 column.
	*
	*@param String JSXX_XBMC
	*/
	public void setJSXX_XBMC(String JSXX_XBMC){
		this.JSXX_XBMC=JSXX_XBMC;
	}
	
	/**
	*Set the value of the 教师资格 column.
	*
	*@param String JSXX_ZGMC
	*/
	public void setJSXX_ZGMC(String JSXX_ZGMC){
		this.JSXX_ZGMC=JSXX_ZGMC;
	}
	
	/**
	*Set the value of the 身份证图片 column.
	*
	*@param String SFZ
	*/
	public void setSFZ(String SFZ){
		this.SFZ=SFZ;
	}
	
	/**
	*Set the value of the 资格证图片 column.
	*
	*@param String ZGZ
	*/
	public void setZGZ(String ZGZ){
		this.ZGZ=ZGZ;
	}
}