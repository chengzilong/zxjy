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
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_BCXX;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030120;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030121;
import com.xsjy.service.service3030000.Service3030120;

/**
 *
 * @ClassName: Servlet3030120
 * @Package:com.xsjy.servlet.servlet3030000
 * @Description: 班次计划控制类
 * @author ztz
 * @date 2015年1月6日 上午9:52:50
 */
/**
 * Servlet implementation class Servlet3030120
 */
@WebServlet("/Servlet3030120")
public class Servlet3030120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECT_COURSE = "CMD_SELECT_COURSE";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";

	/* 本Servlet对应的Service */
	private Service3030120 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet3030120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3030120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_SELECT_COURSE.equals(CMD)) {
			getCourseList(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertData(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateData(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteData(inputdata);
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
	 * @date 2017-08-03
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030120 beanIn = (Pojo3030120) this.getObject(inputdata, "beanLoad",
				Pojo3030120.class);
		beanIn.setJSBM(beanUser.getYHXX_YHID());

		int TotalCount = 0;
		List<Pojo3030120> dataList = new ArrayList<Pojo3030120>();

		try {
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
	 * @FunctionName: getCourseList
	 * @Description: 获取二级页面课程费用数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void getCourseList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030121 beanIn = (Pojo3030121) this.getObject(inputdata, "beanLoad",
				Pojo3030121.class);
		beanIn.setJSBM(beanUser.getYHXX_YHID());

		int TotalCount = 0;
		List<Pojo3030121> dataList = new ArrayList<Pojo3030121>();

		try {
			TotalCount = service.getCourseCount(beanIn);
			dataList = service.getCourseList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 *
	 * @FunctionName: insertData
	 * @Description: 新增数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo3030120 beanIn = (Pojo3030120) this.getObject(inputdata, "BeanIn",Pojo3030120.class);
		beanIn.setBCXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setBCXX_GXR(beanUser.getYHXX_YHID());
		beanIn.setBCXX_LRR(beanUser.getYHXX_UUID());
		boolean result = false;

		try {
			result = service.insertData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			}else{
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
	 * @FunctionName: updateData
	 * @Description: 更新数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo3030120 beanIn = (Pojo3030120) this.getObject(inputdata, "BeanIn",Pojo3030120.class);
		beanIn.setBCXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.updateData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			}else{
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
	 * @FunctionName: deleteData
	 * @Description: 删除数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo_BCXX beanIn = (Pojo_BCXX) this.getObject(inputdata, "BeanIn",Pojo_BCXX.class);
		beanIn.setBCXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.deleteData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			}else{
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