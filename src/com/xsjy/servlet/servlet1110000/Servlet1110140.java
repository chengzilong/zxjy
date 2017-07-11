package com.xsjy.servlet.servlet1110000;

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
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110140;
import com.xsjy.service.service1110000.Service1110140;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1110140
 */
@WebServlet("/Servlet1110140")
public class Servlet1110140 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	/* 本Servlet对应的Service */
	private Service1110140 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1110140() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1110140();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getAccountList(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertAccount(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateAccount(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteAccount(inputdata);
		}
	}
	/**
	 * @FunctionName: getAccountList
	 * @Description: 查询账户列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-05
	 */
	private void getAccountList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1110140 beanIn = (Pojo1110140) this.getObject(inputdata, "beanLoad",
				Pojo1110140.class);

		int TotalCount = 0;
		List<Pojo1110140> PageData = new ArrayList<Pojo1110140>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		beanIn.setGRZH_CJR(beanUser.getYHXX_UUID());
		try {
			
			TotalCount = service.getAccountList_TotalCount(beanIn);
			PageData = service.getAccountList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	
	/**
	 * @FunctionName: insertAccount
	 * @Description: 新增账户
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-05
	 */
	private void insertAccount(Map<String, String[]> inputdata) throws Exception {
		Pojo1110140 beanIn = (Pojo1110140) this.getObject(inputdata, "BeanIn",Pojo1110140.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setGRZH_CJR(beanUser.getYHXX_YHID());
			beanIn.setGRZH_GXR(beanUser.getYHXX_YHID());
			ret = service.insertAccount(beanIn,beanUser.getYHXX_JSID(),beanUser.getYHXX_UUID());
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
	 * @FunctionName: updateAccount
	 * @Description: 修改账户
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-05
	 */
	private void updateAccount(Map<String, String[]> inputdata) throws Exception {
		Pojo1110140 beanIn = (Pojo1110140) this.getObject(inputdata, "BeanIn",Pojo1110140.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setGRZH_GXR(beanUser.getYHXX_YHID());
			ret = service.updateAccount(beanIn);
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
	 * @FunctionName: deleteAccount
	 * @Description: 删除账户
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-05
	 */
	private void deleteAccount(Map<String, String[]> inputdata) throws Exception {
		Pojo1110140 beanIn = (Pojo1110140) this.getObject(inputdata, "BeanIn",Pojo1110140.class);
		int ret = 0;
		try {
			ret = service.deleteAccount(beanIn);
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
