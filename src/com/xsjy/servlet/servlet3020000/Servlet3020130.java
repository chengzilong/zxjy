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
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020130;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020131;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020132;
import com.xsjy.service.service3020000.Service3020130;

/**
 * 
 * @ClassName: Servlet3020130
 * @Package:com.xsjy.servlet.servlet3020000
 * @Description: 我的学生控制类
 * @author ztz
 * @date 2015年1月9日 下午1:34:29
 */
/**
 * Servlet implementation class Servlet3020130
 */
@WebServlet("/Servlet3020130")
public class Servlet3020130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_INDEX_SELECT = "CMD_INDEX_SELECT";
	public static final String CMD_INDEX_DETAIL = "CMD_INDEX_DETAIL";
	public static final String CMD_SEND_SELECT = "CMD_SEND_SELECT";
	public static final String CMD_SEND_INSERT = "CMD_SEND_INSERT";
	public static final String CMD_SEND_UPDATE = "CMD_SEND_UPDATE";
	public static final String CMD_SEND_DELETE = "CMD_SEND_DELETE";
	public static final String CMD_TALK_SELECT = "CMD_TALK_SELECT";
	public static final String CMD_TALK_INSERT = "CMD_TALK_INSERT";
	public static final String CMD_TALK_UPDATE = "CMD_TALK_UPDATE";
	public static final String CMD_TALK_DELETE = "CMD_TALK_DELETE";
	
	/* 本Servlet对应的Service */
	private Service3020130 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet3020130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3020130();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_INDEX_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_INDEX_DETAIL.equals(CMD)) {
			getDetailDataList(inputdata);
		} else if (CMD_SEND_SELECT.equals(CMD)) {
			getSendDataList(inputdata);
		} else if (CMD_SEND_INSERT.equals(CMD)) {
			insertSendData(inputdata);
		} else if (CMD_SEND_UPDATE.equals(CMD)) {
			updateSendData(inputdata);
		} else if (CMD_SEND_DELETE.equals(CMD)) {
			deleteSendData(inputdata);
		} else if (CMD_TALK_SELECT.equals(CMD)) {
			getTalkDataList(inputdata);
		} else if (CMD_TALK_INSERT.equals(CMD)) {
			insertTalkData(inputdata);
		} else if (CMD_TALK_UPDATE.equals(CMD)) {
			updateTalkData(inputdata);
		} else if (CMD_TALK_DELETE.equals(CMD)) {
			deleteTalkData(inputdata);
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
	 * @date 2015年1月9日 下午1:35:39
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020130 beanIn = (Pojo3020130) this.getObject(inputdata, "beanLoad",
				Pojo3020130.class);

		int TotalCount = 0;
		List<Pojo3020130> dataList = new ArrayList<Pojo3020130>();
		
		try {
			beanIn.setJSBM(beanUser.getYHXX_YHID());//获取当前登陆教师ID
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
	 * @FunctionName: getDetailDataList
	 * @Description: 获取数据详情列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月9日 下午3:24:28
	 */
	private void getDetailDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020130 beanIn = (Pojo3020130) this.getObject(inputdata, "beanLoad",
				Pojo3020130.class);

		int TotalCount = 0;
		List<Pojo3020130> dataList = new ArrayList<Pojo3020130>();
		
		try {
			beanIn.setJSBM(beanUser.getYHXX_YHID());//获取当前登陆教师ID
			TotalCount = service.getDetailDataCount(beanIn);
			dataList = service.getDetailDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: getSendDataList
	 * @Description: 获取发布信息数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 上午10:42:14
	 */
	private void getSendDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020131 beanIn = (Pojo3020131) this.getObject(inputdata, "beanLoad",
				Pojo3020131.class);
		beanIn.setXXXX_FBR(beanUser.getYHXX_UUID());

		int TotalCount = 0;
		List<Pojo3020131> dataList = new ArrayList<Pojo3020131>();
		
		try {
			TotalCount = service.getSendDataCount(beanIn);
			dataList = service.getSendDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: insertSendData
	 * @Description: 新增发布信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 上午10:42:33
	 */
	private void insertSendData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020131 beanIn = (Pojo3020131) this.getObject(inputdata, "BeanIn",Pojo3020131.class);
		beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertSendData(beanIn,beanUser.getYHXX_UUID());
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
	 * @FunctionName: updateSendData
	 * @Description: 更新发布信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 上午10:42:47
	 */
	private void updateSendData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020131 beanIn = (Pojo3020131) this.getObject(inputdata, "BeanIn",Pojo3020131.class);
		beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateSendData(beanIn);
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
	 * @FunctionName: deleteSendData
	 * @Description: 删除发布信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 上午10:43:20
	 */
	private void deleteSendData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020131 beanIn = (Pojo3020131) this.getObject(inputdata, "BeanIn",Pojo3020131.class);
		beanIn.setXXXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.deleteSendData(beanIn);
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
	 * @FunctionName: getTalkDataList
	 * @Description: 获取评价信息数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 下午3:45:50
	 */
	private void getTalkDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3020132 beanIn = (Pojo3020132) this.getObject(inputdata, "beanLoad",
				Pojo3020132.class);
		beanIn.setPJXX_PJR(beanUser.getYHXX_UUID());

		int TotalCount = 0;
		List<Pojo3020132> dataList = new ArrayList<Pojo3020132>();
		
		try {
			TotalCount = service.getTalkDataCount(beanIn);
			dataList = service.getTalkDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: insertTalkData
	 * @Description: 新增评价信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 下午3:14:57
	 */
	private void insertTalkData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020132 beanIn = (Pojo3020132) this.getObject(inputdata, "BeanIn",Pojo3020132.class);
		beanIn.setPJXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertTalkData(beanIn,beanUser.getYHXX_UUID());
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
	 * @FunctionName: updateTalkData
	 * @Description: 更新评价信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 下午3:15:11
	 */
	private void updateTalkData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020132 beanIn = (Pojo3020132) this.getObject(inputdata, "BeanIn",Pojo3020132.class);
		beanIn.setPJXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateTalkData(beanIn);
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
	 * @FunctionName: deleteTalkData
	 * @Description: 删除评价信息数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月12日 下午3:15:37
	 */
	private void deleteTalkData(Map<String, String[]> inputdata) throws Exception {
		Pojo3020132 beanIn = (Pojo3020132) this.getObject(inputdata, "BeanIn",Pojo3020132.class);
		beanIn.setPJXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.deleteTalkData(beanIn);
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