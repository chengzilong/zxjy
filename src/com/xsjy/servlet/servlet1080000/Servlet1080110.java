package com.xsjy.servlet.servlet1080000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import com.xsjy.pojo.Custom.pojo_1080000.Pojo1080110;
import com.xsjy.service.service1080000.Service1080110;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1080110
 */
@WebServlet("/Servlet1080110")
public class Servlet1080110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	/* 本Servlet对应的Service */
	private Service1080110 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1080110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1080110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getOnlinePayList(inputdata);
		}
	}
	/**
	 * @FunctionName: getOnlinePayList
	 * @Description: 查询网上交费列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-13
	 */
	private void getOnlinePayList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1080110 beanIn = (Pojo1080110) this.getObject(inputdata, "beanLoad",
				Pojo1080110.class);

		int TotalCount = 0;
		List<Pojo1080110> PageData = new ArrayList<Pojo1080110>();
		try {
			TotalCount = service.getOnlinePayList_TotalCount(beanIn);
			PageData = service.getOnlinePayList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
}
