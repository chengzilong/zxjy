package com.xsjy.servlet.servlet3030000;

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
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030130;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030131;
import com.xsjy.service.service3030000.Service3030130;

/**
 *
 * @ClassName: Servlet3030130
 * @Package:com.xsjy.servlet.servlet3030000
 * @Description: 课表日常控制类
 * @author ztz
 * @date 2015年1月6日 上午11:04:47
 */
/**
 * Servlet implementation class Servlet3030130
 */
@WebServlet("/Servlet3030130")
public class Servlet3030130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_MUTI_SELECT = "CMD_MUTI_SELECT";
	public static final String CMD_MUTI_UPDATE = "CMD_MUTI_UPDATE";

	/* 本Servlet对应的Service */
	private Service3030130 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet3030130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service3030130();
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
	/**
	 *
	 * @FunctionName: getRCSDList
	 * @Description: 获取数据个数和列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-07
	 */
	private void getRCSDList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo3030130 beanIn = (Pojo3030130) this.getObject(inputdata, "beanLoad",
				Pojo3030130.class);

		int TotalCount = 0;
		List<Pojo3030130> PageData = new ArrayList<Pojo3030130>();

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
	/**
	 *
	 * @FunctionName: updateRCSD
	 * @Description: 更新数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月6日 上午11:05:46
	 */
	private void updateRCSD(Map<String, String[]> inputdata) throws Exception {
		Pojo3030130 beanIn = (Pojo3030130) this.getObject(inputdata, "BeanIn",
				Pojo3030130.class);

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
	/**
	 *
	 * @FunctionName: getRCSDMutiList
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2017-08-07
	 */
	private void getRCSDMutiList(Map<String, String[]> inputdata) throws Exception {

		Pojo3030131 beanIn = (Pojo3030131) this.getObject(inputdata, "beanLoad",
				Pojo3030131.class);
		Pojo3030131 resultList = null;
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
	/**
	 *
	 * @FunctionName: updateMutiRCSD
	 * @Description: 更新数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月6日 上午11:06:22
	 */
	private void updateMutiRCSD(Map<String, String[]> inputdata) throws Exception {
		Pojo3030130 beanIn = (Pojo3030130) this.getObject(inputdata, "BeanIn",
				Pojo3030130.class);

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