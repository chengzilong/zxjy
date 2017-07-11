package com.xsjy.pojo.Custom.pojo_2010000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_HDXX;

public class Pojo2010121  extends Pojo_HDXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 回答人 property.*/
	private String HDR;

	/**The value of the simple 回答得分 property.*/
	private String HDDF;
	
	/**The value of the simple 回答总分 property.*/
	private String HDZF;

	/**
	* Simple constructor of Abstract Pojo2010121 instances.
	*/
	public Pojo2010121(){
	}

	/**
	*Return the value of the 回答人 column.
	*
	*@return java.lang.String
	*/
	public String getHDR(){
		return this.HDR;
	}
	
	/**
	*Return the value of the 回答得分 column.
	*
	*@return java.lang.String
	*/
	public String getHDDF(){
		return this.HDDF;
	}
	
	/**
	*Return the value of the 回答总分 column.
	*
	*@return java.lang.String
	*/
	public String getHDZF(){
		return this.HDZF;
	}
	
	/**
	*Set the value of the 回答人 column.
	*
	*@param String HDR
	*/
	public void setHDR(String HDR){
		this.HDR=HDR;
	}
	
	/**
	*Set the value of the 回答得分 column.
	*
	*@param String HDDF
	*/
	public void setHDDF(String HDDF){
		this.HDDF=HDDF;
	}
	
	/**
	*Set the value of the 回答总分 column.
	*
	*@param String HDZF
	*/
	public void setHDZF(String HDZF){
		this.HDZF=HDZF;
	}
}