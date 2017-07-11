package com.xsjy.servlet.common;

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
import com.xsjy.pojo.common.Pojo_SELECT_LIST;
import com.xsjy.service.common.CommonService;

/**
 * @ClassName: CommonServlet
 * @Package:com.hjlwl.servlet.common
 * @Description: 共通Servlet
 * @author ljg
 * @date 2014年7月21日 上午8:30:27
 * @update
 */
/**
 * Servlet implementation class CommonServlet
 */
@WebServlet("/CommonServlet")
public class CommonServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/* 命令定义部分 */
	public static final String LOAD_SELECT = "LOAD_SELECT";
	public static final String JOIN_LESSION = "JOIN_LESSION";
	public static final String GET_USER_SESSION = "GET_USER_SESSION";
	public static final String GET_USERID_SESSION = "GET_USERID_SESSION";
	public static final String CLEAR_SESSION = "CLEAR_SESSION";
	public static final String USER_NAME = "USER_NAME";
	public static final String MESS_COUNT = "MESS_COUNT";
	
	
	public static final String TYPE_YHJS			= "TYPE_YHJS"; //用户角色
	public static final String TYPE_ZDMC			= "TYPE_ZDMC"; //站点名称
	public static final String TYPE_SSJD			= "TYPE_SSJD"; //所属阶段
	public static final String TYPE_KMMC			= "TYPE_KMMC"; //科目名称
	public static final String TYPE_KCLX			= "TYPE_KCLX"; //课程类型
	public static final String TYPE_SKSD			= "TYPE_SKSD"; //上课时段  
	public static final String TYPE_JSXM			= "TYPE_JSXM"; //教师姓名
	public static final String TYPE_KCMC			= "TYPE_KCMC"; //课程名称
	public static final String TYPE_BMFS			= "TYPE_BMFS"; //报名方式
	public static final String TYPE_JFLX			= "TYPE_JFLX"; //交费类型
	public static final String TYPE_BMZT			= "TYPE_BMZT";//报名状态
	public static final String TYPE_XSNJ			= "TYPE_XSNJ"; //学生年级(当前年的上下5年)
	public static final String TYPE_XSXM			= "TYPE_XSXM"; //学生姓名
	public static final String TYPE_GZRQ         = "TYPE_GZRQ"; //工作日期
	public static final String TYPE_QSRQ         = "TYPE_QSRQ"; //起始日期
	public static final String TYPE_JSRQ         = "TYPE_JSRQ"; //结束日期
	public static final String TYPE_QJRQ         = "TYPE_QJRQ"; //起始日期=结束日期
	public static final String TYPE_BCMC         = "TYPE_BCMC"; //班次名称
	public static final String TYPE_JSZG         = "TYPE_JSZG"; //教师资格
	public static final String TYPE_QYMC         = "TYPE_QYMC"; //上级区域
	public static final String TYPE_YXSJ         = "TYPE_YXSJ"; //有效时间
	public static final String TYPE_BCMCXS       = "TYPE_BCMCXS"; //班次名称-学生模块
	
	/* 本Servlet对应的Service */
	private CommonService service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
       
    public CommonServlet() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {

		service = new CommonService();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");
		
		if (LOAD_SELECT.equals(CMD)) {
			loadSelectList(inputdata);
		}else if (JOIN_LESSION.equals(CMD)) {
			joinLession(inputdata);
		}else if (GET_USER_SESSION.equals(CMD)) {
			getUserSession(inputdata);
		}else if (GET_USERID_SESSION.equals(CMD)) {
			getUserIdSession(inputdata);
		}else if (CLEAR_SESSION.equals(CMD)) {
			this.clearSession();
		}else if (USER_NAME.equals(CMD)) {
			this.getUserName();
		}else if (MESS_COUNT.equals(CMD)) {
			this.getUserMessCount();
		}
	}
	
	private void loadSelectList(Map<String, String[]> inputdata) throws Exception{
		List<Pojo_SELECT_LIST> result = null;
		try{
			String strType = this.getString(inputdata, "TYPE");// 下拉列表种类
			if (TYPE_YHJS.equals(strType)) {//角色select
				result = service.getYhjsList();
			} else if (TYPE_ZDMC.equals(strType)) {//全部站点select
				result = service.getZdmcList();
			} else if (TYPE_SSJD.equals(strType)) {//所属阶段select
				result = service.getSsjdList();
			} else if (TYPE_KMMC.equals(strType)) {//科目名称select
				result = service.getKmmcList();
			} else if (TYPE_KCLX.equals(strType)) {//课程类型select
				result = service.getKclxList();
			} else if (TYPE_SKSD.equals(strType)) {//上课时段select
				result = service.getSksdList();
			} else if (TYPE_JSXM.equals(strType)) {//教师姓名select
				result = service.getJsxmList();
			} else if (TYPE_KCMC.equals(strType)) {//课程名称select
				result = service.getKcmcList();
			} else if (TYPE_BMFS.equals(strType)) {//报名方式select
				result = service.getBmfsList();
			} else if (TYPE_JFLX.equals(strType)) {//交费类型select
				result = service.getJflxList();
			} else if (TYPE_BMZT.equals(strType)) {//报名状态select
				result = service.getBmztList();
			} else if (TYPE_XSNJ.equals(strType)) {//学生年级select
				result = service.getXsnjList();
			} else if (TYPE_XSXM.equals(strType)) {//学生姓名select
				result = service.getXsxmList();
			} else if (strType.contains("TYPE_GZRQ")) {//工作年月-工作日期
				result = service.getDayListByNY(strType.split("-")[1]);
			} else if (strType.contains("TYPE_QSRQ")) {//工作年月-起始日期
				result = service.getDayListBegin(strType.split("-")[1]);
			} else if (strType.contains("TYPE_JSRQ")) {//工作年月-结束日期
				result = service.getDayListEnd(strType.split("-")[1]);
			} else if (strType.contains("TYPE_QJRQ")) {//工作年月-起始日期=结束日期
				result = service.getDayListBegin_End(strType.split("-")[1],strType.split("-")[2]);
			} else if(TYPE_BCMC.equals(strType)){
				Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
				result = service.getBCMCList(beanUser);
			} else if(strType.contains("TYPE_BCMC-")){
				Pojo_YHXX beanUser = new Pojo_YHXX();
				beanUser.setYHXX_YHID(strType.split("-")[1]);
				beanUser.setYHXX_JSID("105");
				result = service.getBCMCList(beanUser);
			} else if(TYPE_JSZG.equals(strType)){
				result = service.getJSZGList();
			} else if (strType.contains("TYPE_QYMC")) {//上级区域
				result = service.getQYList(strType.split("-")[1]);
			} else if(TYPE_YXSJ.equals(strType)){
				result = service.getYXSJList();
			} else if (TYPE_BCMCXS.equals(strType)) {//班次名称-学生
				Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
				result = service.getBCXSList(beanUser);
			}
			arrResult.add("SUCCESS");
			arrResult.add(result);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	
	private void joinLession(Map<String, String[]> inputdata) throws Exception{
		try{
			String strName = this.getString(inputdata, "NAME");// 下拉列表种类
			String strPhone = this.getString(inputdata, "PHONE");// 下拉列表种类
			String strRemark = this.getString(inputdata, "REMARK");// 下拉列表种类
			if(service.getJoinCount(strPhone)>0){
				arrResult.add("JOINED");	
			}else{
				if(service.insertJoinLession(strName, strPhone, strRemark)){
					arrResult.add("SUCCESS");		
				}else{
					arrResult.add("ERROR");
				}
			}
			
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	
	private void getUserSession(Map<String, String[]> inputdata) throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add(beanUser.getYHXX_YHMC());
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	
	private void getUserIdSession(Map<String, String[]> inputdata) throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add(beanUser.getYHXX_YHID());
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	private void getUserName() throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add("SUCCESS");
				String result = service.getUserName(beanUser.getYHXX_YHID());
				arrResult.add(result);
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	
	private void getUserMessCount() throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add("SUCCESS");
				String result = service.getUserMessCount(beanUser.getYHXX_UUID());
				arrResult.add(result);
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}

}
