package com.xsjy.servlet.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.service.login.ServiceRegister;
import com.xsjy.servlet.common.SendMailThread;

/**
 * Servlet implementation class ServletRegister
 */
@WebServlet("/ServletRegister")
public class ServletRegister extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_REGIST = "CMD_REGIST";
	/* 本Servlet对应的Service */
	private ServiceRegister service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public ServletRegister() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ServiceRegister();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");
		if (CMD_CHK_EXIST.equals(CMD)) {
			checkUserexist(inputdata);
		}else if (CMD_REGIST.equals(CMD)) {
			registUser(inputdata);
		}
	}
	/**
	 * @FunctionName: checkUserexist
	 * @Description: 验证用户是否已经注册
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-7-11
	 */
	private void checkUserexist(Map<String, String[]> inputdata) throws Exception {
		String strSJHM = this.getString(inputdata, "SJHM");// 用户ID
		try {
			int TotalCount = service.checkUserexist(strSJHM);
			if(TotalCount > 0){
				arrResult.add("USER_EXIST");
			}else{
				arrResult.add("USER_NOEXIST");
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
	 * @FunctionName: registUser
	 * @Description: 用户注册
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-7-11
	 */
	private void registUser(Map<String, String[]> inputdata) throws Exception {
		String strSJHM = this.getString(inputdata, "SJHM");// 邮箱
		String strNC = this.getString(inputdata, "NC");// 昵称
		String strDLMM = this.getString(inputdata, "DLMM");// 登录密码
		String strYHQF = this.getString(inputdata, "YHQF");// 用户区分
		int ret = 0;

		try {
            String code = UUID.randomUUID().toString().replaceAll("-", "");
			ret = service.registUser(strSJHM,strNC,strDLMM,strYHQF,code);
			if(ret>0){
				arrResult.add("SUCCESS");

				Pojo_YHXX user = new Pojo_YHXX();
				user.setYHXX_CODE(code);
				user.setYHXX_YHID(strSJHM);
				user.setYHXX_YHMC(strNC);
				new SendMailThread(user).start();
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
