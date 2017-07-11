package com.xsjy.servlet.servlet3010000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;

import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3010000.Pojo3010130;
import com.xsjy.service.service3010000.Service3010130;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;

/**
 * Servlet implementation class Servlet3010130
 */
@WebServlet("/Servlet3010130")
public class Servlet3010130 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	/* 本Servlet对应的Service */
	private Service3010130 service;

    public Servlet3010130() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3010130();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getTeacherList(inputdata);
		}
	}
	/**
	 * @FunctionName: getTeacherList
	 * @Description: 查询教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-05
	 */
	private void getTeacherList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3010130 beanIn = (Pojo3010130) this.getObject(inputdata, "beanLoad",
				Pojo3010130.class);

		int TotalCount = 0;
		List<Pojo3010130> PageData = new ArrayList<Pojo3010130>();
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
