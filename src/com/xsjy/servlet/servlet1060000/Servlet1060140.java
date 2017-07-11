package com.xsjy.servlet.servlet1060000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import com.xsjy.pojo.Custom.pojo_1060000.Pojo1060140;
import com.xsjy.service.service1060000.Service1060140;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1060140
 */
@WebServlet("/Servlet1060140")
public class Servlet1060140 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	/* 本Servlet对应的Service */
	private Service1060140 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1060140() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1060140();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getStudentList(inputdata);
		}
	}
	/**
	 * @FunctionName: getStudentList
	 * @Description: 查询学生信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-16
	 */
	private void getStudentList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1060140 beanIn = (Pojo1060140) this.getObject(inputdata, "beanLoad",
				Pojo1060140.class);

		int TotalCount = 0;
		List<Pojo1060140> PageData = new ArrayList<Pojo1060140>();
		try {
			TotalCount = service.getStudentEvaluateList_TotalCount(beanIn);
			PageData = service.getStudentEvaluateList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}	
}
