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
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110150;
import com.xsjy.service.service1110000.Service1110150;

/**
 * 
 * @ClassName: Servlet1110150
 * @Package:com.xsjy.servlet.servlet1110150
 * @Description: 个人信息
 * @author czl
 * @date 2014-11-19
 * @update
 */
/**
 * Servlet implementation class Servlet1110150
 */
@WebServlet("/Servlet1110150")
public class Servlet1110150 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_INFO = "CMD_INFO";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_VALIDATE = "CMD_VALIDATE";
	
	/* 本Servlet对应的Service */
	private Service1110150 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1110150() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1110150();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_INFO.equals(CMD)) {
			getInfo();
		}else if (CMD_UPDATE.equals(CMD)) {
			updateInfo(inputdata);
		}else if (CMD_VALIDATE.equals(CMD)) {
			validatePas(inputdata);
		}
	}
	/**
	 * @FunctionName: getInfo
	 * @Description: 获取个人信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-11-19
	 */
	private void getInfo() throws Exception {
		Pojo1110150 Data = null;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			Data = service.getInfo(beanUser.getYHXX_UUID());	
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
	 * @FunctionName: updateInfo
	 * @Description: 修改个人信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-11-19
	 */
	private void updateInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1110150 beanIn = (Pojo1110150) this.getObject(inputdata, "BeanIn",Pojo1110150.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		int ret = 0;
		
		try {
			ret = service.updateInfo(beanIn);
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
	 * @FunctionName: validatePas
	 * @Description: 验证原密码
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-05
	 */
	private void validatePas(Map<String, String[]> inputdata) throws Exception {
		Pojo1110150 Data = null;
		Pojo1110150 beanIn = (Pojo1110150) this.getObject(inputdata, "BeanIn",Pojo1110150.class);
		try {
			Data = service.validatePas(beanIn);	
			if(Data != null)
			{
				arrResult.add("SUCCESS");
			}else
			{
				arrResult.add("NOPASSWORD");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}