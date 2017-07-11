package com.xsjy.servlet.servlet1030000;

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
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030110;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030111;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030112;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030120;
import com.xsjy.service.service1030000.Service1030110;

/**
 * Servlet implementation class Servlet1030110
 */
@WebServlet("/Servlet1030110")
public class Servlet1030110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_SELECTMX = "CMD_SELECTMX";
	public static final String CMD_SELECTRY = "CMD_SELECTRY";
	
	/* 本Servlet对应的Service */
	private Service1030110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1030110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1030110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getNewsList(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertNews(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateNews(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteNews(inputdata);
		}else if (CMD_SELECTMX.equals(CMD)) {
			getNewsmxList(inputdata);
		}else if (CMD_SELECTRY.equals(CMD)) {
			getRenyuanList(inputdata);
		}
	}
	/**
	 * @FunctionName: getNewsList
	 * @Description: 查询消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-16
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
	 * @FunctionName: insertNews
	 * @Description: 创建消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-16
	 */
	private void insertNews(Map<String, String[]> inputdata) throws Exception {
		Pojo1030110 beanIn = (Pojo1030110) this.getObject(inputdata, "BeanIn",Pojo1030110.class);
		Pojo1030111 beanIn1 = (Pojo1030111) this.getObject(inputdata, "BeanIn",Pojo1030111.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setXXXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
			beanIn.setXXXX_FBR(beanUser.getYHXX_UUID());
			beanIn1.setXXMX_CJR(beanUser.getYHXX_YHID());
			beanIn1.setXXMX_GXR(beanUser.getYHXX_YHID());
			ret = service.insertNews(beanIn, beanIn1);
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
	 * @FunctionName: updateNews
	 * @Description: 修改消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-16
	 */
	private void updateNews(Map<String, String[]> inputdata) throws Exception {
		Pojo1030110 beanIn = (Pojo1030110) this.getObject(inputdata, "BeanIn",Pojo1030110.class);
		Pojo1030111 beanIn1 = (Pojo1030111) this.getObject(inputdata, "BeanIn",Pojo1030111.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setXXXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
			beanIn1.setXXMX_CJR(beanUser.getYHXX_YHID());
			beanIn1.setXXMX_GXR(beanUser.getYHXX_YHID());
			ret = service.updateNews(beanIn, beanIn1);
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
	 * @FunctionName: deleteNews
	 * @Description: 删除消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-16
	 */
	private void deleteNews(Map<String, String[]> inputdata) throws Exception {
		Pojo1030110 beanIn = (Pojo1030110) this.getObject(inputdata, "BeanIn",Pojo1030110.class);
		int ret = 0;
		try {
			ret = service.deleteNews(beanIn);
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
		List<Pojo1030112> PageData = new ArrayList<Pojo1030112>();
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
		int TotalCount = 0;
		List<Pojo1030120> PageData = new ArrayList<Pojo1030120>();
		try {
			TotalCount = service.getRenyuanList_TotalCount(beanIn);
			PageData = service.getRenyuanList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
}
