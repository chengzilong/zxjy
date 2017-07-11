package com.xsjy.servlet.servlet3010000;

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
import com.xsjy.pojo.Custom.pojo_3010000.Pojo3010110;
import com.xsjy.pojo.Custom.pojo_3010000.Pojo3010111;
import com.xsjy.service.service3010000.Service3010110;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet3010110
 */
@WebServlet("/Servlet3010110")
public class Servlet3010110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_SELECTHD = "CMD_SELECTHD";
	/* 本Servlet对应的Service */
	private Service3010110 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet3010110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3010110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getQuestionList(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertAnswer(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateAnswer(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteAnswer(inputdata);
		}else if (CMD_SELECTHD.equals(CMD)) {
			getAnswerList(inputdata);
		}
	}
	/**
	 * @FunctionName: getQuestionList
	 * @Description: 查询问题列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-09
	 */
	private void getQuestionList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3010110 beanIn = (Pojo3010110) this.getObject(inputdata, "beanLoad",
				Pojo3010110.class);

		int TotalCount = 0;
		List<Pojo3010110> PageData = new ArrayList<Pojo3010110>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setWTXX_CJR(beanUser.getYHXX_YHID());
			TotalCount = service.getQuestionList_TotalCount(beanIn);
			PageData = service.getQuestionList_PageData(beanIn, page, limit, sort,beanUser.getYHXX_UUID());
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	
	/**
	 * @FunctionName: insertAnswer
	 * @Description: 新增回答
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-09
	 */
	private void insertAnswer(Map<String, String[]> inputdata) throws Exception {
		Pojo3010111 beanIn = (Pojo3010111) this.getObject(inputdata, "BeanIn",Pojo3010111.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setHDXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setHDXX_GXR(beanUser.getYHXX_YHID());
			ret = service.insertAnswer(beanIn,beanUser.getYHXX_UUID());
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
	 * @FunctionName: updateAnswer
	 * @Description: 修改问题
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-09
	 */
	private void updateAnswer(Map<String, String[]> inputdata) throws Exception {
		Pojo3010111 beanIn = (Pojo3010111) this.getObject(inputdata, "BeanIn",Pojo3010111.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setHDXX_GXR(beanUser.getYHXX_YHID());
			ret = service.updateAnswer(beanIn);
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
	 * @FunctionName: deleteAnswer
	 * @Description: 删除答案
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-09
	 */
	private void deleteAnswer(Map<String, String[]> inputdata) throws Exception {
		Pojo3010111 beanIn = (Pojo3010111) this.getObject(inputdata, "BeanIn",Pojo3010111.class);
		int ret = 0;
		try {
			ret = service.deleteAnswer(beanIn);
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
	 * @FunctionName: getAnswerList
	 * @Description: 查询回答列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-09
	 */
	private void getAnswerList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		String wtid = this.getString(inputdata,"WTID");
		int TotalCount = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		List<Pojo3010111> PageData = new ArrayList<Pojo3010111>();
		try {
			
			TotalCount = service.getAnswerList_TotalCount(wtid,beanUser.getYHXX_UUID());
			PageData = service.getAnswerList_PageData(wtid,beanUser.getYHXX_UUID(), page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	
}
