package com.xsjy.pojo.Custom.pojo_1110000;
import java.io.Serializable;

import com.xsjy.pojo.BaseTable.Pojo_YHXX;
public class Pojo1110150  extends Pojo_YHXX implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**The value of the simple 角色名称 property.*/
	private String YHXX_JSMC;


	/**
	* Simple constructor of Abstract Pojo1110150 instances.
	*/
	public Pojo1110150(){
	}

	/**
	*Return the value of the 角色名称 column.
	*
	*@return java.lang.String
	*/
	public String getYHXX_JSMC(){
		return this.YHXX_JSMC;
	}

	
	/**
	*Set the value of the 备用 column.
	*
	*@param String YHXX_JSMC 
	*/
	public void setYHXX_JSMC(String YHXX_JSMC){
		this.YHXX_JSMC=YHXX_JSMC;
	}
}