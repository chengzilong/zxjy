package com.xsjy.pojo.Custom.pojo_1070000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_JJXX;

public class Pojo1070120  extends Pojo_JJXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 性别 property.*/
	private String JSXX_XBMC;

	/**
	* Simple constructor of Abstract Pojo1070120 instances.
	*/
	public Pojo1070120(){
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