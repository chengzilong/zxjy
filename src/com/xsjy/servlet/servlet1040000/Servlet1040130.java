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
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040130;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040131;
import com.xsjy.service.service1040000.Service1040130;

/**
 * 
 * @ClassName: Servlet1040130
 * @Package:com.xsjy.servlet.servlet1040000
 * @Description: 教师分配控制类
 * @author ztz
 * @date 2014年12月19日 上午9:57:56
 * @update
 */
/**
 * Servlet implementation class Servlet1040130
 */
@WebServlet("/Servlet1040130")
public class Servlet1040130 extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/* 命令定义部分 */
	public static final String CMD_SELECT_CLASS = "CMD_SELECT_CLASS";
	public static final String CMD_SELECT_TEACHER = "CMD_SELECT_TEACHER";
	public static final String CMD_SELECT_RELATION = "CMD_SELECT_RELATION";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_RELATION = "CMD_RELATION";
	public static final String CMD_CANCEL_RELATION = "CMD_CANCEL_RELATION";
	
	/* 本Servlet对应的Service */
	private Service1040130 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
	public Servlet1040130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040130();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT_CLASS.equals(CMD)) {
			getClassList(inputdata);
		} else if (CMD_SELECT_TEACHER.equals(CMD)) {
			getTeacherList(inputdata);
		} else if (CMD_SELECT_RELATION.equals(CMD)) {
			getRelationList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			checkBcjsexist(inputdata);
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
	 * @date 2014年12月19日 上午9:59:39
	 */
	private void getClassList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040130 beanIn = (Pojo1040130) this.getObject(inputdata, "beanLoad",
				Pojo1040130.class);

		int TotalCount = 0;
		List<Pojo1040130> dataList = new ArrayList<Pojo1040130>();
		
		try {
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
	 * @FunctionName: getTeacherList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月19日 上午9:59:27
	 */
	private void getTeacherList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040131 beanIn = (Pojo1040131) this.getObject(inputdata, "beanLoad",
				Pojo1040131.class);

		int TotalCount = 0;
		List<Pojo1040131> dataList = new ArrayList<Pojo1040131>();
		
		try {
			TotalCount = service.getTeacherCount(beanIn);
			dataList = service.getTeacherList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: getRelationList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月19日 上午10:07:14
	 */
	private void getRelationList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040131 beanIn = (Pojo1040131) this.getObject(inputdata, "beanLoad",
				Pojo1040131.class);

		int TotalCount = 0;
		List<Pojo1040131> dataList = new ArrayList<Pojo1040131>();
		
		try {
			TotalCount = service.getRelationCount(beanIn);
			dataList = service.getRelationList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * @FunctionName: checkBcjsexist
	 * @Description: 验证班次教师是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-02-02
	 */
	private void checkBcjsexist(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String teaIds = this.getString(inputdata, "TEAIDS");
		teaIds = teaIds.substring(0, teaIds.length() - 1);
		String teaMcs = this.getString(inputdata, "TEAMCS");
		teaMcs = teaMcs.substring(0, teaMcs.length() - 1);
		
		try {
			String TotalCount = service.checkBcjsexist(teaIds,teaMcs,bcId);
			if(!TotalCount.equals("") ){
				arrResult.add("BCJS_EXIST");
				arrResult.add(TotalCount);
			}else{
				arrResult.add("BCJS_NOEXIST");
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
	 * @Description: 班次教师建立关联
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月19日 上午9:59:59
	 */
	private void createRelationData(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String teaIds = this.getString(inputdata, "TEAIDS");
		teaIds = teaIds.substring(0, teaIds.length() - 1);
		
		try {
			boolean result = service.relationData(bcId, teaIds, beanUser);
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
	 * @Description: 班次教师取消关联
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年12月19日 上午10:01:53
	 */
	private void cancelRelationData(Map<String, String[]> inputdata) throws Exception {
		String bcId = this.getString(inputdata, "BCID");
		String teaIds = this.getString(inputdata, "TEAIDS");
		teaIds = teaIds.substring(0, teaIds.length() - 1);
		
		try {
			boolean result = service.cancelRelationData(bcId, teaIds, beanUser);
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