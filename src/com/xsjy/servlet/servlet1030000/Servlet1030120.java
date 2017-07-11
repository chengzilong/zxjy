package com.xsjy.servlet.servlet1030000;

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
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030110;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030120;
import com.xsjy.service.service1030000.Service1030120;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1030120
 */
@WebServlet("/Servlet1030120")
public class Servlet1030120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECTMX = "CMD_SELECTMX";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_SELECTRY = "CMD_SELECTRY";
	public static final String CMD_SEND = "CMD_SEND";
	/* 本Servlet对应的Service */
	private Service1030120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1030120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1030120();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getNewsList(inputdata);
		}else if (CMD_SELECTMX.equals(CMD)) {
			getNewsmxList(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteNewsmx(inputdata);
		}else if (CMD_SELECTRY.equals(CMD)) {
			getRenyuanList(inputdata);
		}else if (CMD_SEND.equals(CMD)) {
			sendNews(inputdata);
		}
	}
	/**
	 * @FunctionName: getNewsList
	 * @Description: 查询消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-17
	 */
	private void getNewsList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1030110 beanIn = (Pojo1030110) this.getObject(inputdata, "beanLoad",
				Pojo1030110.class);

		int TotalCount = 0;
		List<Pojo1030110> PageData = new ArrayList<Pojo1030110>();
		try {
			TotalCount = service.getNewsList_TotalCount(beanIn);
			PageData = service.getNewsList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: getNewsmxList
	 * @Description: 查询消息明细
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-17
	 */
	private void getNewsmxList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		String strXXID = this.getString(inputdata, "XXID");
		int TotalCount = 0;
		List<Pojo1030120> PageData = new ArrayList<Pojo1030120>();
		try {
			TotalCount = service.getNewsmxList_TotalCount(strXXID);
			PageData = service.getNewsmxList_PageData(strXXID, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: deleteNewsmx
	 * @Description: 删除消息明细
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-17
	 */
	private void deleteNewsmx(Map<String, String[]> inputdata) throws Exception {
		String strMXID = this.getString(inputdata, "MXID");
		int ret = 0;
		try {
			ret = service.deleteNewsmx(strMXID);
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
	 * @FunctionName: getRenyuanList
	 * @Description: 查询人员
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-17
	 */
	private void getRenyuanList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1030120 beanIn = (Pojo1030120) this.getObject(inputdata, "beanLoad",
				Pojo1030120.class);
		String strTag = this.getString(inputdata, "strTag");
		int TotalCount = 0;
		List<Pojo1030120> PageData = new ArrayList<Pojo1030120>();
		try {
			if(strTag.equals("1")){
				TotalCount = service.getRenyuanList_TotalCount(beanIn);
				PageData = service.getRenyuanList_PageData(beanIn, page, limit, sort);
			}else{
		
			}
			
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: sendNews
	 * @Description: 发布消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-16
	 */
	private void sendNews(Map<String, String[]> inputdata) throws Exception {
		Pojo1030120 beanIn = (Pojo1030120) this.getObject(inputdata, "BeanIn",Pojo1030120.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);	
		try {
			beanIn.setXXMX_CJR(beanUser.getYHXX_YHID());
			beanIn.setXXMX_GXR(beanUser.getYHXX_YHID());
			ret = service.sendNews(beanIn);
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
}
