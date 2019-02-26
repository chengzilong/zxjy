package com.xsjy.servlet.servlet3020000;

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
import com.xsjy.pojo.BaseTable.Pojo_BMXX;
import com.xsjy.pojo.BaseTable.Pojo_XSXX;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020120;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020121;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020122;
import com.xsjy.service.service3020000.Service3020120;

/**
 *
 * @ClassName: Servlet3020120
 * @Package:com.xsjy.servlet.servlet3020000
 * @Description: 学生报名控制类
 * @author ztz
 * @date 2015年1月7日 下午2:32:12
 * @update ztz at 2015年1月7日 下午2:32:12
 */
/**
 * Servlet implementation class Servlet3020120
 */
@WebServlet("/Servlet3020120")
public class Servlet3020120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_GET_XSXM = "CMD_GET_XSXM";
	public static final String CMD_SELECT_COURSE = "CMD_SELECT_COURSE";
	public static final String CMD_SELECT_CLASS = "CMD_SELECT_CLASS";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_INSERT_XK = "CMD_INSERT_XK";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT_XB = "CMD_INSERT_XB";
	public static final String CMD_UPDATE_TK = "CMD_UPDATE_TK";
	public static final String CMD_UPDATE_TB = "CMD_UPDATE_TB";

	/* 本Servlet对应的Service */
	private Service3020120 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet3020120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3020120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_GET_XSXM.equals(CMD)) {
			getXsxx(inputdata);
		} else if (CMD_SELECT_COURSE.equals(CMD)) {
			getCourseList(inputdata);
		} else if (CMD_SELECT_CLASS.equals(CMD)) {
			getClassList(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteData(inputdata);
		} else if (CMD_INSERT_XK.equals(CMD)) {
			insertData(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			checkBcxsexist(inputdata);
		} else if (CMD_INSERT_XB.equals(CMD)) {
			insertData(inputdata);
		} else if (CMD_UPDATE_TK.equals(CMD)) {
			updateData(inputdata);
		} else if (CMD_UPDATE_TB.equals(CMD)) {
			updateData(inputdata);
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
	 * @date 2015-08-03
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020120 beanIn = (Pojo3020120) this.getObject(inputdata, "beanLoad",
				Pojo3020120.class);

		int TotalCount = 0;
		List<Pojo3020120> dataList = new ArrayList<Pojo3020120>();

		try {
			beanIn.setJSBM(beanUser.getYHXX_YHID());
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
	 * @FunctionName: getXsxm
	 * @Description: 获取学生姓名
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void getXsxx(Map<String, String[]> inputdata) throws Exception {
		String xsbm = this.getString(inputdata, "XSDH");
		Pojo_XSXX dataBean = null;

		try {
			dataBean = service.getXsxx(xsbm);
			if (dataBean != null) {
				arrResult.add("SUCCESS");
				arrResult.add(dataBean);
				arrResult.add(beanUser.getYHXX_YHID());
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			//输出Ajax返回结果。
			print(arrResult);
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
		Pojo3020121 beanIn = (Pojo3020121) this.getObject(inputdata, "beanLoad",
				Pojo3020121.class);

		int TotalCount = 0;
		List<Pojo3020121> dataList = new ArrayList<Pojo3020121>();

		try {
			beanIn.setCURRENT_JSBM(beanUser.getYHXX_YHID());
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
	 * @FunctionName: getClassList
	 * @Description: 获取二级页面班次数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void getClassList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020122 beanIn = (Pojo3020122) this.getObject(inputdata, "beanLoad",
				Pojo3020122.class);

		int TotalCount = 0;
		List<Pojo3020122> dataList = new ArrayList<Pojo3020122>();

		try {
			beanIn.setCURRENT_JSBM(beanUser.getYHXX_YHID());
			TotalCount = service.getClassCount(beanIn);
			dataList = service.getClassList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
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
		Pojo_BMXX beanIn = (Pojo_BMXX) this.getObject(inputdata, "BeanIn",Pojo_BMXX.class);
		beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
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
	 * @FunctionName: checkBcxsexist
	 * @Description: 验证班次学生是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void checkBcxsexist(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String stuid = this.getString(inputdata, "STUID");

		try {
			int TotalCount = service.checkBcxsexist(stuid,bcId);
			if(TotalCount>0){
				arrResult.add("BCXS_EXIST");
			}else{
				arrResult.add("BCXS_NOEXIST");
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
	 * @FunctionName: insertData
	 * @Description: 新增数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-03
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020120 beanIn = (Pojo3020120) this.getObject(inputdata, "BeanIn",Pojo3020120.class);
		beanIn.setBMXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			beanIn.setJSBM(beanUser.getYHXX_YHID());
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
		Pojo3020120 beanIn = (Pojo3020120) this.getObject(inputdata, "BeanIn",Pojo3020120.class);
		beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
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
}