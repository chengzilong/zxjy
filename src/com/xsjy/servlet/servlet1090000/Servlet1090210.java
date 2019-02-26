package com.xsjy.servlet.servlet1090000;

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
import com.xsjy.pojo.Custom.pojo_1090000.Pojo1090210;
import com.xsjy.service.service1090000.Service1090210;

/**
 * Servlet implementation class Servlet1090210
 */
@WebServlet("/Servlet1090210")
public class Servlet1090210 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	/* 本Servlet对应的Service */
	private Service1090210 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1090210() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1090210();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getAreaList(inputdata);
		}else if (CMD_CHK_EXIST.equals(CMD)) {
			checkAreaexist(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertArea(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateArea(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteArea(inputdata);
		}
	}
	/**
	 * @FunctionName: getAreaList
	 * @Description: 查询区域列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-27
	 */
	private void getAreaList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1090210 beanIn = (Pojo1090210) this.getObject(inputdata, "beanLoad",
				Pojo1090210.class);

		int TotalCount = 0;
		List<Pojo1090210> PageData = new ArrayList<Pojo1090210>();
		try {
			TotalCount = service.getAreaList_TotalCount(beanIn);
			PageData = service.getAreaList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: checkAreaexist
	 * @Description: 验证区域编码是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-27
	 */
	private void checkAreaexist(Map<String, String[]> inputdata) throws Exception {
		String strQYBM = this.getString(inputdata, "QYBM");// 区域编码
		try {
			int TotalCount = service.checkAreaexist(strQYBM);
			if(TotalCount > 0){
				arrResult.add("AREA_EXIST");
			}else{
				arrResult.add("AREA_NOEXIST");
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
	 * @FunctionName: insertArea
	 * @Description: 新增区域
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-26
	 */
	private void insertArea(Map<String, String[]> inputdata) throws Exception {
		Pojo1090210 beanIn = (Pojo1090210) this.getObject(inputdata, "BeanIn",Pojo1090210.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setXZQY_CJR(beanUser.getYHXX_YHID());
			beanIn.setXZQY_GXR(beanUser.getYHXX_YHID());
			ret = service.insertArea(beanIn);
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
	 * @FunctionName: updateArea
	 * @Description: 修改区域
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-27
	 */
	private void updateArea(Map<String, String[]> inputdata) throws Exception {
		Pojo1090210 beanIn = (Pojo1090210) this.getObject(inputdata, "BeanIn",Pojo1090210.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setXZQY_GXR(beanUser.getYHXX_YHID());
			ret = service.updateArea(beanIn);
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
	 * @FunctionName: deleteArea
	 * @Description: 删除区域
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-27
	 */
	private void deleteArea(Map<String, String[]> inputdata) throws Exception {
		Pojo1090210 beanIn = (Pojo1090210) this.getObject(inputdata, "BeanIn",Pojo1090210.class);
		int ret = 0;
		try {
			ret = service.deleteArea(beanIn);
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
