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
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010110;
import com.xsjy.service.service2010000.Service2010110;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;

/**
 * Servlet implementation class Servlet2010110
 */
@WebServlet("/Servlet2010110")
public class Servlet2010110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	
	/* 本Servlet对应的Service */
	private Service2010110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet2010110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2010110();
		arrResult = new ArrayList<Object>();
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getNewsList(inputdata);
		} else if(CMD_UPDATE.equals(CMD)){
			updateCKZT(inputdata);
		}
	}
	/**
	 * @FunctionName: getNewsList
	 * @Description: 查询消息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-12
	 */
	private void getNewsList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2010110 beanIn = (Pojo2010110) this.getObject(inputdata, "beanLoad",
				Pojo2010110.class);

		int TotalCount = 0;
		List<Pojo2010110> PageData = new ArrayList<Pojo2010110>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXXXX_CJR(beanUser.getYHXX_UUID());
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
	 * @FunctionName: updateCKZT
	 * @Description: 更新消息查看状态
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-12
	 */
	private void updateCKZT(Map<String, String[]> inputdata) throws Exception {
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		String mxid = this.getString(inputdata, "MXID");
		try {
			ret = service.updateCKZT(beanUser.getYHXX_YHID(), mxid);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
				arrResult.add("ERROR");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}
