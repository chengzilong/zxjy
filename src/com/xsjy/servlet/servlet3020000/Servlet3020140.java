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
import com.xsjy.pojo.BaseTable.Pojo_XXXX;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020140;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020141;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020142;
import com.xsjy.service.service3020000.Service3020140;

/**
 * 
 * @ClassName: Servlet3020140
 * @Package:com.xsjy.servlet.servlet3020000
 * @Description: 发送消息控制类
 * @author ztz
 * @date 2015年1月13日 上午11:14:15
 * @update ztz at 2015年1月13日 上午11:14:15
 */
/**
 * Servlet implementation class Servlet3020140
 */
@WebServlet("/Servlet3020140")
public class Servlet3020140 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_INDEX_SELECT = "CMD_INDEX_SELECT";
	public static final String CMD_INDEX_DETAIL = "CMD_INDEX_DETAIL";
	public static final String CMD_STU_SELECT = "CMD_STU_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service3020140 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet3020140() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3020140();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_INDEX_SELECT.equals(CMD)) {
			getIndexDataList(inputdata);
		} else if (CMD_INDEX_DETAIL.equals(CMD)) {
			getIndexDetailDataList(inputdata);
		} else if (CMD_STU_SELECT.equals(CMD)) {
			getStuDataList(inputdata);
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
	 * @FunctionName: getIndexDataList
	 * @Description: 获取数据个数和列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月13日 上午11:15:45
	 */
	private void getIndexDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo_XXXX beanIn = (Pojo_XXXX) this.getObject(inputdata, "beanLoad",
				Pojo_XXXX.class);
		int dataCount = 0;
		List<Pojo_XXXX> dataList = new ArrayList<Pojo_XXXX>();
		
		try {
			beanIn.setXXXX_FBR(beanUser.getYHXX_UUID());
			dataCount = service.getIndexDataCount(beanIn);
			dataList = service.getIndexDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(dataCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: getIndexDetailDataList
	 * @Description: 获取数据详情列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月13日 上午11:38:27
	 */
	private void getIndexDetailDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		String xxid = this.getString(inputdata, "XXID");
		int dataCount = 0;
		List<Pojo3020141> dataList = new ArrayList<Pojo3020141>();
		
		try {
			dataCount = service.getIndexDetailDataCount(xxid, "SELECT");
			dataList = service.getIndexDetailDataList(xxid, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(dataCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: getStuDataList
	 * @Description: 获取学生列表数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月13日 下午3:58:58
	 */
	private void getStuDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020142 beanIn = (Pojo3020142) this.getObject(inputdata, "beanLoad",
				Pojo3020142.class);
		int dataCount = 0;
		List<Pojo3020142> dataList = new ArrayList<Pojo3020142>();
		
		try {
			beanIn.setJSBM(beanUser.getYHXX_UUID());
			dataCount = service.getStuDataCount(beanIn);
			dataList = service.getStuDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(dataCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增发布消息、消息明细数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月14日 下午12:36:08
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020140 beanIn = (Pojo3020140) this.getObject(inputdata, "BeanIn", Pojo3020140.class);
		boolean result = false;
		
		try {
			beanIn.setXXXX_CJR(beanUser.getYHXX_YHID());
			result = service.insertData(beanIn,beanUser.getYHXX_UUID());
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
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 更新发布消息、消息明细数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月14日 下午1:28:33
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020140 beanIn = (Pojo3020140) this.getObject(inputdata, "BeanIn", Pojo3020140.class);
		boolean result = false;
		
		try {
			beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
			result = service.updateData(beanIn);
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
	/**
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除发布消息、消息明细数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月13日 下午3:23:01
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo_XXXX beanIn = (Pojo_XXXX) this.getObject(inputdata, "BeanIn",Pojo_XXXX.class);
		boolean result = false;
		
		try {
			beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
			result = service.deleteData(beanIn);
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