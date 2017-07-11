package com.xsjy.servlet.login;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.framework.core.BaseServlet;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_MENU;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.common.Pojo_USER_INFO;
import com.xsjy.service.login.ServiceLogin;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_USER_CHECK = "CMD_USER_CHECK";

	// 本Servlet对应的Service
	private ServiceLogin service;
	// Ajax返回前台的结果集
	private ArrayList<Object> arrResult;

	private String projPath;

	public ServletLogin() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ServiceLogin();
		arrResult = new ArrayList<Object>();
		projPath = request.getSession().getServletContext().getRealPath("/bin/upload/pics/");

		if (CMD_USER_CHECK.equals(CMD)) {
			checkUser(inputdata);
		}

		// 输出Ajax返回结果集
	    print(arrResult);

	}

	/**
	 * @FunctionName: checkUser
	 * @Description: 验证用户，成功后取得菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-7-11
	 */
	private void checkUser(Map<String, String[]> inputdata) throws Exception {
		String userId = this.getString(inputdata, "USERID");// 用户ID
		String userPS = this.getString(inputdata, "PASSWORD");// 用户密码
		List<Pojo_MENU> menuList = null;// 菜单信息
		Pojo_YHXX userBean       = null;// 用户信息
		Pojo_USER_INFO userInfo  = null;// 登录用户信息
		try {
			/* 验证用户 */
			userBean = service.checkUserID(userId);
			if(userBean == null){
				arrResult.add("CMD_NOEXIST");
				return;
			}else{
				String dbPass = userBean.getYHXX_YHMM();
				if(!dbPass.equals(userPS)){//验证密码
					arrResult.add("CMD_PASS_ERR");
					return;
				}
			}

			/*用户验证通过，取得用户对应的菜单信息*/
			String strMenuFlag = "";
			String strJSLX = service.getUserJSLX(userId);//0-管理员 1-教师 2-学生
			if(strJSLX.equals("0")){
				menuList=service.getMenuInfoByUser(userId);
				userInfo = service.getUserInfo(userId,"0");
				strMenuFlag = "MENUMANAGE";
			}else if(strJSLX.equals("1")){
				menuList=service.getMenuInfoByTeacher(userId);
				userInfo = service.getUserInfo(userId,"1");
				strMenuFlag = "MENUSCHOOL";
			}else{//学生/其它
				menuList=service.getMenuInfoByStudent(userId);
				userInfo = service.getUserInfo(userId,"2");
				strMenuFlag = "MENUSCHOOL";
			}

			if(menuList==null){
				arrResult.add("CMD_MENU_NULL");
				return;
			}

			double randomNum = Math.random();
			File dstFile = new File(projPath+"/"+userBean.getYHXX_YHID()+".JPG");
			if (dstFile.exists()) {
				userInfo.setHEAD_PICS("upload/pics/"+userBean.getYHXX_YHID()+".JPG?id="+randomNum);
			}else{
				userInfo.setHEAD_PICS("img/resources/nopic2.png?id="+randomNum);
			}

			setSessionObject(SessionAttribute.USER_INFO_JSON, JSONArray.fromObject(userInfo).toString());//用户首页信息存入Session
			setSessionObject(SessionAttribute.LOGIN_USER, userBean);//登录用户信息存入Session
			setSessionObject(SessionAttribute.LOGIN_MENU, menuList);//用户菜单信息存入Session
			setSessionObject(SessionAttribute.LOGIN_MENU_JSON, JSONArray.fromObject(menuList).toString());

			arrResult.add("CMD_OK");
			arrResult.add(strMenuFlag);

		} catch (Exception e) {
			arrResult.add("CMD_EXCEPTION");
		}
	}

}
