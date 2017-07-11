package com.xsjy.servlet.servlet1110000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110130;
import com.xsjy.service.service1110000.Service1110130;

/**
 * 
 * @ClassName: Servlet1110130
 * @Package:com.xsjy.servlet.servlet1110130
 * @Description: 学生基本信息控制类
 * @author czl
 * @date 2015-01-07
 * @update
 */
/**
 * Servlet implementation class Servlet1110130
 */
@WebServlet("/Servlet1110130")
public class Servlet1110130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_DATA = "CMD_DATA";
	public static final String CMD_UPDATE = "CMD_UPDATE";

	
	/* 本Servlet对应的Service */
	private Service1110130 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1110130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1110130();
		arrResult = new ArrayList<Object>();			
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_DATA.equals(CMD)) {
			getStudentInfo(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateData(inputdata);
		} 	
	}
	/**
	 * 
	 * @FunctionName: getStudentInfo
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-07
	 */
	private void getStudentInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1110130 Data = null;
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			Data = service.getStudentInfo(beanUser.getYHXX_YHID());
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
	 * 
	 * @FunctionName: updateData
	 * @Description: 更新数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-07
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1110130 beanIn = (Pojo1110130) this.getObject(inputdata, "BeanIn",Pojo1110130.class);
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXSXX_GXR(beanUser.getYHXX_YHID());
		int ret = 0;
		try {
			ret = service.updateStudent(beanIn);
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