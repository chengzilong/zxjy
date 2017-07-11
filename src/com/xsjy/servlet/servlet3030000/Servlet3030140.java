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
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030140;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030141;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030142;
import com.xsjy.service.service3030000.Service3030140;

/**
 * 
 * @ClassName: Servlet3030140
 * @Package:com.xsjy.servlet.servlet3030000
 * @Description: 学生分配控制类
 * @author ztz
 * @date 2015年1月14日 下午3:52:24
 */
/**
 * Servlet implementation class Servlet3030140
 */
@WebServlet("/Servlet3030140")
public class Servlet3030140 extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/* 命令定义部分 */
	public static final String CMD_SELECT_CLASS = "CMD_SELECT_CLASS";
	public static final String CMD_SELECT_STUDENT = "CMD_SELECT_STUDENT";
	public static final String CMD_SELECT_ENROLL = "CMD_SELECT_ENROLL";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_RELATION = "CMD_RELATION";
	public static final String CMD_CANCEL_RELATION = "CMD_CANCEL_RELATION";
	
	/* 本Servlet对应的Service */
	private Service3030140 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
	public Servlet3030140() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3030140();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT_CLASS.equals(CMD)) {
			getClassList(inputdata);
		} else if (CMD_SELECT_STUDENT.equals(CMD)) {
			getStudentList(inputdata);
		} else if (CMD_SELECT_ENROLL.equals(CMD)) {
			getEnrollList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			checkBcxsexist(inputdata);
		} else if (CMD_RELATION.equals(CMD)) {
			createRelationData(inputdata);
		} else if (CMD_CANCEL_RELATION.equals(CMD)) {
			cancelRelationData(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getClassList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月18日 上午11:12:29
	 */
	private void getClassList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030140 beanIn = (Pojo3030140) this.getObject(inputdata, "beanLoad",
				Pojo3030140.class);

		int TotalCount = 0;
		List<Pojo3030140> dataList = new ArrayList<Pojo3030140>();
		
		try {
			beanIn.setJSBM(beanUser.getYHXX_YHID());
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
	 * @FunctionName: getStudentList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月18日 上午11:12:41
	 */
	private void getStudentList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030141 beanIn = (Pojo3030141) this.getObject(inputdata, "beanLoad",
				Pojo3030141.class);

		int TotalCount = 0;
		List<Pojo3030141> dataList = new ArrayList<Pojo3030141>();
		
		try {
			TotalCount = service.getStudentCount(beanIn);
			dataList = service.getStudentList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: getEnrollList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月18日 上午11:12:54
	 */
	private void getEnrollList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030142 beanIn = (Pojo3030142) this.getObject(inputdata, "beanLoad",
				Pojo3030142.class);

		int TotalCount = 0;
		List<Pojo3030142> dataList = new ArrayList<Pojo3030142>();
		
		try {
			beanIn.setJSBM(beanUser.getYHXX_YHID());
			TotalCount = service.getEnrollCount(beanIn);
			dataList = service.getEnrollList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * @FunctionName: checkBcxsexist
	 * @Description: 验证班次学生是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-29
	 */
	private void checkBcxsexist(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String stuIds = this.getString(inputdata, "STUIDS");
		stuIds = stuIds.substring(0, stuIds.length() - 1);
		String stuMcs = this.getString(inputdata, "STUMCS");
		stuMcs = stuMcs.substring(0, stuMcs.length() - 1);
		
		try {
			String TotalCount = service.checkBcxsexist(stuIds,stuMcs,bcId);
			if(!TotalCount.equals("") ){
				arrResult.add("BCXS_EXIST");
				arrResult.add(TotalCount);
			}else{
				arrResult.add("BCXS_NOEXIST");
				arrResult.add("");
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
	 * @FunctionName: createRelationData
	 * @Description: 班次学生建立关联
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月18日 下午12:36:37
	 */
	private void createRelationData(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String dataIds = this.getString(inputdata, "DATAIDS");
		dataIds = dataIds.substring(0, dataIds.length() - 1);
		String stuIds = this.getString(inputdata, "STUIDS");
		stuIds = stuIds.substring(0, stuIds.length() - 1);
		
		try {
			boolean result = service.relationData(bcId, dataIds, stuIds, beanUser);
			if (result) {
				arrResult.add("CMD_OK");
			} else {
				arrResult.add("CMD_ERROR");
			}
		} catch (Exception e) {
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: cancelRelationData
	 * @Description: 班次学生取消关联
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月18日 下午12:36:46
	 */
	private void cancelRelationData(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String stuIds = this.getString(inputdata, "STUIDS");
		String bcxsids = this.getString(inputdata, "BCXSIDS");
		stuIds = stuIds.substring(0, stuIds.length() - 1);
		bcxsids = bcxsids.substring(0, bcxsids.length() - 1);
		
		try {
			boolean result = service.cancelRelationData(bcId, stuIds, bcxsids, beanUser);
			if (result) {
				arrResult.add("CMD_OK");
			} else {
				arrResult.add("CMD_ERROR");
			}
		} catch (Exception e) {
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}