package com.xsjy.pojo.Custom.pojo_1060000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_JJMX;

public class Pojo1060121  extends Pojo_JJMX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 性别 property.*/
	private String JSXX_XBMC;

	/**
	* Simple constructor of Abstract Pojo1060121 instances.
	*/
	public Pojo1060121(){
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
	*Set the value of the 性别 column.
	*
	*@param String JSXX_XBMC
	*/
	public void setJSXX_XBMC(String JSXX_XBMC){
		this.JSXX_XBMC=JSXX_XBMC;
	}
}