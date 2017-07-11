package com.xsjy.servlet.servlet2020000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020130;
import com.xsjy.service.service2020000.Service2020130;

/**
 * 
 * @ClassName: Servlet2020130
 * @Package:com.xsjy.servlet.servlet2020000
 * @Description: 课表日程控制类
 * @author ztz
 * @date 2015年1月15日 下午2:13:54
 */
/**
 * Servlet implementation class Servlet2020130
 */
@WebServlet("/Servlet2020130")
public class Servlet2020130 extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CMD_GET_TITLE = "CMD_GET_TITLE";
	public static final String CMD_SELECT = "CMD_SELECT";
	
	//本Servlet对应的Service
	private Service2020130 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
	public Servlet2020130() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2020130();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		if (CMD_GET_TITLE.equals(CMD)) {
			getTitleList(inputdata);
		} else if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getTitleList
	 * @Description: 获取课表日程动态表头数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月15日 下午2:21:32
	 */
	private void getTitleList(Map<String, String[]> inputdata) throws Exception {
		List<Pojo2020130> titleList = new ArrayList<Pojo2020130>();
		
		try {
			titleList = service.getTitleList();
			arrResult.add("SUCCESS");
			arrResult.add(titleList);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取课表日程列表数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月15日 下午2:37:26
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2020130 beanIn = (Pojo2020130) this.getObject(inputdata, "beanLoad",
				Pojo2020130.class);
		int dataCount = 0;
		List<Pojo2020130> dataList = new ArrayList<Pojo2020130>();
		
		try {
			beanIn.setXSBM(beanUser.getYHXX_YHID());
			dataCount = service.getDataCount(beanIn);
			dataList = service.getDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			printGrid(dataCount, dataList);
		}
	}
}