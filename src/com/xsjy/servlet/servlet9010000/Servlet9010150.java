package com.xsjy.servlet.servlet9010000;

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
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010150;
import com.xsjy.service.service9010000.Service9010150;

/**
 * 
 * @ClassName: Servlet9010150
 * @Package:com.xsjy.servlet.servlet9010000
 * @Description: 站点管理控制类
 * @author ztz
 * @date 2014年10月30日 下午1:19:14
 * @update
 */
/**
 * Servlet implementation class Servlet9010150
 */
@WebServlet("/Servlet9010150")
public class Servlet9010150 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_ZHANDIAN_MD = "CMD_ZHANDIAN_MD";
	
	/* 本Servlet对应的Service */
	private Service9010150 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet9010150() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010150();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_SELECT.equals(CMD)) {
			getSiteList(inputdata);
		}else if (CMD_CHK_EXIST.equals(CMD)) {
			chkSiteExist(inputdata);
		}else if (CMD_INSERT.equals(CMD)) {
			insertSite(inputdata);
		}else if (CMD_UPDATE.equals(CMD)) {
			updateSite(inputdata);
		}else if (CMD_DELETE.equals(CMD)) {
			deleteSite(inputdata);
		}
	}
	/**
	 * @FunctionName: getSiteList
	 * @Description: 查询站点列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-10-27
	 */
	private void getSiteList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "beanLoad",
				Pojo9010150.class);

		int TotalCount = 0;
		List<Pojo9010150> PageData = new ArrayList<Pojo9010150>();
		
		try {
			TotalCount = service.getSiteList_TotalCount(beanIn);
			PageData = service.getSiteList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 * @FunctionName: chkSiteExist
	 * @Description: 判断站点信息是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-10-27
	 */
	private void chkSiteExist(Map<String, String[]> inputdata) throws Exception {
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "BeanIn",Pojo9010150.class);

		try {
			int TotalCount = service.getSiteCheck_TotalCount(beanIn);
			if(TotalCount > 0){
				arrResult.add("DATA_EXIST");
			}else{
				arrResult.add("DATA_NOEXIST");
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
	 * @FunctionName: insertSite
	 * @Description: 新增站点
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-10-27
	 */
	private void insertSite(Map<String, String[]> inputdata) throws Exception {
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "BeanIn",Pojo9010150.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setPXWD_CJR(beanUser.getYHXX_YHID());
		beanIn.setPXWD_GXR(beanUser.getYHXX_YHID());
		int ret = 0;
		
		try {
			ret = service.insertSite(beanIn);
			if(ret>0){
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
	 * @FunctionName: updateSite
	 * @Description: 修改站点
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-10-27
	 */
	private void updateSite(Map<String, String[]> inputdata) throws Exception {
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "BeanIn",Pojo9010150.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setPXWD_GXR(beanUser.getYHXX_YHID());
		int ret = 0;
		
		try {
			ret = service.updateSite(beanIn);
			if(ret>0){
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
	 * @FunctionName: deleteSite
	 * @Description: 删除站点
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-10-27
	 */
	private void deleteSite(Map<String, String[]> inputdata) throws Exception {
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "BeanIn",Pojo9010150.class);
		int ret = 0;
		
		try {
			ret = service.deleteSite(beanIn);
			if(ret>0){
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