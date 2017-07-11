package com.xsjy.pojo.Custom.pojo_9010000;
import java.io.Serializable;

import com.xsjy.pojo.BaseTable.Pojo_PXWD;
public class Pojo9010150  extends Pojo_PXWD implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**The value of the simple 备用 property.*/
	private String PXWD_BY;


	/**
	* Simple constructor of Abstract BeanPXWD instances.
	*/
	public Pojo9010150(){
	}

	/**
	*Return the value of the 备用 column.
	*
	*@return java.lang.String
	*/
	public String getPXWD_BY(){
		return this.PXWD_BY;
	}

	
	/**
	*Set the value of the 备用 column.
	*
	*@param String PXWD_BY 
	*/
	public void setPXWD_BY(String PXWD_BY){
		this.PXWD_BY=PXWD_BY;
	}
}