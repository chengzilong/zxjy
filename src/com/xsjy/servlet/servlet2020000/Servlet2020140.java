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
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020140;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020141;
import com.xsjy.service.service2020000.Service2020140;

/**
 * 
 * @ClassName: Servlet2020140
 * @Package:com.xsjy.servlet.servlet2020000
 * @Description: 我的学生控制类
 * @author czl
 * @date 2015-01-13
 */
/**
 * Servlet implementation class Servlet2020140
 */
@WebServlet("/Servlet2020140")
public class Servlet2020140 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_INDEX_SELECT = "CMD_INDEX_SELECT";
	public static final String CMD_TALK_SELECT = "CMD_TALK_SELECT";
	public static final String CMD_TALK_INSERT = "CMD_TALK_INSERT";
	public static final String CMD_TALK_UPDATE = "CMD_TALK_UPDATE";
	public static final String CMD_TALK_DELETE = "CMD_TALK_DELETE";
	
	/* 本Servlet对应的Service */
	private Service2020140 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet2020140() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2020140();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_INDEX_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_TALK_SELECT.equals(CMD)) {
			getTalkDataList(inputdata);
		} else if (CMD_TALK_INSERT.equals(CMD)) {
			insertTalkData(inputdata);
		} else if (CMD_TALK_UPDATE.equals(CMD)) {
			updateTalkData(inputdata);
		} else if (CMD_TALK_DELETE.equals(CMD)) {
			deleteTalkData(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-13
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2020140 beanIn = (Pojo2020140) this.getObject(inputdata, "beanLoad",
				Pojo2020140.class);

		int TotalCount = 0;
		List<Pojo2020140> dataList = new ArrayList<Pojo2020140>();
		
		try {
			beanIn.setJSXX_CJR(beanUser.getYHXX_YHID());//获取当前登陆学生ID
			TotalCount = service.getDataCount(beanIn);
			dataList = service.getDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: getTalkDataList
	 * @Description: 获取评价信息数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-13
	 */
	private void getTalkDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2020141 beanIn = (Pojo2020141) this.getObject(inputdata, "beanLoad",
				Pojo2020141.class);
		beanIn.setPJXX_PJR(beanUser.getYHXX_YHID());

		int TotalCount = 0;
		List<Pojo2020141> dataList = new ArrayList<Pojo2020141>();
		
		try {
			TotalCount = service.getTalkDataCount(beanIn);
			dataList = service.getTalkDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: insertTalkData
	 * @Description: 新增评价信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-13
	 */
	private void insertTalkData(Map<String, String[]> inputdata) throws Exception {
		Pojo2020141 beanIn = (Pojo2020141) this.getObject(inputdata, "BeanIn",Pojo2020141.class);
		beanIn.setPJXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertTalkData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: updateTalkData
	 * @Description: 更新评价信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-13
	 */
	private void updateTalkData(Map<String, String[]> inputdata) throws Exception {
		Pojo2020141 beanIn = (Pojo2020141) this.getObject(inputdata, "BeanIn",Pojo2020141.class);
		beanIn.setPJXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateTalkData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: deleteTalkData
	 * @Description: 删除评价信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-13
	 */
	private void deleteTalkData(Map<String, String[]> inputdata) throws Exception {
		Pojo2020141 beanIn = (Pojo2020141) this.getObject(inputdata, "BeanIn",Pojo2020141.class);
		beanIn.setPJXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.deleteTalkData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}