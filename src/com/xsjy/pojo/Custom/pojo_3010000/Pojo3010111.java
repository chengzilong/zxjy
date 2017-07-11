package com.xsjy.pojo.Custom.pojo_3010000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_HDXX;

public class Pojo3010111  extends Pojo_HDXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 回答人 property.*/
	private String HDR;


	/**
	* Simple constructor of Abstract Pojo3010111 instances.
	*/
	public Pojo3010111(){
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