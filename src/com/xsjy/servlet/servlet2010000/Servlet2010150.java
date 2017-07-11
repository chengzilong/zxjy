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
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010150;
import com.xsjy.service.service2010000.Service2010150;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;

/**
 * Servlet implementation class Servlet2010150
 */
@WebServlet("/Servlet2010150")
public class Servlet2010150 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	/* 本Servlet对应的Service */
	private Service2010150 service;

    public Servlet2010150() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2010150();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getTeacherAttentionList(inputdata);
		}
	}
	/**
	 * @FunctionName: getTeacherAttentionList
	 * @Description: 查询关注信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-14
	 */
	private void getTeacherAttentionList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2010150 beanIn = (Pojo2010150) this.getObject(inputdata, "beanLoad",
				Pojo2010150.class);

		int TotalCount = 0;
		List<Pojo2010150> PageData = new ArrayList<Pojo2010150>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setGZXX_CJR(beanUser.getYHXX_YHID());
		try {
			TotalCount = service.getTeacherAttentionList_TotalCount(beanIn);
			PageData = service.getTeacherAttentionList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}	
}
