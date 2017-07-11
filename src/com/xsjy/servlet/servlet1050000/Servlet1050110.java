package com.xsjy.servlet.servlet1050000;

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
import com.xsjy.pojo.BaseTable.Pojo_LSBM;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1050000.Pojo1050110;
import com.xsjy.service.service1050000.Service1050110;

/**
 * 
 * @ClassName: Servlet1050110
 * @Package:com.xsjy.servlet.servlet1050000
 * @Description: 临时报名控制类
 * @author ztz
 * @date 2014年12月11日 上午10:51:22
 * @update
 */
/**
 * Servlet implementation class Servlet1050110
 */
@WebServlet("/Servlet1050110")
public class Servlet1050110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service1050110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1050110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1050110();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			checkDataExist(inputdata);
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
	 * @author ztz
	 * @date 2014年12月12日 上午9:26:32
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "beanLoad",
				Pojo1050110.class);

		int TotalCount = 0;
		List<Pojo1050110> dataList = new ArrayList<Pojo1050110>();
		
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
	 * @FunctionName: checkDataExist
	 * @Description: 判断数据是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月11日 上午11:32:18
	 */
	private void checkDataExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "BeanIn",Pojo1050110.class);

		try {
			int TotalCount = service.getDataCount(beanIn);
			if(TotalCount > 0){
				arrResult.add("DATA_EXIST");
			}else{
				arrResult.add("DATA_NOT_EXIST");
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
	 * @date 2014年12月12日 上午10:18:25
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo_LSBM beanIn = (Pojo_LSBM) this.getObject(inputdata, "BeanIn",Pojo_LSBM.class);
		beanIn.setLSBM_CJR(beanUser.getYHXX_YHID());
		beanIn.setLSBM_GXR(beanUser.getYHXX_YHID());
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
	 * @author ztz
	 * @date 2014年12月12日 上午10:18:33
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo_LSBM beanIn = (Pojo_LSBM) this.getObject(inputdata, "BeanIn",Pojo_LSBM.class);
		beanIn.setLSBM_GXR(beanUser.getYHXX_YHID());
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
	 * @author ztz
	 * @date 2014年12月12日 上午10:18:42
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo_LSBM beanIn = (Pojo_LSBM) this.getObject(inputdata, "BeanIn",Pojo_LSBM.class);
		beanIn.setLSBM_GXR(beanUser.getYHXX_YHID());
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