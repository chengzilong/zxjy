package com.xsjy.servlet.school;

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
import com.xsjy.pojo.Custom.pojo_school.PojoSchool;
import com.xsjy.service.school.ServiceSchool;

/**
 * 
 * @ClassName: ServletSchool
 * @Package:com.xsjy.servlet.school
 * @Description: 网站学生查询教师学校
 * @author czl
 * @date 2014-12-30
 */
/**
 * Servlet implementation class ServletSchool
 */
@WebServlet("/ServletSchool")
public class ServletSchool extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CMD_SELECTJS = "CMD_SELECTJS";
	public static final String CMD_SELECTINFO = "CMD_SELECTINFO";
	public static final String CMD_SELECTXX = "CMD_SELECTXX";
	
	//本Servlet对应的Service
	private ServiceSchool service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	public ServletSchool() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ServiceSchool();
		arrResult = new ArrayList<Object>();
		
		if (CMD_SELECTJS.equals(CMD)) {
			getjsDataList(inputdata);
		} else if(CMD_SELECTXX.equals(CMD)){
			getxxDataList(inputdata);
		} else if(CMD_SELECTINFO.equals(CMD)){
			getjsDataInfo(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getjsDataList
	 * @Description: 获取页面数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-30
	 */
	private void getjsDataList(Map<String, String[]> inputdata) throws Exception {
		List<PojoSchool> dataBeanList = new ArrayList<PojoSchool>();
		
		try {
			dataBeanList = service.getDataBeanList();
			if (dataBeanList.size() > 0) {
				arrResult.add("SUCCESS");
				arrResult.add(dataBeanList);
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: getxxDataList
	 * @Description: 获取页面数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-30
	 */
	private void getxxDataList(Map<String, String[]> inputdata) throws Exception {
		List<PojoSchool> dataBeanList = new ArrayList<PojoSchool>();
		
		try {
			dataBeanList = service.getDataBeanList();
			if (dataBeanList.size() > 0) {
				arrResult.add("SUCCESS");
				arrResult.add(dataBeanList);
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: getjsDataInfo
	 * @Description: 获取页面数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-31
	 */
	private void getjsDataInfo(Map<String, String[]> inputdata) throws Exception {
		List<PojoSchool> dataBeanList = new ArrayList<PojoSchool>();
		
		try {
			dataBeanList = service.getDataBeanInfo();
			if (dataBeanList.size() > 0) {
				arrResult.add("SUCCESS");
				arrResult.add(dataBeanList);
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
}
