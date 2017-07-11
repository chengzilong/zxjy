package com.xsjy.servlet.servlet3030000;

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
import com.xsjy.common.SessionValue;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030110;
import com.xsjy.service.service3030000.Service3030110;

/**
 * 
 * @ClassName: Servlet3030110
 * @Package:com.xsjy.servlet.servlet3030000
 * @Description: 我的课程控制类
 * @author ztz
 * @date 2015年1月5日 下午4:51:10
 */
/**
 * Servlet implementation class Servlet3030110
 */
@WebServlet("/Servlet3030110")
public class Servlet3030110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service3030110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet3030110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3030110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getCourseFeeList(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertCourseFee(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateCourseFee(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteCourseFee(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getCourseFeeList
	 * @Description: 查询课程费用列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月5日 上午11:01:59
	 */
	private void getCourseFeeList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030110 beanIn = (Pojo3030110) this.getObject(inputdata, "beanLoad",
				Pojo3030110.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		beanIn.setJSBM(beanUser.getYHXX_YHID());

		int TotalCount = 0;
		List<Pojo3030110> PageData = new ArrayList<Pojo3030110>();
		try {
			TotalCount = service.getCourseFeeList_TotalCount(beanIn);
			PageData = service.getCourseFeeList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * 
	 * @FunctionName: insertCourseFee
	 * @Description: 新增课程费用
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月5日 上午11:02:12
	 */
	private void insertCourseFee(Map<String, String[]> inputdata) throws Exception {
		Pojo3030110 beanIn = (Pojo3030110) this.getObject(inputdata, "BeanIn",Pojo3030110.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setKCFY_CJR(beanUser.getYHXX_YHID());
			beanIn.setKCFY_GXR(beanUser.getYHXX_YHID());
			ret = service.insertCourseFee(beanIn);
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
	/**
	 * 
	 * @FunctionName: updateCourseFee
	 * @Description: 修改课程费用
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月5日 上午11:02:24
	 */
	private void updateCourseFee(Map<String, String[]> inputdata) throws Exception {
		Pojo3030110 beanIn = (Pojo3030110) this.getObject(inputdata, "BeanIn",Pojo3030110.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setKCFY_GXR(beanUser.getYHXX_YHID());
			ret = service.updateCourseFee(beanIn);
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
	/**
	 * 
	 * @FunctionName: deleteCourseFee
	 * @Description: 删除课程费用
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月5日 上午11:03:00
	 */
	private void deleteCourseFee(Map<String, String[]> inputdata) throws Exception {
		Pojo3030110 beanIn = (Pojo3030110) this.getObject(inputdata, "BeanIn",Pojo3030110.class);
		int ret = 0;
		try {
			ret = service.deleteCourseFee(beanIn);
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