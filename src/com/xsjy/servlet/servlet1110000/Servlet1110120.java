package com.xsjy.servlet.servlet1110000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110120;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070111;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070112;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070113;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070114;
import com.xsjy.service.service1110000.Service1110120;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1110120
 */
@WebServlet("/Servlet1110120")
public class Servlet1110120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_DATA = "CMD_DATA";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_ALLKMLIST = "CMD_ALLKMLIST";
	public static final String CMD_ALLQYLIST = "CMD_ALLQYLIST";
	public static final String CMD_OWNKMLIST = "CMD_OWNLKMLIST";
	public static final String CMD_OWNQYLIST = "CMD_OWNQYLIST";
	public static final String CMD_OWNKMLISTINFO = "CMD_OWNKMLISTINFO";
	/* 本Servlet对应的Service */
	private Service1110120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1110120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1110120();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_DATA.equals(CMD)) {
			getTeacherInfo(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateTeacher(inputdata);
		}else if (CMD_ALLKMLIST.equals(CMD)) {
			getAllKmList(inputdata);
		}else if (CMD_ALLQYLIST.equals(CMD)) {
			getAllQyList(inputdata);
		}else if (CMD_OWNKMLIST.equals(CMD)) {
			getOwnKmList(inputdata);
		}else if (CMD_OWNQYLIST.equals(CMD)) {
			getOwnQyList(inputdata);
		}else if (CMD_OWNKMLISTINFO.equals(CMD)) {
			getOwnKmListinfo(inputdata);
		}
	}
	/**
	 * @FunctionName: getTeacherInfo
	 * @Description: 查询教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-07
	 */
	private void getTeacherInfo(Map<String, String[]> inputdata) throws Exception {
		
		Pojo1110120 Data = null;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			Data = service.getTeacherInfo(beanUser.getYHXX_YHID());
			if(Data != null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(Data);
			}else
			{
				arrResult.add("DATA_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: updateTeacher
	 * @Description: 修改教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-07
	 */
	private void updateTeacher(Map<String, String[]> inputdata) throws Exception {
		String kcmcs = this.getString(inputdata, "KCMCS");
		kcmcs = kcmcs.substring(0, kcmcs.length() - 1);
		String qymcs = this.getString(inputdata, "QYMCS");
		qymcs = qymcs.substring(0, qymcs.length() - 1);
		Pojo1110120 beanIn = (Pojo1110120) this.getObject(inputdata, "BeanIn",Pojo1110120.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setJSXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setJSXX_GXR(beanUser.getYHXX_YHID());
			ret = service.updateTeacher(beanIn,kcmcs,qymcs);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: getAllKmList
	 * @Description: 获取所有科目列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-11
	 */
	private void getAllKmList(Map<String, String[]> inputdata) throws Exception {
		List<Pojo1070111> PageData = new ArrayList<Pojo1070111>();
		try {
			PageData = service.getAllKmList();
			if(PageData!=null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(PageData);
			}else
			{
				arrResult.add("LIST_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: getOwnKmList
	 * @Description: 获取擅长科目列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-11
	 */
	private void getOwnKmList(Map<String, String[]> inputdata) throws Exception {
		String strJSID = this.getString(inputdata, "JSID");// 教师ID
		List<Pojo1070112> PageData = new ArrayList<Pojo1070112>();
		try {
			PageData = service.getOwnKmList(strJSID);
			if(PageData!=null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(PageData);
			}else
			{
				arrResult.add("LIST_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: getOwnKmListinfo
	 * @Description: 获取擅长科目列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-25
	 */
	private void getOwnKmListinfo(Map<String, String[]> inputdata) throws Exception {
		String strJSID = this.getString(inputdata, "JSID");// 教师ID
		List<Pojo1070112> PageData = new ArrayList<Pojo1070112>();
		try {
			PageData = service.getOwnKmListinfo(strJSID);
			if(PageData!=null)
			{
				if(PageData.size()==0){
					arrResult.add("SUCCESS");
					arrResult.add(PageData);
					arrResult.add("NOKM");
				}else{
					arrResult.add("SUCCESS");
					arrResult.add(PageData);
					arrResult.add("");
				}
				
			}else
			{
				arrResult.add("LIST_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: getAllQyList
	 * @Description: 获取所有区域列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-29
	 */
	private void getAllQyList(Map<String, String[]> inputdata) throws Exception {
		List<Pojo1070113> PageData = new ArrayList<Pojo1070113>();
		try {
			PageData = service.getAllQyList();
			if(PageData!=null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(PageData);
			}else
			{
				arrResult.add("LIST_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: getOwnQyList
	 * @Description: 获取授课区域列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-29
	 */
	private void getOwnQyList(Map<String, String[]> inputdata) throws Exception {
		String strJSID = this.getString(inputdata, "JSID");// 教师ID
		List<Pojo1070114> PageData = new ArrayList<Pojo1070114>();
		try {
			PageData = service.getOwnQyList(strJSID);
			if(PageData!=null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(PageData);
			}else
			{
				arrResult.add("LIST_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}
