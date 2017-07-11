package com.xsjy.pojo.Custom.pojo_1060000;
import java.io.Serializable;
import com.xsjy.pojo.BaseTable.Pojo_PJXX;

public class Pojo1060140  extends Pojo_PJXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 学生姓名 property.*/
	private String PJXX_XSXM;

	/**The value of the simple 评价人 property.*/
	private String PJR;

	/**The value of the simple 被评人 property.*/
	private String BPR;

	
	/**
	* Simple constructor of Abstract Pojo1060140 instances.
	*/
	public Pojo1060140(){
	}

	/**
	*Return the value of the 学生姓名 column.
	*
	*@return java.lang.String
	*/
	public String getPJXX_XSXM(){
		return this.PJXX_XSXM;
	}
	
	/**
	*Return the value of the 评价人 column.
	*
	*@return java.lang.String
	*/
	public String getPJR(){
		return this.PJR;
	}
	
	/**
	*Return the value of the 被评人 column.
	*
	*@return java.lang.String
	*/
	public String getBPR(){
		return this.BPR;
	}
	/**
	*Set the value of the 学生姓名 column.
	*
	*@param String PJXX_XSXM
	*/
	public void setPJXX_XSXM(String PJXX_XSXM){
		this.PJXX_XSXM=PJXX_XSXM;
	}
	
	/**
	*Set the value of the 评价人 column.
	*
	*@param String PJR
	*/
	public void setPJR(String PJR){
		this.PJR=PJR;
	}
	
	/**
	*Set the value of the 被评人 column.
	*
	*@param String BPR
	*/
	public void setBPR(String BPR){
		this.BPR=BPR;
	}
}