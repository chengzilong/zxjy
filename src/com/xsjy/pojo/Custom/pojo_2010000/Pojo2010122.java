package com.xsjy.pojo.Custom.pojo_2010000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_JJXX;

public class Pojo2010122  extends Pojo_JJXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 回答人 property.*/
	private String HDR;


	/**
	* Simple constructor of Abstract Pojo2010122 instances.
	*/
	public Pojo2010122(){
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
	*Set the value of the 回答人 column.
	*
	*@param String HDR
	*/
	public void setHDR(String HDR){
		this.HDR=HDR;
	}
	
}