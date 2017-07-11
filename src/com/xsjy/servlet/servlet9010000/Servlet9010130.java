package com.xsjy.servlet.servlet9010000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010120;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010130;
import com.xsjy.service.service9010000.Service9010130;

/**
 * 
 * @ClassName: Servlet9010130
 * @Package:com.xsjy.servlet.servlet9010000
 * @Description: 角色权限控制类
 * @author ztz
 * @date 2014年10月30日 上午11:06:47
 * @update
 */
/**
 * Servlet implementation class Servlet9010130
 */
@WebServlet("/Servlet9010130")
public class Servlet9010130 extends BaseServlet {
private static final long serialVersionUID = 1L;
	
	/* 命令定义部分 */
	public static final String CMD_ROLE_SELECT = "CMD_ROLE_SELECT";
	public static final String CMD_MENU_SELECT = "CMD_MENU_SELECT";
	public static final String CMD_RELATION = "CMD_RELATION";
	public static final String CMD_CANCEL_RELATION = "CMD_CANCEL_RELATION";
	
	/* 本SERVLET对应的Service */
	private Service9010130 service;
	
	/* AJAX返回前台的结果集 */
	private ArrayList<Object> result;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010130();
		result = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		if (CMD_ROLE_SELECT.equals(CMD)) {
			jsxxSelect(inputdata);
		} else if (CMD_MENU_SELECT.equals(CMD)) {
			menuSelect(inputdata);
		} else if (CMD_RELATION.equals(CMD)) {
			relationMenu(inputdata);
		} else if(CMD_CANCEL_RELATION.equals(CMD)) {
			cancelRelationMenu(inputdata);
		}
	}

	/**
	 * Constructor of the object.
	 */
	public Servlet9010130() {
		super();
	}
	/**
	 * 
	 * @FunctionName: jsxxSelect
	 * @Description: 获取角色信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年10月27日 下午3:28:59
	 */
	private void jsxxSelect(Map<String, String[]> inputdata) 
			throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		List<Pojo9010120> jsxxList = new ArrayList<Pojo9010120>();
		int dataCount = 0;
		
		try {
			dataCount = service.getJsxxDataCount();
			jsxxList = service.getJsxxData(page, limit, sort);
		} catch (Exception e) {
			result.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			printGrid(dataCount, jsxxList);
		}
	}
	/**
	 * 
	 * @FunctionName: menuSelect
	 * @Description: 获取菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年10月27日 下午4:09:49
	 */
	private void menuSelect(Map<String, String[]> inputdata) 
			throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		String jsid = this.getString(inputdata, "JSID");
		String jslx = this.getString(inputdata, "JSLX");
		List<Pojo9010130> menuList = new ArrayList<Pojo9010130>();
		int dataCount = 0;
		
		try {
			dataCount = service.getMenuDataCount(jslx);
			menuList = service.getMenuData(jsid, jslx, page, limit, sort);
		} catch (Exception e) {
			result.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			printGrid(dataCount, menuList);
		}
	}
	/**
	 * 
	 * @FunctionName: roleRelationMenu
	 * @Description: 角色关联菜单
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年10月28日 下午7:33:48
	 */
	private void relationMenu(Map<String, String[]> inputdata)
			throws Exception {
		String jsid = this.getString(inputdata, "JSID");
		String cdids = this.getString(inputdata, "CDIDS");
		cdids = cdids.substring(0, cdids.length() - 1);
		
		try {
			boolean res = service.relationMenu(jsid, cdids, beanUser);
			if(res) {
				result.add("CMD_OK");
			} else {
				result.add("CMD_ERROR");
			}
		} catch (Exception e) {
			result.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(result);
		}
	}
	/**
	 * 
	 * @FunctionName: cancelRelationMenu
	 * @Description: 取消角色关联菜单
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2014年10月28日 下午7:36:17
	 */
	private void cancelRelationMenu(Map<String, String[]> inputdata) 
			throws Exception {
		String jsid = this.getString(inputdata, "JSID");
		String cdids = this.getString(inputdata, "CDIDS");
		cdids = cdids.substring(0, cdids.length() - 1);
		
		try {
			boolean res = service.cancelRelationMenu(jsid, cdids, beanUser);
			if(res) {
				result.add("CMD_OK");
			} else {
				result.add("CMD_ERROR");
			}
		} catch (Exception e) {
			result.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(result);
		}
	}
}