package com.xsjy.servlet.servlet1070000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070110;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070111;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070112;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070113;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070114;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070115;
import com.xsjy.service.service1070000.Service1070110;

/**
 * Servlet implementation class Servlet1070110
 */
@WebServlet("/Servlet1070110")
public class Servlet1070110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_ALLKMLIST = "CMD_ALLKMLIST";
	public static final String CMD_ALLQYLIST = "CMD_ALLQYLIST";
	public static final String CMD_OWNKMLIST = "CMD_OWNLKMLIST";
	public static final String CMD_OWNQYLIST = "CMD_OWNQYLIST";
	public static final String CMD_OWNKMLISTINFO = "CMD_OWNKMLISTINFO";
	public static final String CMD_JSTP = "CMD_JSTP";
	/* 本Servlet对应的Service */
	private Service1070110 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1070110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1070110();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getTeacherList(inputdata);
		}else if (CMD_CHK_EXIST.equals(CMD)) {
			checkUserexist(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertTeacher(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateTeacher(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteTeacher(inputdata);
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
		}else if (CMD_JSTP.equals(CMD)) {
			getJstp(inputdata);
		}
	}
	/**
	 * @FunctionName: getTeacherList
	 * @Description: 查询教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-10
	 */
	private void getTeacherList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1070110 beanIn = (Pojo1070110) this.getObject(inputdata, "beanLoad",
				Pojo1070110.class);

		int TotalCount = 0;
		List<Pojo1070110> PageData = new ArrayList<Pojo1070110>();
		try {
			TotalCount = service.getTeacherList_TotalCount(beanIn);
			PageData = service.getTeacherList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: checkUserexist
	 * @Description: 验证用户是否已经注册
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-31
	 */
	private void checkUserexist(Map<String, String[]> inputdata) throws Exception {
		String strSJHM = this.getString(inputdata, "SJHM");// 用户ID
		try {
			int TotalCount = service.checkUserexist(strSJHM);
			if(TotalCount > 0){
				arrResult.add("USER_EXIST");
			}else{
				arrResult.add("USER_NOEXIST");
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
	 * @FunctionName: insertTeacher
	 * @Description: 新增教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-31
	 */
	private void insertTeacher(Map<String, String[]> inputdata) throws Exception {
		String kcmcs = this.getString(inputdata, "KCMCS");
		kcmcs = kcmcs.substring(0, kcmcs.length() - 1);
		String qymcs = this.getString(inputdata, "QYMCS");
		qymcs = qymcs.substring(0, qymcs.length() - 1);
		Pojo1070110 beanIn = (Pojo1070110) this.getObject(inputdata, "BeanIn",Pojo1070110.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setJSXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setJSXX_GXR(beanUser.getYHXX_YHID());
			ret = service.insertTeacher(beanIn,kcmcs,qymcs);
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
	 * @FunctionName: updateTeacher
	 * @Description: 修改教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-31
	 */
	private void updateTeacher(Map<String, String[]> inputdata) throws Exception {
		String kcmcs = this.getString(inputdata, "KCMCS");
		kcmcs = kcmcs.substring(0, kcmcs.length() - 1);
		String qymcs = this.getString(inputdata, "QYMCS");
		qymcs = qymcs.substring(0, qymcs.length() - 1);
		Pojo1070110 beanIn = (Pojo1070110) this.getObject(inputdata, "BeanIn",Pojo1070110.class);
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
	 * @FunctionName: deleteTeacher
	 * @Description: 删除教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-31
	 */
	private void deleteTeacher(Map<String, String[]> inputdata) throws Exception {
		Pojo1070110 beanIn = (Pojo1070110) this.getObject(inputdata, "BeanIn",Pojo1070110.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		int ret = 0;
		try {
			beanIn.setJSXX_GXR(beanUser.getYHXX_YHID());
			ret = service.deleteTeacher(beanIn);
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
	 * @date 2017-07-31
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
	 * @date 2017-07-31
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
	 * @date 2017-07-31
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
	 * @date 2017-07-31
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
	 * @date 2017-07-31
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
	/**
	 * @FunctionName: getJstp
	 * @Description: 获取教师图片
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-31
	 */
	private void getJstp(Map<String, String[]> inputdata) throws Exception {
		Pojo1070115 Data = null;
		String jsid = this.getString(inputdata, "JSID");
		try {
			Data = service.getJstp(jsid);
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
}
