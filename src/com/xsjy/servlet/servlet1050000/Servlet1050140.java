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
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1050000.Pojo1050140;
import com.xsjy.service.service1050000.Service1050140;

/**
 * 
 * @ClassName: Servlet1050140
 * @Package:com.xsjy.servlet.servlet1050000
 * @Description: 报名查询控制类
 * @author ztz
 * @date 2014年12月12日 下午2:11:34
 * @update
 */
/**
 * Servlet implementation class Servlet1050140
 */
@WebServlet("/Servlet1050140")
public class Servlet1050140 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	
	/* 本Servlet对应的Service */
	private Service1050140 service;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1050140() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1050140();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
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
	 * @date 2014年12月12日 下午2:15:59
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1050140 beanIn = (Pojo1050140) this.getObject(inputdata, "beanLoad",
				Pojo1050140.class);

		int TotalCount = 0;
		List<Pojo1050140> dataList = new ArrayList<Pojo1050140>();
		
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
}