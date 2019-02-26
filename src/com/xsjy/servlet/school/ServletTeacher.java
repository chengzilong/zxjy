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
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030120;
import com.xsjy.pojo.Custom.pojo_school.PojoTeacher;
import com.xsjy.service.school.ServiceTeacher;

/**
 *
 * @ClassName: ServletTeacher
 * @Package:com.xsjy.servlet.school
 * @Description: 网站学生查询教师控制类
 * @author ztz
 * @date 2014年12月25日 上午10:46:20
 */
/**
 * Servlet implementation class ServletTeacher
 */
@WebServlet("/ServletTeacher")
public class ServletTeacher extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public static final String CMD_DATA_COUNT = "CMD_DATA_COUNT";
	public static final String CMD_DATA_LIST = "CMD_DATA_LIST";
	public static final String CMD_DATA_DETAIL = "CMD_DATA_DETAIL";
	public static final String CMD_PJXX_LIST = "CMD_PJXX_LIST";
	public static final String CMD_HDXX_LIST = "CMD_HDXX_LIST";
	public static final String CMD_ATTENTION = "CMD_ATTENTION";
	public static final String CMD_CANCEL_ATTENTION = "CMD_CANCEL_ATTENTION";

	//本Servlet对应的Service
	private ServiceTeacher service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

	public ServletTeacher() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ServiceTeacher();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		if (CMD_DATA_COUNT.equals(CMD)) {
			getDataCount(inputdata);
		} else if (CMD_DATA_LIST.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_DATA_DETAIL.equals(CMD)) {
			getDataDetail(inputdata);
		} else if (CMD_PJXX_LIST.equals(CMD)) {
			getPjxxList(inputdata);
		} else if (CMD_HDXX_LIST.equals(CMD)) {
			getHdxxList(inputdata);
		} else if (CMD_ATTENTION.equals(CMD)) {
			attention(inputdata);
		} else if (CMD_CANCEL_ATTENTION.equals(CMD)) {
			cancelAttention(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getDataCount
	 * @Description: 获取列表数据个数
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-7-14
	 */
	private void getDataCount(Map<String, String[]> inputdata) throws Exception {
		PojoTeacher dataBean = (PojoTeacher) this.getObject(inputdata, "BeanIn", PojoTeacher.class);
		int dataCount = 0;

		try {
			dataCount = service.getDataBeanCount(dataBean);
			arrResult.add("SUCCESS");
			arrResult.add(dataCount);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
	/**
	 *
	 * @FunctionName: getDataList
	 * @Description: 获取页面数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-07-18
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		PojoTeacher dataBean = (PojoTeacher) this.getObject(inputdata, "BeanIn", PojoTeacher.class);
		List<PojoTeacher> dataBeanList = new ArrayList<PojoTeacher>();

		try {
			dataBeanList = service.getDataBeanList(dataBean);
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
	 * @FunctionName: getDataDetail
	 * @Description: 获取页面数据详情（基本信息、课程排班、评价信息个数、回答信息个数、班次信息）
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月30日 下午2:44:22
	 * @update ztz at 2015年1月20日 下午2:51:50
	 */
	private void getDataDetail(Map<String, String[]> inputdata) throws Exception {
		String teaId = this.getString(inputdata, "TEAID");
		PojoTeacher dataBean = null;
		List<PojoTeacher> rotaDateList = new ArrayList<PojoTeacher>();
		List<PojoTeacher> rotaCourseList = new ArrayList<PojoTeacher>();
		int pjxxCount = 0;
		int hdxxCount = 0;
		List<Pojo3030120> classList = new ArrayList<Pojo3030120>();

		try {
			dataBean = service.getDataBean(teaId, beanUser == null?"":beanUser.getYHXX_YHID());//基本信息
			rotaDateList = service.getRotaDateList();//课程排班
			rotaCourseList = service.getRotaCourseList(teaId);//课程排班
			pjxxCount = service.getPjxxCount(teaId);//评价信息个数
			hdxxCount = service.getHdxxCount(teaId);//回答信息个数
			classList = service.getClassList(teaId);//班次信息

			if (dataBean != null) {
				arrResult.add("SUCCESS");
				arrResult.add(dataBean);
				arrResult.add(rotaDateList);
				arrResult.add(rotaCourseList);
				arrResult.add(pjxxCount);
				arrResult.add(hdxxCount);
				arrResult.add(classList);
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
	 * @FunctionName: getPjxxList
	 * @Description: 获取评价信息数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月19日 下午5:02:17
	 */
	private void getPjxxList(Map<String, String[]> inputdata) throws Exception {
		String page = this.getString(inputdata, "PAGE");
		String teaId = this.getString(inputdata, "TEAID");
		List<PojoTeacher> pjxxList = new ArrayList<PojoTeacher>();

		try {
			pjxxList = service.getPjxxList(page, teaId);
			if (pjxxList.size() > 0) {
				arrResult.add("SUCCESS");
				arrResult.add(pjxxList);
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
	 * @FunctionName: getHdxxList
	 * @Description: 获取回答信息数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月20日 上午10:39:17
	 */
	private void getHdxxList(Map<String, String[]> inputdata) throws Exception {
		String page = this.getString(inputdata, "PAGE");
		String teaId = this.getString(inputdata, "TEAID");
		List<PojoTeacher> hdxxList = new ArrayList<PojoTeacher>();

		try {
			hdxxList = service.getHdxxList(page, teaId);
			if (hdxxList.size() > 0) {
				arrResult.add("SUCCESS");
				arrResult.add(hdxxList);
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
	 * @FunctionName: attention
	 * @Description: 增加关注教师数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月20日 下午2:25:48
	 */
	private void attention(Map<String, String[]> inputdata) throws Exception {
		String teaId = this.getString(inputdata, "TEAID");
		boolean result = false;

		try {
			if (beanUser != null) {
				result = service.attention(beanUser, teaId);
				if (result) {
					arrResult.add("SUCCESS");
				} else {
					arrResult.add("FAILURE");
				}
			} else {
				arrResult.add("NOT_LOGIN");
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
	 * @FunctionName: cancelAttention
	 * @Description: 取消关注教师数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月20日 下午2:26:23
	 */
	private void cancelAttention(Map<String, String[]> inputdata) throws Exception {
		String teaId = this.getString(inputdata, "TEAID");
		boolean result = false;

		try {
			result = service.cancelAttention(beanUser, teaId);
			if (result) {
				arrResult.add("SUCCESS");
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