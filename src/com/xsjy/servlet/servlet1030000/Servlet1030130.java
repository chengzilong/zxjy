package com.xsjy.servlet.servlet1030000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030130;
import com.xsjy.service.service1030000.Service1030130;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1030130
 */
@WebServlet("/Servlet1030130")
public class Servlet1030130 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECTRY = "CMD_SELECTRY";
	
	/* 本Servlet对应的Service */
	private Service1030130 service;

    public Servlet1030130() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1030130();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getNewsList(inputdata);
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
	 * @date 2014-12-18
	 */
	private void getNewsList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1030130 beanIn = (Pojo1030130) this.getObject(inputdata, "beanLoad",
				Pojo1030130.class);

		int TotalCount = 0;
		List<Pojo1030130> PageData = new ArrayList<Pojo1030130>();
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
	 * @FunctionName: getRenyuanList
	 * @Description: 查询人员
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-18
	 */
	private void getRenyuanList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1030130 beanIn = (Pojo1030130) this.getObject(inputdata, "beanLoad",
				Pojo1030130.class);
		int TotalCount = 0;
		List<Pojo1030130> PageData = new ArrayList<Pojo1030130>();
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
