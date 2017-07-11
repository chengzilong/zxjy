package com.xsjy.servlet.servlet2020000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;

import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020110;
import com.xsjy.service.service2020000.Service2020110;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;

/**
 * Servlet implementation class Servlet2020110
 */
@WebServlet("/Servlet2020110")
public class Servlet2020110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	
	/* 本Servlet对应的Service */
	private Service2020110 service;
	
	
    public Servlet2020110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2020110();
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getMycourseList(inputdata);
		} 
	}
	/**
	 * @FunctionName: getMycourseList
	 * @Description: 查询课程
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-13
	 */
	private void getMycourseList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2020110 beanIn = (Pojo2020110) this.getObject(inputdata, "beanLoad",
				Pojo2020110.class);

		int TotalCount = 0;
		List<Pojo2020110> PageData = new ArrayList<Pojo2020110>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setBCXX_CJR(beanUser.getYHXX_YHID());
		try {
			TotalCount = service.getMycourseList_TotalCount(beanIn);
			PageData = service.getMycourseList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
}
