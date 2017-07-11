package com.xsjy.servlet.servlet2010000;

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
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010120;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010121;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010122;
import com.xsjy.service.service2010000.Service2010120;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;

/**
 * Servlet implementation class Servlet2010120
 */
@WebServlet("/Servlet2010120")
public class Servlet2010120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_GRJF = "CMD_GRJF";
	public static final String CMD_HDJF = "CMD_HDJF";
	public static final String CMD_SYJF = "CMD_SYJF";
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_WTXXEND = "CMD_WTXXEND";
	public static final String CMD_SELECTHD = "CMD_SELECTHD";
	public static final String CMD_DAFEN = "CMD_DAFEN";
	public static final String CMD_RETURNJF = "CMD_RETURNJF";
	public static final String CMD_SETHDJF = "CMD_SETHDJF";
	/* 本Servlet对应的Service */
	private Service2010120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet2010120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2010120();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_GRJF.equals(CMD)) {
			getGRJF();
		}else if (CMD_HDJF.equals(CMD)) {
			getHDJF(inputdata);
		}else if (CMD_SYJF.equals(CMD)) {
			getSYJF(inputdata);
		}else if (CMD_SELECT.equals(CMD)) {
			getQuestionList(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertQuestion(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateQuestion(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteQuestion(inputdata);
		}else if (CMD_WTXXEND.equals(CMD)) {
			setWTXXend(inputdata);
		}else if (CMD_SELECTHD.equals(CMD)) {
			getAnswerList(inputdata);
		}else if (CMD_DAFEN.equals(CMD)) {
			setHDXXdefen(inputdata);
		}else if (CMD_RETURNJF.equals(CMD)) {
			updateOwnjf(inputdata);
		}else if (CMD_SETHDJF.equals(CMD)) {
			setHdjf(inputdata);
		}
	}
	/**
	 * @FunctionName: getGRJF
	 * @Description: 获取个人积分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-15
	 */
	private void getGRJF() throws Exception {
		Pojo2010122 Data = null;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			Data = service.getGRJF(beanUser.getYHXX_UUID());	
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
	 * @FunctionName: getHDJF
	 * @Description: 获取回答积分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-15
	 */
	private void getHDJF(Map<String, String[]> inputdata) throws Exception {
		Pojo2010121 Data = null;
		String wtid = this.getString(inputdata, "WTID");
		try {
			Data = service.getHDJF(wtid);	
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
	 * @FunctionName: getSYJF
	 * @Description: 获取剩余积分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-16
	 */
	private void getSYJF(Map<String, String[]> inputdata) throws Exception {
		Pojo2010121 Data = null;
		String wtid = this.getString(inputdata, "WTID");
		String hdid = this.getString(inputdata, "HDID");
		try {
			Data = service.getSYJF(wtid,hdid);	
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
	 * @FunctionName: getQuestionList
	 * @Description: 查询问题列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-08
	 */
	private void getQuestionList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2010120 beanIn = (Pojo2010120) this.getObject(inputdata, "beanLoad",
				Pojo2010120.class);

		int TotalCount = 0;
		List<Pojo2010120> PageData = new ArrayList<Pojo2010120>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			
			TotalCount = service.getQuestionList_TotalCount(beanIn,beanUser.getYHXX_UUID());
			PageData = service.getQuestionList_PageData(beanIn, page, limit, sort,beanUser.getYHXX_UUID());
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	
	/**
	 * @FunctionName: insertQuestion
	 * @Description: 新增问题
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-08
	 */
	private void insertQuestion(Map<String, String[]> inputdata) throws Exception {
		Pojo2010120 beanIn = (Pojo2010120) this.getObject(inputdata, "BeanIn",Pojo2010120.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setWTXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setWTXX_GXR(beanUser.getYHXX_YHID());
			ret = service.insertQuestion(beanIn,beanUser.getYHXX_UUID());
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
	 * @FunctionName: updateQuestion
	 * @Description: 修改问题
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-08
	 */
	private void updateQuestion(Map<String, String[]> inputdata) throws Exception {
		Pojo2010120 beanIn = (Pojo2010120) this.getObject(inputdata, "BeanIn",Pojo2010120.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setWTXX_GXR(beanUser.getYHXX_YHID());
			ret = service.updateQuestion(beanIn,beanUser.getYHXX_UUID());
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
	 * @FunctionName: deleteQuestion
	 * @Description: 删除问题
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-01-08
	 */
	private void deleteQuestion(Map<String, String[]> inputdata) throws Exception {
		Pojo2010120 beanIn = (Pojo2010120) this.getObject(inputdata, "BeanIn",Pojo2010120.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setWTXX_GXR(beanUser.getYHXX_YHID());
			ret = service.deleteQuestion(beanIn,beanUser.getYHXX_UUID());
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
	 * @FunctionName: setWTXXend
	 * @Description: 结束问题
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-15
	 */
	private void setWTXXend(Map<String, String[]> inputdata) throws Exception {
		Pojo2010120 beanIn = (Pojo2010120) this.getObject(inputdata, "BeanIn",Pojo2010120.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setWTXX_GXR(beanUser.getYHXX_YHID());
			ret = service.setWTXXend(beanIn);
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
		List<Pojo2010121> PageData = new ArrayList<Pojo2010121>();
		try {
			
			TotalCount = service.getAnswerList_TotalCount(wtid);
			PageData = service.getAnswerList_PageData(wtid, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: setHDXXdefen
	 * @Description: 打分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-15
	 */
	private void setHDXXdefen(Map<String, String[]> inputdata) throws Exception {
		Pojo2010121 beanIn = (Pojo2010121) this.getObject(inputdata, "BeanIn",Pojo2010121.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setHDXX_GXR(beanUser.getYHXX_YHID());
			ret = service.setHDXXdefen(beanIn);
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
	 * @FunctionName: updateOwnjf
	 * @Description: 修改个人积分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-16
	 */
	private void updateOwnjf(Map<String, String[]> inputdata) throws Exception {	
		String grjf = this.getString(inputdata,"GRJF");
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			ret = service.updateOwnjf(grjf,beanUser.getYHXX_YHID(),beanUser.getYHXX_UUID());
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
	 * @FunctionName: setHdjf
	 * @Description: 设置回答积分
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-16
	 */
	private void setHdjf(Map<String, String[]> inputdata) throws Exception {
		String hdrs = this.getString(inputdata, "HDRS");//回答人
		String hdjfs = this.getString(inputdata, "HDJFS");  //回答积分
		hdrs = hdrs.substring(0, hdrs.length() - 1);
		hdjfs = hdjfs.substring(0, hdjfs.length() - 1);
		String[] hdr = hdrs.split(",");
		String[] hdjf = hdjfs.split(",");
		Pojo2010122 Data = null;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			for(int i = 0; i < hdr.length; i++) {
				Data = service.getGRJF(hdr[i]);	
				if(Data != null){
					service.updateHdjf(hdr[i],hdjf[i],Data.getJJXX_JJID(),beanUser.getYHXX_YHID());
				}else{
					service.insertHdjf(hdr[i],hdjf[i],beanUser.getYHXX_YHID());
				}
			}	
			arrResult.add("CMD_OK");
		
		} catch (Exception e) {
			// TODO: handle exception
			arrResult.add("CMD_EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
}
