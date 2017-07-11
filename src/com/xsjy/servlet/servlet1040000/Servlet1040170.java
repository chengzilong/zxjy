package com.xsjy.servlet.servlet1040000;

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
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040170;
import com.xsjy.service.service1040000.Service1040170;

/**
 * 
 * @ClassName: Servlet1040170
 * @Package:com.xsjy.servlet.servlet1040000
 * @Description: 课程费用设定控制类
 * @author ztz
 * @date 2014年12月22日 下午3:52:39
 * @update
 */
/**
 * Servlet implementation class Servlet1040170
 */
@WebServlet("/Servlet1040170")
public class Servlet1040170 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	/* 本Servlet对应的Service */
	private Service1040170 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1040170() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040170();
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
	 * @date 2014年12月22日 下午3:53:01
	 */
	private void getCourseFeeList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040170 beanIn = (Pojo1040170) this.getObject(inputdata, "beanLoad",
				Pojo1040170.class);

		int TotalCount = 0;
		List<Pojo1040170> PageData = new ArrayList<Pojo1040170>();
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
	 * @date 2014年12月22日 下午3:53:09
	 */
	private void insertCourseFee(Map<String, String[]> inputdata) throws Exception {
		Pojo1040170 beanIn = (Pojo1040170) this.getObject(inputdata, "BeanIn",Pojo1040170.class);
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
	 * @date 2014年12月22日 下午3:53:18
	 */
	private void updateCourseFee(Map<String, String[]> inputdata) throws Exception {
		Pojo1040170 beanIn = (Pojo1040170) this.getObject(inputdata, "BeanIn",Pojo1040170.class);
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
	 * @date 2014年12月22日 下午3:53:26
	 */
	private void deleteCourseFee(Map<String, String[]> inputdata) throws Exception {
		Pojo1040170 beanIn = (Pojo1040170) this.getObject(inputdata, "BeanIn",Pojo1040170.class);
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
