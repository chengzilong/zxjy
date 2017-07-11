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
import com.xsjy.pojo.Custom.pojo_school.PojoLession;
import com.xsjy.service.school.ServiceLession;

/**
 * Servlet implementation class ServletLession
 */
@WebServlet("/ServletLession")
public class ServletLession extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_DATA_COUNT = "CMD_DATA_COUNT";
	public static final String CMD_DATA_LIST = "CMD_DATA_LIST";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_JOIN = "CMD_JOIN";

	/* 本Servlet对应的Service */
	private ServiceLession service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

	public ServletLession() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ServiceLession();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_DATA_COUNT.equals(CMD)) {
			getDataCount(inputdata);
		}else if (CMD_DATA_LIST.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			checkRoleAndData(inputdata);
		} else if (CMD_JOIN.equals(CMD)) {
			insertData(inputdata);
		}
	}
	
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取列表数据个数
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月26日 上午10:44:08
	 */
	private void getDataCount(Map<String, String[]> inputdata) throws Exception {
		PojoLession dataBean = (PojoLession) this.getObject(inputdata, "BeanIn", PojoLession.class);
		int dataCount = 0;
		
		try {
			dataCount = service.getDataCount(dataBean);
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
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ljg
	 * @date 2014年12月17日 上午10:31:43
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "Sort");// 排序关键字
		int page = this.getInt(inputdata, "CurrPage");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		PojoLession beanIn = (PojoLession) this.getObject(inputdata,
				"BeanIn", PojoLession.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		List<PojoLession> dataList = new ArrayList<PojoLession>();

		try {
			if(beanUser==null){
				dataList = service.getDataList(beanIn, page, limit, sort,"");	
			}else{
				dataList = service.getDataList(beanIn, page, limit, sort,beanUser.getYHXX_YHID());
			}
			
			arrResult.add("SUCCESS");
			arrResult.add(dataList);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: checkRoleAndData
	 * @Description: 判断数据是否存在，角色是否正确
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月5日 下午3:50:40
	 */
	private void checkRoleAndData(Map<String, String[]> inputdata) throws Exception {
		String lessionID = this.getString(inputdata, "lessionID");//课程ID

		try {
			if (!"106".equals(beanUser.getYHXX_JSID())) {
				arrResult.add("ROLE_ERROR");
			} else {
				boolean isDataExist = service.isDataExist(lessionID, beanUser.getYHXX_YHID());
				if (isDataExist) {
					arrResult.add("DATA_EXIST");
				} else {
					arrResult.add("DATA_NOT_EXIST");
				}
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
	 * @FunctionName: insertData
	 * @Description: 新增数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月5日 下午3:50:50
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		String lessionID = this.getString(inputdata, "lessionID");//课程ID
		boolean result = false;
		
		try {
			result = service.insertData(lessionID, beanUser);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
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
