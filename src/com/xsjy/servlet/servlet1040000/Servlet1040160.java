package com.xsjy.servlet.servlet1040000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.framework.session.SessionAttribute;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040160;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040161;
import com.xsjy.service.service1040000.Service1040160;

@WebServlet("/Servlet1040160")
public class Servlet1040160 extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_MUTI_SELECT = "CMD_MUTI_SELECT";
	public static final String CMD_MUTI_UPDATE = "CMD_MUTI_UPDATE";
	
	/* 本Servlet对应的Service */
	private Service1040160 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

       
    public Servlet1040160() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040160();
		arrResult = new ArrayList<Object>();
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getRCSDList(inputdata);
		}else if(CMD_UPDATE.equals(CMD)){
			updateRCSD(inputdata);
		}else if(CMD_MUTI_SELECT.equals(CMD)){
			getRCSDMutiList(inputdata);
		}else if(CMD_MUTI_UPDATE.equals(CMD)){
			updateMutiRCSD(inputdata);
		}
		
	}
	
	private void getRCSDList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040160 beanIn = (Pojo1040160) this.getObject(inputdata, "beanLoad",
				Pojo1040160.class);

		int TotalCount = 0;
		List<Pojo1040160> PageData = new ArrayList<Pojo1040160>();
		
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
	
	private void updateRCSD(Map<String, String[]> inputdata) throws Exception {
		Pojo1040160 beanIn = (Pojo1040160) this.getObject(inputdata, "BeanIn",
				Pojo1040160.class);

		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setBCRC_CJR(beanUser.getYHXX_YHID());
		beanIn.setBCRC_GXR(beanUser.getYHXX_YHID());
		
		try {
			int intResult = 0;
			
			//如果排班记录,则插入
			if(MyStringUtils.isBlank(beanIn.getBCRC_BCRCID())){
				 String strUUID = service.insertRCSD(beanIn);
				 if(!"".equals(strUUID)){
					 beanIn.setBCRC_BCRCID(strUUID);
				 }else{
					 arrResult.add("FAILURE");
					 return;
				 }
			}
			
			//已有排班记录,更新
			intResult = service.updateRCSD(beanIn);
			
			if(intResult>0){
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
	
	private void getRCSDMutiList(Map<String, String[]> inputdata) throws Exception {
		
		Pojo1040161 beanIn = (Pojo1040161) this.getObject(inputdata, "beanLoad",
				Pojo1040161.class);
		Pojo1040161 resultList = null;
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
	
	private void updateMutiRCSD(Map<String, String[]> inputdata) throws Exception {
		Pojo1040160 beanIn = (Pojo1040160) this.getObject(inputdata, "BeanIn",
				Pojo1040160.class);
		
		String dataDays = (String) this.getString(inputdata,"dataDays");
		JSONArray jsonArray = JSONArray.fromObject(dataDays);

		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setBCRC_CJR(beanUser.getYHXX_YHID());
		beanIn.setBCRC_GXR(beanUser.getYHXX_YHID());
		
		try {
			int intResult = 0;
			
			//如果排班记录,则插入
			if(MyStringUtils.isBlank(beanIn.getBCRC_BCRCID())){
				 String strUUID = service.insertRCSD(beanIn);
				 if(!"".equals(strUUID)){
					 beanIn.setBCRC_BCRCID(strUUID);
				 }else{
					 arrResult.add("FAILURE");
					 return;
				 }
			}
			
			//已有排班记录,更新
			intResult = service.updateMuitRCSD(beanIn, jsonArray.toArray());
			
			if(intResult>0){
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
