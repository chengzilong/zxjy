package com.xsjy.pojo.BaseTable;

import java.io.Serializable;

public class Pojo_SKSD implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 时段ID property.*/
	private String SKSD_SDID;

	/**The value of the simple 时段名称 property.*/
	private String SKSD_SDMC;
	
	/**The value of the simple 时段简称 property.*/
	private String SKSD_SDJC;

	/**The value of the simple 删除标志 property.*/
	private String SKSD_SCBZ;

	/**The value of the simple 创建人 property.*/
	private String SKSD_CJR;

	/**The value of the simple 创建时间 property.*/
	private String SKSD_CJSJ;

	/**The value of the simple 更新人 property.*/
	private String SKSD_GXR;

	/**The value of the simple 更新时间 property.*/
	private String SKSD_GXSJ;

	/**
	* Simple constructor of Abstract BeanSKSD instances.
	*/
	public Pojo_SKSD(){
	}

	/**
	*Return the value of the 时段ID column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_SDID(){
		return this.SKSD_SDID;
	}

	/**
	*Return the value of the 时段名称 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_SDMC(){
		return this.SKSD_SDMC;
	}
	
	/**
	*Return the value of the 时段简称 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_SDJC(){
		return this.SKSD_SDJC;
	}

	/**
	*Return the value of the 删除标志 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_SCBZ(){
		return this.SKSD_SCBZ;
	}

	/**
	*Return the value of the 创建人 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_CJR(){
		return this.SKSD_CJR;
	}

	/**
	*Return the value of the 创建时间 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_CJSJ(){
		return this.SKSD_CJSJ;
	}

	/**
	*Return the value of the 更新人 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_GXR(){
		return this.SKSD_GXR;
	}

	/**
	*Return the value of the 更新时间 column.
	*
	*@return java.lang.String
	*/
	public String getSKSD_GXSJ(){
		return this.SKSD_GXSJ;
	}

	/**
	*Set the value of the 时段ID column.
	*
	*@param String SKSD_SDID 
	*/
	public void setSKSD_SDID(String SKSD_SDID){
		this.SKSD_SDID=SKSD_SDID;
	}

	/**
	*Set the value of the 时段名称 column.
	*
	*@param String SKSD_SDMC 
	*/
	public void setSKSD_SDMC(String SKSD_SDMC){
		this.SKSD_SDMC=SKSD_SDMC;
	}

	/**
	*Set the value of the 时段简称 column.
	*
	*@param String SKSD_SDJC 
	*/
	public void setSKSD_SDJC(String SKSD_SDJC){
		this.SKSD_SDJC=SKSD_SDJC;
	}
	
	/**
	*Set the value of the 删除标志 column.
	*
	*@param String SKSD_SCBZ 
	*/
	public void setSKSD_SCBZ(String SKSD_SCBZ){
		this.SKSD_SCBZ=SKSD_SCBZ;
	}

	/**
	*Set the value of the 创建人 column.
	*
	*@param String SKSD_CJR 
	*/
	public void setSKSD_CJR(String SKSD_CJR){
		this.SKSD_CJR=SKSD_CJR;
	}

	/**
	*Set the value of the 创建时间 column.
	*
	*@param String SKSD_CJSJ 
	*/
	public void setSKSD_CJSJ(String SKSD_CJSJ){
		this.SKSD_CJSJ=SKSD_CJSJ;
	}

	/**
	*Set the value of the 更新人 column.
	*
	*@param String SKSD_GXR 
	*/
	public void setSKSD_GXR(String SKSD_GXR){
		this.SKSD_GXR=SKSD_GXR;
	}

	/**
	*Set the value of the 更新时间 column.
	*
	*@param String SKSD_GXSJ 
	*/
	public void setSKSD_GXSJ(String SKSD_GXSJ){
		this.SKSD_GXSJ=SKSD_GXSJ;
	}

}
