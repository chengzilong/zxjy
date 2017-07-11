package com.xsjy.servlet.servlet2010000;

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
import com.xsjy.common.SessionValue;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010130;
import com.xsjy.service.service2010000.Service2010130;

/**
 * 
 * @ClassName: Servlet2010130
 * @Package:com.xsjy.servlet.Servlet2010130
 * @Description: 报名查询控制类
 * @author czl
 * @date 2015-01-12
 * @update
 */
/**
 * Servlet implementation class Servlet2010130
 */
@WebServlet("/Servlet2010130")
public class Servlet2010130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	
	/* 本Servlet对应的Service */
	private Service2010130 service;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet2010130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2010130();
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
	 * @author czl
	 * @date 2015-01-12
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2010130 beanIn = (Pojo2010130) this.getObject(inputdata, "beanLoad",
				Pojo2010130.class);

		int TotalCount = 0;
		List<Pojo2010130> dataList = new ArrayList<Pojo2010130>();
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionValue.LOGIN_USER);
		try {
			beanIn.setBMXX_CJR(beanUser.getYHXX_YHID());
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