package com.xsjy.servlet.servlet1060000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import com.xsjy.pojo.Custom.pojo_1060000.Pojo1060120;
import com.xsjy.pojo.Custom.pojo_1060000.Pojo1060121;
import com.xsjy.service.service1060000.Service1060120;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1060120
 */
@WebServlet("/Servlet1060120")
public class Servlet1060120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECTMX = "CMD_SELECTMX";
	/* 本Servlet对应的Service */
	private Service1060120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1060120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1060120();
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
	 * @date 2014-12-16
	 */
	private void getIntegralList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1060120 beanIn = (Pojo1060120) this.getObject(inputdata, "beanLoad",
				Pojo1060120.class);

		int TotalCount = 0;
		List<Pojo1060120> PageData = new ArrayList<Pojo1060120>();
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
	 * @date 2014-12-16
	 */
	private void getIntegralmxList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1060121 beanIn = (Pojo1060121) this.getObject(inputdata, "beanLoad",
				Pojo1060121.class);

		int TotalCount = 0;
		List<Pojo1060121> PageData = new ArrayList<Pojo1060121>();
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
