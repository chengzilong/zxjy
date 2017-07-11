package com.xsjy.servlet.servlet1110000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import com.framework.session.SessionAttribute;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110110;
import com.xsjy.service.service1110000.Service1110110;
import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;

/**
 * Servlet implementation class Servlet1110110
 */
@WebServlet("/Servlet1110110")
public class Servlet1110110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DATA = "CMD_DATA";
	/* 本Servlet对应的Service */
	private Service1110110 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1110110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1110110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		if (CMD_UPDATE.equals(CMD)) {
			updateStaff(inputdata);
		} else if(CMD_DATA.equals(CMD)){
			getInfo();
		}
	}
	/**
	 * @FunctionName: getInfo
	 * @Description: 获取登录人信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-06
	 */
	private void getInfo() throws Exception {
		Pojo1110110 Data = null;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			Data = service.getInfo(beanUser.getYHXX_YHID());	
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
	 * @FunctionName: updateStaff
	 * @Description: 修改员工信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-01-06
	 */
	private void updateStaff(Map<String, String[]> inputdata) throws Exception {
		Pojo1110110 beanIn = (Pojo1110110) this.getObject(inputdata, "BeanIn",Pojo1110110.class);
		int ret = 0;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			beanIn.setYGXX_CJR(beanUser.getYHXX_YHID());
			beanIn.setYGXX_GXR(beanUser.getYHXX_YHID());
			
			//没有员工记录,则插入
			if(MyStringUtils.isBlank(beanIn.getYGXX_YGID())){
				 String strUUID = service.insertStaff(beanIn);
				 if(!"".equals(strUUID)){
					 beanIn.setYGXX_YGID(strUUID);
				 }else{
					 arrResult.add("FAILURE");
					 return;
				 }
			}	
			//已有员工记录,更新
			ret = service.updateStaff(beanIn);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}
