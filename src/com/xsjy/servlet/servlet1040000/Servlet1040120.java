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
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_BCXX;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040120;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040121;
import com.xsjy.service.service1040000.Service1040120;

/**
 *
 * @ClassName: Servlet1040120
 * @Package:com.xsjy.servlet.servlet1040000
 * @Description: 班次设定控制类
 * @author ztz
 * @date 2014年12月16日 下午4:01:58
 * @update
 */
/**
 * Servlet implementation class Servlet1040120
 */
@WebServlet("/Servlet1040120")
public class Servlet1040120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECT_COURSE = "CMD_SELECT_COURSE";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_CHECK = "CMD_CHECK";
	public static final String CMD_CANCEL_CHECK = "CMD_CANCEL_CHECK";

	/* 本Servlet对应的Service */
	private Service1040120 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet1040120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040120();
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
		} else if (CMD_CHECK.equals(CMD)) {
			checkClass(inputdata);
		} else if (CMD_CANCEL_CHECK.equals(CMD)) {
			cancelCheckClass(inputdata);
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
	 * @date 2017-08-04
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040120 beanIn = (Pojo1040120) this.getObject(inputdata, "beanLoad",
				Pojo1040120.class);

		int TotalCount = 0;
		List<Pojo1040120> dataList = new ArrayList<Pojo1040120>();

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
	 * @date 2017-08-04
	 */
	private void getCourseList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040121 beanIn = (Pojo1040121) this.getObject(inputdata, "beanLoad",
				Pojo1040121.class);

		int TotalCount = 0;
		List<Pojo1040121> dataList = new ArrayList<Pojo1040121>();

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
	 * @date 2017-08-04
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo1040120 beanIn = (Pojo1040120) this.getObject(inputdata, "BeanIn",Pojo1040120.class);
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
	 * @date 2017-08-04
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1040120 beanIn = (Pojo1040120) this.getObject(inputdata, "BeanIn",Pojo1040120.class);
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
	 * @date 2017-08-04
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
	/**
	 *
	 * @FunctionName: checkClass
	 * @Description: 验证班次
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-04
	 */
	private void checkClass(Map<String, String[]> inputdata) throws Exception {
		Pojo_BCXX beanIn = (Pojo_BCXX) this.getObject(inputdata, "BeanIn",Pojo_BCXX.class);
		beanIn.setBCXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.checkClass(beanIn);
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
	 * @FunctionName: cancelCheckClass
	 * @Description: 取消验证班次
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-04
	 */
	private void cancelCheckClass(Map<String, String[]> inputdata) throws Exception {
		Pojo_BCXX beanIn = (Pojo_BCXX) this.getObject(inputdata, "BeanIn",Pojo_BCXX.class);
		beanIn.setBCXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.cancelCheckClass(beanIn);
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