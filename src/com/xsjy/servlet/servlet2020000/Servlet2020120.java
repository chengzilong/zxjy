package com.xsjy.servlet.servlet2020000;

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
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020120;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020121;
import com.xsjy.service.service2020000.Service2020120;

@WebServlet("/Servlet2020120")
public class Servlet2020120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_MUTI_SELECT = "CMD_MUTI_SELECT";
	
	
	/* 本Servlet对应的Service */
	private Service2020120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

       
    public Servlet2020120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service2020120();
		arrResult = new ArrayList<Object>();
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getRCSDList(inputdata);
		} else if(CMD_MUTI_SELECT.equals(CMD)){
			getRCSDMutiList(inputdata);
		}
		
	}
	private void getRCSDList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo2020120 beanIn = (Pojo2020120) this.getObject(inputdata, "beanLoad",
				Pojo2020120.class);

		int TotalCount = 0;
		List<Pojo2020120> PageData = new ArrayList<Pojo2020120>();
		
		try {
			TotalCount = service.getRCSDList_TotalCount(beanIn);
			PageData = service.getRCSDList_PageData(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	
	private void getRCSDMutiList(Map<String, String[]> inputdata) throws Exception {
		
		Pojo2020121 beanIn = (Pojo2020121) this.getObject(inputdata, "beanLoad",
				Pojo2020121.class);
		Pojo2020121 resultList = null;
		try {
			resultList = service.getRCSDListByID(beanIn);
			arrResult.add("SUCCESS");
			arrResult.add(resultList);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}	
}
