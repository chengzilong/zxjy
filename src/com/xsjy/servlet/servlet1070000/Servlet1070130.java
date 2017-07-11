package com.xsjy.servlet.servlet1070000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070130;
import com.xsjy.service.service1070000.Service1070130;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1070130
 */
@WebServlet("/Servlet1070130")
public class Servlet1070130 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_IDENTIFICATE = "CMD_IDENTIFICATE";
	/* 本Servlet对应的Service */
	private Service1070130 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1070130() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1070130();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getTeacherList(inputdata);
		}else if (CMD_IDENTIFICATE.equals(CMD)) {
			identificateTeacher(inputdata);
		}
	}
	/**
	 * @FunctionName: getTeacherList
	 * @Description: 查询教师信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-13
	 */
	private void getTeacherList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1070130 beanIn = (Pojo1070130) this.getObject(inputdata, "beanLoad",
				Pojo1070130.class);

		int TotalCount = 0;
		List<Pojo1070130> PageData = new ArrayList<Pojo1070130>();
		try {
			TotalCount = service.getTeacherList_TotalCount(beanIn);
			PageData = service.getTeacherList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: identificateTeacher
	 * @Description: 教师认证
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-13
	 */
	private void identificateTeacher(Map<String, String[]> inputdata) throws Exception {
		Pojo1070130 beanIn = (Pojo1070130) this.getObject(inputdata, "BeanIn",Pojo1070130.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setJSXX_GXR(beanUser.getYHXX_YHID());
			ret = service.identificateTeacher(beanIn,beanUser.getYHXX_UUID());
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
				arrResult.add("FAILURE");
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
