package com.xsjy.pojo.Custom.pojo_1080000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_JFMX;

public class Pojo1080121  extends Pojo_JFMX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 费用 property.*/
	private String JFMX_FY;

	/**
	* Simple constructor of Abstract Pojo1080121 instances.
	*/
	public Pojo1080121(){
	}

	/**
	*Return the value of the 费用 column.
	*
	*@return java.lang.String
	*/
	public String getJFMX_FY(){
		return this.JFMX_FY;
	}
	
	
	/**
	*Set the value of the 费用 column.
	*
	*@param String JFMX_FY
	*/
	public void setJFMX_FY(String JFMX_FY){
		this.JFMX_FY=JFMX_FY;
	}
	
}