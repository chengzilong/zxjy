package com.xsjy.servlet.servlet1080000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import com.xsjy.common.SessionValue;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1080000.Pojo1080120;
import com.xsjy.pojo.Custom.pojo_1080000.Pojo1080121;
import com.xsjy.service.service1080000.Service1080120;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1080120
 */
@WebServlet("/Servlet1080120")
public class Servlet1080120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_VALUE = "CMD_VALUE";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_SELECTMX = "CMD_SELECTMX";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	/* 本Servlet对应的Service */
	private Service1080120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1080120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1080120();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getManagePayList(inputdata);
		}else if (CMD_VALUE.equals(CMD)) {
			getValue(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertManagePay(inputdata);
		}else if (CMD_SELECTMX.equals(CMD)) {
			getManagePaymxList(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateManagePay(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteManagePay(inputdata);
		}
	}
	/**
	 * @FunctionName: getManagePayList
	 * @Description: 查询报名交费列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-12
	 */
	private void getManagePayList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1080120 beanIn = (Pojo1080120) this.getObject(inputdata, "beanLoad",
				Pojo1080120.class);

		int TotalCount = 0;
		List<Pojo1080120> PageData = new ArrayList<Pojo1080120>();
		try {
			TotalCount = service.getManagePayList_TotalCount(beanIn);
			PageData = service.getManagePayList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: getValue
	 * @Description: 加载数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-15
	 */
	private void getValue(Map<String, String[]> inputdata) throws Exception {
		Pojo1080120 Data = null;
		String bmid = this.getString(inputdata, "BMID");
		try {
			Data = service.getValue(bmid);	
			if(Data != null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(Data);
			}else
			{
				arrResult.add("DATA_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: getManagePaymxList
	 * @Description: 查询交费明细列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-12
	 */
	private void getManagePaymxList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1080121 beanIn = (Pojo1080121) this.getObject(inputdata, "beanLoad",
				Pojo1080121.class);

		int TotalCount = 0;
		List<Pojo1080121> PageData = new ArrayList<Pojo1080121>();
		try {
			TotalCount = service.getManagePaymxList_TotalCount(beanIn);
			PageData = service.getManagePaymxList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: insertManagePay
	 * @Description: 新增交费
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-13
	 */
	private void insertManagePay(Map<String, String[]> inputdata) throws Exception {
		Pojo1080120 beanIn = (Pojo1080120) this.getObject(inputdata, "BeanIn",Pojo1080120.class);	
		Pojo1080121 beanInmx = (Pojo1080121) this.getObject(inputdata, "BeanInmx",Pojo1080121.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setBMXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
			ret = service.insertManagePay(beanIn,beanInmx);
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
	 * @FunctionName: updateManagePay
	 * @Description: 修改交费
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-12
	 */
	private void updateManagePay(Map<String, String[]> inputdata) throws Exception {
		Pojo1080120 beanIn = (Pojo1080120) this.getObject(inputdata, "BeanIn",Pojo1080120.class);	
		Pojo1080121 beanInmx = (Pojo1080121) this.getObject(inputdata, "BeanInmx",Pojo1080121.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setBMXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
			ret = service.updateManagePay(beanIn,beanInmx);
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
	 * @FunctionName: deleteManagePay
	 * @Description: 删除交费
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-12
	 */
	private void deleteManagePay(Map<String, String[]> inputdata) throws Exception {
		Pojo1080121 beanInmx = (Pojo1080121) this.getObject(inputdata, "BeanInmx",Pojo1080121.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanInmx.setJFMX_GXR(beanUser.getYHXX_YHID());
			ret = service.deleteManagePay(beanInmx);
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
