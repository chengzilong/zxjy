package com.xsjy.servlet.servlet1070000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070120;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070121;
import com.xsjy.service.service1070000.Service1070120;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1070120
 */
@WebServlet("/Servlet1070120")
public class Servlet1070120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECTMX = "CMD_SELECTMX";
	/* 本Servlet对应的Service */
	private Service1070120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1070120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1070120();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getIntegralList(inputdata);
		}else if (CMD_SELECTMX.equals(CMD)) {
			getIntegralmxList(inputdata);
		}
	}
	/**
	 * @FunctionName: getIntegralList
	 * @Description: 查询积分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-15
	 */
	private void getIntegralList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1070120 beanIn = (Pojo1070120) this.getObject(inputdata, "beanLoad",
				Pojo1070120.class);

		int TotalCount = 0;
		List<Pojo1070120> PageData = new ArrayList<Pojo1070120>();
		try {
			TotalCount = service.getIntegralList_TotalCount(beanIn);
			PageData = service.getIntegralList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: getIntegralmxList
	 * @Description: 查询积分明细
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-15
	 */
	private void getIntegralmxList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1070121 beanIn = (Pojo1070121) this.getObject(inputdata, "beanLoad",
				Pojo1070121.class);

		int TotalCount = 0;
		List<Pojo1070121> PageData = new ArrayList<Pojo1070121>();
		try {
			TotalCount = service.getIntegralmxList_TotalCount(beanIn);
			PageData = service.getIntegralmxList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
}
