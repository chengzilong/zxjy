package com.xsjy.servlet.servlet2010000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;

import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010140;
import com.xsjy.service.service2010000.Service2010140;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;

/**
 * Servlet implementation class Servlet2010140
 */
@WebServlet("/Servlet2010140")
public class Servlet2010140 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	/* 本Servlet对应的Service */
	private Service2010140 service;

    public Servlet2010140() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2010140();
		
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
	 * @date 2014-01-12
	 */
	private void getStudentList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2010140 beanIn = (Pojo2010140) this.getObject(inputdata, "beanLoad",
				Pojo2010140.class);

		int TotalCount = 0;
		List<Pojo2010140> PageData = new ArrayList<Pojo2010140>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setPJXX_CJR(beanUser.getYHXX_UUID());
		try {
			TotalCount = service.getTeacherEvaluateList_TotalCount(beanIn);
			PageData = service.getTeacherEvaluateList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}	
}
