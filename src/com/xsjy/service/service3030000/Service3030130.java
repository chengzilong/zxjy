package com.xsjy.service.service3030000;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_BCXX;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040160;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030130;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030131;

public class Service3030130 extends BaseService {
	private DBManager db = null;

	public Service3030130() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getRCSDList_TotalCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-07
	 */
	public int getRCSDList_TotalCount(Pojo3030130 beanIn) throws Exception {
		int result = 0;
		try {
			    db.openConnection();
				Pojo_BCXX bcxx = null;
				if(MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())&&!("000").equals(beanIn.getBCRC_BCID())){
					bcxx = this.getBCRQ(beanIn.getBCRC_BCID());
					String btime = bcxx.getBCXX_KSSJ();
					String etime = bcxx.getBCXX_JSSJ();
					String betweens = getBetweenDate(btime,etime);
					betweens = betweens.substring(0, betweens.length() - 1);
					String[] between = betweens.split(",");
					result = between.length;
				}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 *
	 * @FunctionName: getRCSDList_PageData
	 * @Description: 查询明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3030130>
	 * @author czl
	 * @date 2017-08-07
	 */
	public <T> List<Pojo3030130> getRCSDList_PageData(Pojo3030130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030130> result = null;

		try {
			db.openConnection();
			db.beginTran();
			Pojo_BCXX bcxx = null;
			if(MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())&&!("000").equals(beanIn.getBCRC_BCID())){
				bcxx = this.getBCRQ(beanIn.getBCRC_BCID());
				String btime = bcxx.getBCXX_KSSJ();
				String etime = bcxx.getBCXX_JSSJ();
				String betweens = getBetweenDate(btime,etime);
				String newbetweens = "";
				betweens = betweens.substring(0, betweens.length() - 1);
				String[] between = betweens.split(",");

				List<Pojo1040160> PageData = new ArrayList<Pojo1040160>();
				PageData = getNYList(beanIn.getBCRC_BCID());
				String[] bet = new String[PageData.size()];
				for(int a=0;a<PageData.size();a++){
		    		bet[a]=PageData.get(a).getBCRC_NY();
		    	 }
				for(String aItem:between){
					boolean flag = true;
					for(String bItem:bet){
					if(aItem.equals(bItem)){
					flag = false;
					break;
					}
					}
					if(flag){
						newbetweens+=aItem+",";
					}
					}
				String[] newbetween = new String[0];
				if(!newbetweens.equals("")){
					newbetween = newbetweens.split(",");
				}
			    StringBuffer strbuf = new StringBuffer();
				if(PageData.size()==0){
					strbuf.append(" SELECT A.*,A.BCRC_NY AS NY FROM(SELECT ");
					strbuf.append("     B.BCRC_BCRCID, ");//班次日程ID
					strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
					strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
					strbuf.append("     D.KCFY_SDID AS SDID, ");//时段ID
					strbuf.append("     E.SKSD_SDMC AS SDMC, ");//时段名称
					strbuf.append("     CONCAT(A.BCXX_KSSJ,'至',A.BCXX_JSSJ) AS BCSD, ");//班次时段
					strbuf.append("     '"+between[0]+"' AS BCRC_NY, ");//上课年月
					strbuf.append("     F01.SKSD_SDJC AS BCRC_DAY01, ");//01日
					strbuf.append("     F02.SKSD_SDJC AS BCRC_DAY02, ");//02日
					strbuf.append("     F03.SKSD_SDJC AS BCRC_DAY03, ");//03日
					strbuf.append("     F04.SKSD_SDJC AS BCRC_DAY04, ");//04日
					strbuf.append("     F05.SKSD_SDJC AS BCRC_DAY05, ");//05日
					strbuf.append("     F06.SKSD_SDJC AS BCRC_DAY06, ");//06日
					strbuf.append("     F07.SKSD_SDJC AS BCRC_DAY07, ");//07日
					strbuf.append("     F08.SKSD_SDJC AS BCRC_DAY08, ");//08日
					strbuf.append("     F09.SKSD_SDJC AS BCRC_DAY09, ");//09日
					strbuf.append("     F10.SKSD_SDJC AS BCRC_DAY10, ");//10日
					strbuf.append("     F11.SKSD_SDJC AS BCRC_DAY11, ");//11日
					strbuf.append("     F12.SKSD_SDJC AS BCRC_DAY12, ");//12日
					strbuf.append("     F13.SKSD_SDJC AS BCRC_DAY13, ");//13日
					strbuf.append("     F14.SKSD_SDJC AS BCRC_DAY14, ");//14日
					strbuf.append("     F15.SKSD_SDJC AS BCRC_DAY15, ");//15日
					strbuf.append("     F16.SKSD_SDJC AS BCRC_DAY16, ");//16日
					strbuf.append("     F17.SKSD_SDJC AS BCRC_DAY17, ");//17日
					strbuf.append("     F18.SKSD_SDJC AS BCRC_DAY18, ");//18日
					strbuf.append("     F19.SKSD_SDJC AS BCRC_DAY19, ");//19日
					strbuf.append("     F20.SKSD_SDJC AS BCRC_DAY20, ");//20日
					strbuf.append("     F21.SKSD_SDJC AS BCRC_DAY21, ");//21日
					strbuf.append("     F22.SKSD_SDJC AS BCRC_DAY22, ");//22日
					strbuf.append("     F23.SKSD_SDJC AS BCRC_DAY23, ");//23日
					strbuf.append("     F24.SKSD_SDJC AS BCRC_DAY24, ");//24日
					strbuf.append("     F25.SKSD_SDJC AS BCRC_DAY25, ");//25日
					strbuf.append("     F26.SKSD_SDJC AS BCRC_DAY26, ");//26日
					strbuf.append("     F27.SKSD_SDJC AS BCRC_DAY27, ");//27日
					strbuf.append("     F28.SKSD_SDJC AS BCRC_DAY28, ");//28日
					strbuf.append("     F29.SKSD_SDJC AS BCRC_DAY29, ");//29日
					strbuf.append("     F30.SKSD_SDJC AS BCRC_DAY30, ");//30日
					strbuf.append("     F31.SKSD_SDJC AS BCRC_DAY31  ");//31日
					strbuf.append("  FROM BCXX A ");
					strbuf.append("  LEFT JOIN KCXX C ON A.BCXX_KCID = C.KCXX_KCID ");
					strbuf.append("  LEFT JOIN BCRC B ON A.BCXX_BCID = B.BCRC_BCID ");
					strbuf.append("  LEFT JOIN KCFY D ON A.BCXX_KCFYID = D.KCFY_FYID ");
					strbuf.append("  LEFT JOIN SKSD E ON D.KCFY_SDID = E.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F01 ON B.BCRC_DAY01 = F01.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F02 ON B.BCRC_DAY02 = F02.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F03 ON B.BCRC_DAY02 = F03.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F04 ON B.BCRC_DAY02 = F04.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F05 ON B.BCRC_DAY02 = F05.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F06 ON B.BCRC_DAY02 = F06.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F07 ON B.BCRC_DAY02 = F07.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F08 ON B.BCRC_DAY02 = F08.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F09 ON B.BCRC_DAY02 = F09.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F10 ON B.BCRC_DAY02 = F10.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F11 ON B.BCRC_DAY02 = F11.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F12 ON B.BCRC_DAY02 = F12.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F13 ON B.BCRC_DAY02 = F13.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F14 ON B.BCRC_DAY02 = F14.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F15 ON B.BCRC_DAY02 = F15.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F16 ON B.BCRC_DAY02 = F16.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F17 ON B.BCRC_DAY02 = F17.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F18 ON B.BCRC_DAY02 = F18.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F19 ON B.BCRC_DAY02 = F19.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F20 ON B.BCRC_DAY02 = F20.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F21 ON B.BCRC_DAY02 = F21.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F22 ON B.BCRC_DAY02 = F22.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F23 ON B.BCRC_DAY02 = F23.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F24 ON B.BCRC_DAY02 = F24.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F25 ON B.BCRC_DAY02 = F25.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F26 ON B.BCRC_DAY02 = F26.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F27 ON B.BCRC_DAY02 = F27.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F28 ON B.BCRC_DAY02 = F28.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F29 ON B.BCRC_DAY02 = F29.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F30 ON B.BCRC_DAY02 = F30.SKSD_SDID ");
					strbuf.append("  LEFT JOIN SKSD F31 ON B.BCRC_DAY02 = F31.SKSD_SDID ");
					strbuf.append(" WHERE 1=1 ");
					if (MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())) {
						strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getBCRC_BCID())
							.append("'");
					}
				    for(int i = 0; i < between.length-1; i++){
				    	strbuf.append(" UNION");
				    	strbuf.append(" SELECT ");
						strbuf.append("     B.BCRC_BCRCID, ");//班次日程ID
						strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
						strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
						strbuf.append("     D.KCFY_SDID AS SDID, ");//时段ID
						strbuf.append("     E.SKSD_SDMC AS SDMC, ");//时段名称
						strbuf.append("     CONCAT(A.BCXX_KSSJ,'至',A.BCXX_JSSJ) AS BCSD, ");//班次时段
						strbuf.append("     '"+between[i+1]+"' AS BCRC_NY, ");//上课年月
						strbuf.append("     F01.SKSD_SDJC AS BCRC_DAY01, ");//01日
						strbuf.append("     F02.SKSD_SDJC AS BCRC_DAY02, ");//02日
						strbuf.append("     F03.SKSD_SDJC AS BCRC_DAY03, ");//03日
						strbuf.append("     F04.SKSD_SDJC AS BCRC_DAY04, ");//04日
						strbuf.append("     F05.SKSD_SDJC AS BCRC_DAY05, ");//05日
						strbuf.append("     F06.SKSD_SDJC AS BCRC_DAY06, ");//06日
						strbuf.append("     F07.SKSD_SDJC AS BCRC_DAY07, ");//07日
						strbuf.append("     F08.SKSD_SDJC AS BCRC_DAY08, ");//08日
						strbuf.append("     F09.SKSD_SDJC AS BCRC_DAY09, ");//09日
						strbuf.append("     F10.SKSD_SDJC AS BCRC_DAY10, ");//10日
						strbuf.append("     F11.SKSD_SDJC AS BCRC_DAY11, ");//11日
						strbuf.append("     F12.SKSD_SDJC AS BCRC_DAY12, ");//12日
						strbuf.append("     F13.SKSD_SDJC AS BCRC_DAY13, ");//13日
						strbuf.append("     F14.SKSD_SDJC AS BCRC_DAY14, ");//14日
						strbuf.append("     F15.SKSD_SDJC AS BCRC_DAY15, ");//15日
						strbuf.append("     F16.SKSD_SDJC AS BCRC_DAY16, ");//16日
						strbuf.append("     F17.SKSD_SDJC AS BCRC_DAY17, ");//17日
						strbuf.append("     F18.SKSD_SDJC AS BCRC_DAY18, ");//18日
						strbuf.append("     F19.SKSD_SDJC AS BCRC_DAY19, ");//19日
						strbuf.append("     F20.SKSD_SDJC AS BCRC_DAY20, ");//20日
						strbuf.append("     F21.SKSD_SDJC AS BCRC_DAY21, ");//21日
						strbuf.append("     F22.SKSD_SDJC AS BCRC_DAY22, ");//22日
						strbuf.append("     F23.SKSD_SDJC AS BCRC_DAY23, ");//23日
						strbuf.append("     F24.SKSD_SDJC AS BCRC_DAY24, ");//24日
						strbuf.append("     F25.SKSD_SDJC AS BCRC_DAY25, ");//25日
						strbuf.append("     F26.SKSD_SDJC AS BCRC_DAY26, ");//26日
						strbuf.append("     F27.SKSD_SDJC AS BCRC_DAY27, ");//27日
						strbuf.append("     F28.SKSD_SDJC AS BCRC_DAY28, ");//28日
						strbuf.append("     F29.SKSD_SDJC AS BCRC_DAY29, ");//29日
						strbuf.append("     F30.SKSD_SDJC AS BCRC_DAY30, ");//30日
						strbuf.append("     F31.SKSD_SDJC AS BCRC_DAY31  ");//31日
						strbuf.append("  FROM BCXX A ");
						strbuf.append("  LEFT JOIN KCXX C ON A.BCXX_KCID = C.KCXX_KCID ");
						strbuf.append("  LEFT JOIN KCFY D ON A.BCXX_KCFYID = D.KCFY_FYID ");
						strbuf.append("  LEFT JOIN SKSD E ON D.KCFY_SDID = E.SKSD_SDID ");
						strbuf.append("  LEFT JOIN ");
						strbuf.append("  (SELECT * FROM BCRC WHERE BCRC_NY='"+between[i+1]+"') B ON A.BCXX_BCID = B.BCRC_BCID ");
						strbuf.append("  LEFT JOIN SKSD F01 ON B.BCRC_DAY01 = F01.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F02 ON B.BCRC_DAY02 = F02.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F03 ON B.BCRC_DAY02 = F03.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F04 ON B.BCRC_DAY02 = F04.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F05 ON B.BCRC_DAY02 = F05.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F06 ON B.BCRC_DAY02 = F06.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F07 ON B.BCRC_DAY02 = F07.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F08 ON B.BCRC_DAY02 = F08.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F09 ON B.BCRC_DAY02 = F09.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F10 ON B.BCRC_DAY02 = F10.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F11 ON B.BCRC_DAY02 = F11.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F12 ON B.BCRC_DAY02 = F12.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F13 ON B.BCRC_DAY02 = F13.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F14 ON B.BCRC_DAY02 = F14.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F15 ON B.BCRC_DAY02 = F15.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F16 ON B.BCRC_DAY02 = F16.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F17 ON B.BCRC_DAY02 = F17.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F18 ON B.BCRC_DAY02 = F18.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F19 ON B.BCRC_DAY02 = F19.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F20 ON B.BCRC_DAY02 = F20.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F21 ON B.BCRC_DAY02 = F21.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F22 ON B.BCRC_DAY02 = F22.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F23 ON B.BCRC_DAY02 = F23.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F24 ON B.BCRC_DAY02 = F24.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F25 ON B.BCRC_DAY02 = F25.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F26 ON B.BCRC_DAY02 = F26.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F27 ON B.BCRC_DAY02 = F27.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F28 ON B.BCRC_DAY02 = F28.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F29 ON B.BCRC_DAY02 = F29.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F30 ON B.BCRC_DAY02 = F30.SKSD_SDID ");
						strbuf.append("  LEFT JOIN SKSD F31 ON B.BCRC_DAY02 = F31.SKSD_SDID ");
						strbuf.append("  WHERE 1=1 ");
						if (MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())) {
							strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getBCRC_BCID())
								.append("'");
						}
				    }
				    strbuf.append("   ) A ORDER BY NY");
				}else{
					strbuf.append(" SELECT A.*,A.BCRC_NY AS NY FROM(SELECT ");
					strbuf.append("     B.BCRC_BCRCID, ");//班次日程ID
					strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
					strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
					strbuf.append("     D.KCFY_SDID AS SDID, ");//时段ID
					strbuf.append("     E.SKSD_SDMC AS SDMC, ");//时段名称
					strbuf.append("     CONCAT(A.BCXX_KSSJ,'至',A.BCXX_JSSJ) AS BCSD, ");//班次时段
					strbuf.append("     BCRC_NY, ");//上课年月
					strbuf.append("     F01.SKSD_SDJC AS BCRC_DAY01, ");//01日
					strbuf.append("     F02.SKSD_SDJC AS BCRC_DAY02, ");//02日
					strbuf.append("     F03.SKSD_SDJC AS BCRC_DAY03, ");//03日
					strbuf.append("     F04.SKSD_SDJC AS BCRC_DAY04, ");//04日
					strbuf.append("     F05.SKSD_SDJC AS BCRC_DAY05, ");//05日
					strbuf.append("     F06.SKSD_SDJC AS BCRC_DAY06, ");//06日
					strbuf.append("     F07.SKSD_SDJC AS BCRC_DAY07, ");//07日
					strbuf.append("     F08.SKSD_SDJC AS BCRC_DAY08, ");//08日
					strbuf.append("     F09.SKSD_SDJC AS BCRC_DAY09, ");//09日
					strbuf.append("     F10.SKSD_SDJC AS BCRC_DAY10, ");//10日
					strbuf.append("     F11.SKSD_SDJC AS BCRC_DAY11, ");//11日
					strbuf.append("     F12.SKSD_SDJC AS BCRC_DAY12, ");//12日
					strbuf.append("     F13.SKSD_SDJC AS BCRC_DAY13, ");//13日
					strbuf.append("     F14.SKSD_SDJC AS BCRC_DAY14, ");//14日
					strbuf.append("     F15.SKSD_SDJC AS BCRC_DAY15, ");//15日
					strbuf.append("     F16.SKSD_SDJC AS BCRC_DAY16, ");//16日
					strbuf.append("     F17.SKSD_SDJC AS BCRC_DAY17, ");//17日
					strbuf.append("     F18.SKSD_SDJC AS BCRC_DAY18, ");//18日
					strbuf.append("     F19.SKSD_SDJC AS BCRC_DAY19, ");//19日
					strbuf.append("     F20.SKSD_SDJC AS BCRC_DAY20, ");//20日
					strbuf.append("     F21.SKSD_SDJC AS BCRC_DAY21, ");//21日
					strbuf.append("     F22.SKSD_SDJC AS BCRC_DAY22, ");//22日
					strbuf.append("     F23.SKSD_SDJC AS BCRC_DAY23, ");//23日
					strbuf.append("     F24.SKSD_SDJC AS BCRC_DAY24, ");//24日
					strbuf.append("     F25.SKSD_SDJC AS BCRC_DAY25, ");//25日
					strbuf.append("     F26.SKSD_SDJC AS BCRC_DAY26, ");//26日
					strbuf.append("     F27.SKSD_SDJC AS BCRC_DAY27, ");//27日
					strbuf.append("     F28.SKSD_SDJC AS BCRC_DAY28, ");//28日
					strbuf.append("     F29.SKSD_SDJC AS BCRC_DAY29, ");//29日
					strbuf.append("     F30.SKSD_SDJC AS BCRC_DAY30, ");//30日
					strbuf.append("     F31.SKSD_SDJC AS BCRC_DAY31  ");//31日
					strbuf.append("  FROM BCXX A ");
	                strbuf.append("  LEFT JOIN KCXX C ON A.BCXX_KCID = C.KCXX_KCID ");
	                strbuf.append("  LEFT JOIN BCRC B ON A.BCXX_BCID = B.BCRC_BCID ");
	                strbuf.append("  LEFT JOIN KCFY D ON A.BCXX_KCFYID = D.KCFY_FYID ");
	                strbuf.append("  LEFT JOIN SKSD E ON D.KCFY_SDID = E.SKSD_SDID ");
	                strbuf.append("  LEFT JOIN SKSD F01 ON B.BCRC_DAY01 = F01.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F02 ON B.BCRC_DAY02 = F02.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F03 ON B.BCRC_DAY02 = F03.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F04 ON B.BCRC_DAY02 = F04.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F05 ON B.BCRC_DAY02 = F05.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F06 ON B.BCRC_DAY02 = F06.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F07 ON B.BCRC_DAY02 = F07.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F08 ON B.BCRC_DAY02 = F08.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F09 ON B.BCRC_DAY02 = F09.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F10 ON B.BCRC_DAY02 = F10.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F11 ON B.BCRC_DAY02 = F11.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F12 ON B.BCRC_DAY02 = F12.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F13 ON B.BCRC_DAY02 = F13.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F14 ON B.BCRC_DAY02 = F14.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F15 ON B.BCRC_DAY02 = F15.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F16 ON B.BCRC_DAY02 = F16.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F17 ON B.BCRC_DAY02 = F17.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F18 ON B.BCRC_DAY02 = F18.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F19 ON B.BCRC_DAY02 = F19.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F20 ON B.BCRC_DAY02 = F20.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F21 ON B.BCRC_DAY02 = F21.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F22 ON B.BCRC_DAY02 = F22.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F23 ON B.BCRC_DAY02 = F23.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F24 ON B.BCRC_DAY02 = F24.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F25 ON B.BCRC_DAY02 = F25.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F26 ON B.BCRC_DAY02 = F26.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F27 ON B.BCRC_DAY02 = F27.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F28 ON B.BCRC_DAY02 = F28.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F29 ON B.BCRC_DAY02 = F29.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F30 ON B.BCRC_DAY02 = F30.SKSD_SDID ");
                    strbuf.append("  LEFT JOIN SKSD F31 ON B.BCRC_DAY02 = F31.SKSD_SDID ");
                    strbuf.append("  WHERE 1=1 ");
					if (MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())) {
						strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getBCRC_BCID())
							.append("'");
					}
				    for(int i = 0; i < newbetween.length; i++){
				    	strbuf.append(" UNION");
				    	strbuf.append(" SELECT ");
						strbuf.append("     B.BCRC_BCRCID, ");//班次日程ID
						strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
						strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
						strbuf.append("     D.KCFY_SDID AS SDID, ");//时段ID
						strbuf.append("     E.SKSD_SDMC AS SDMC, ");//时段名称
						strbuf.append("     CONCAT(A.BCXX_KSSJ,'至',A.BCXX_JSSJ) AS BCSD, ");//班次时段
						strbuf.append("     '"+newbetween[i]+"' AS BCRC_NY, ");//上课年月
						strbuf.append("     F01.SKSD_SDJC AS BCRC_DAY01, ");//01日
						strbuf.append("     F02.SKSD_SDJC AS BCRC_DAY02, ");//02日
						strbuf.append("     F03.SKSD_SDJC AS BCRC_DAY03, ");//03日
						strbuf.append("     F04.SKSD_SDJC AS BCRC_DAY04, ");//04日
						strbuf.append("     F05.SKSD_SDJC AS BCRC_DAY05, ");//05日
						strbuf.append("     F06.SKSD_SDJC AS BCRC_DAY06, ");//06日
						strbuf.append("     F07.SKSD_SDJC AS BCRC_DAY07, ");//07日
						strbuf.append("     F08.SKSD_SDJC AS BCRC_DAY08, ");//08日
						strbuf.append("     F09.SKSD_SDJC AS BCRC_DAY09, ");//09日
						strbuf.append("     F10.SKSD_SDJC AS BCRC_DAY10, ");//10日
						strbuf.append("     F11.SKSD_SDJC AS BCRC_DAY11, ");//11日
						strbuf.append("     F12.SKSD_SDJC AS BCRC_DAY12, ");//12日
						strbuf.append("     F13.SKSD_SDJC AS BCRC_DAY13, ");//13日
						strbuf.append("     F14.SKSD_SDJC AS BCRC_DAY14, ");//14日
						strbuf.append("     F15.SKSD_SDJC AS BCRC_DAY15, ");//15日
						strbuf.append("     F16.SKSD_SDJC AS BCRC_DAY16, ");//16日
						strbuf.append("     F17.SKSD_SDJC AS BCRC_DAY17, ");//17日
						strbuf.append("     F18.SKSD_SDJC AS BCRC_DAY18, ");//18日
						strbuf.append("     F19.SKSD_SDJC AS BCRC_DAY19, ");//19日
						strbuf.append("     F20.SKSD_SDJC AS BCRC_DAY20, ");//20日
						strbuf.append("     F21.SKSD_SDJC AS BCRC_DAY21, ");//21日
						strbuf.append("     F22.SKSD_SDJC AS BCRC_DAY22, ");//22日
						strbuf.append("     F23.SKSD_SDJC AS BCRC_DAY23, ");//23日
						strbuf.append("     F24.SKSD_SDJC AS BCRC_DAY24, ");//24日
						strbuf.append("     F25.SKSD_SDJC AS BCRC_DAY25, ");//25日
						strbuf.append("     F26.SKSD_SDJC AS BCRC_DAY26, ");//26日
						strbuf.append("     F27.SKSD_SDJC AS BCRC_DAY27, ");//27日
						strbuf.append("     F28.SKSD_SDJC AS BCRC_DAY28, ");//28日
						strbuf.append("     F29.SKSD_SDJC AS BCRC_DAY29, ");//29日
						strbuf.append("     F30.SKSD_SDJC AS BCRC_DAY30, ");//30日
						strbuf.append("     F31.SKSD_SDJC AS BCRC_DAY31  ");//31日
						strbuf.append("  FROM BCXX A ");
						strbuf.append("  LEFT JOIN KCXX C ON A.BCXX_KCID = C.KCXX_KCID ");
                        strbuf.append("  LEFT JOIN KCFY D ON A.BCXX_KCFYID = D.KCFY_FYID ");
                        strbuf.append("  LEFT JOIN SKSD E ON D.KCFY_SDID = E.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN ");
                        strbuf.append("  (SELECT * FROM BCRC WHERE BCRC_NY='"+between[i+1]+"') B ON A.BCXX_BCID = B.BCRC_BCID ");
                        strbuf.append("  LEFT JOIN SKSD F01 ON B.BCRC_DAY01 = F01.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F02 ON B.BCRC_DAY02 = F02.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F03 ON B.BCRC_DAY02 = F03.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F04 ON B.BCRC_DAY02 = F04.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F05 ON B.BCRC_DAY02 = F05.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F06 ON B.BCRC_DAY02 = F06.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F07 ON B.BCRC_DAY02 = F07.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F08 ON B.BCRC_DAY02 = F08.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F09 ON B.BCRC_DAY02 = F09.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F10 ON B.BCRC_DAY02 = F10.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F11 ON B.BCRC_DAY02 = F11.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F12 ON B.BCRC_DAY02 = F12.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F13 ON B.BCRC_DAY02 = F13.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F14 ON B.BCRC_DAY02 = F14.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F15 ON B.BCRC_DAY02 = F15.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F16 ON B.BCRC_DAY02 = F16.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F17 ON B.BCRC_DAY02 = F17.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F18 ON B.BCRC_DAY02 = F18.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F19 ON B.BCRC_DAY02 = F19.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F20 ON B.BCRC_DAY02 = F20.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F21 ON B.BCRC_DAY02 = F21.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F22 ON B.BCRC_DAY02 = F22.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F23 ON B.BCRC_DAY02 = F23.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F24 ON B.BCRC_DAY02 = F24.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F25 ON B.BCRC_DAY02 = F25.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F26 ON B.BCRC_DAY02 = F26.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F27 ON B.BCRC_DAY02 = F27.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F28 ON B.BCRC_DAY02 = F28.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F29 ON B.BCRC_DAY02 = F29.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F30 ON B.BCRC_DAY02 = F30.SKSD_SDID ");
                        strbuf.append("  LEFT JOIN SKSD F31 ON B.BCRC_DAY02 = F31.SKSD_SDID ");
						strbuf.append(" WHERE 1=1 ");
						if (MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())) {
							strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getBCRC_BCID())
								.append("'");
						}

				    }
				    strbuf.append("   ) A ORDER BY NY");
				}
				StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
						sort);
				ResultSetHandler<List<Pojo3030130>> rs = new BeanListHandler<Pojo3030130>(
						Pojo3030130.class);

				result = db.queryForBeanListHandler(execSql, rs);
			}

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 *
	 * @FunctionName: getNYList
	 * @Description: 查询年月
	 * @param bcid
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040160>
	 * @author czl
	 * @date 2017-08-07
	 */
	public List<Pojo1040160> getNYList(String bcid) throws Exception {
		List<Pojo1040160> result = null;
		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         BCRC_NY");
			strbuf.append(" FROM ");
			strbuf.append("         BCRC ");
			strbuf.append(" WHERE ");
			strbuf.append("         BCRC_BCID = '"+bcid+"' ");
			ResultSetHandler<List<Pojo1040160>> rs = new BeanListHandler<Pojo1040160>(
					Pojo1040160.class);
			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
		}
		return result;
	}
	/**
	 *
	 * @FunctionName: getBCRQ
	 * @Description: 查询班次日期跨度
	 * @param bcid
	 * @return
	 * @throws Exception
	 * @return Pojo_BCXX
	 * @author czl
	 * @date 2017-08-07
	 */
	private Pojo_BCXX getBCRQ(String bcid) throws Exception {
			StringBuffer strbuf = new StringBuffer();
			Pojo_BCXX bcxx = new Pojo_BCXX();
			try {
					strbuf.append(" SELECT ");
					strbuf.append("     BCXX_KSSJ,");//开始时间
					strbuf.append("     BCXX_JSSJ");//结束时间
					strbuf.append(" FROM ");
					strbuf.append("     BCXX ");
					strbuf.append("   WHERE BCXX_BCID = '").append(bcid).append("'");

					ResultSetHandler<Pojo_BCXX> rsh = new BeanHandler<Pojo_BCXX>(
							Pojo_BCXX.class);

					bcxx = db.queryForBeanHandler(strbuf, rsh);
			} catch (Exception e) {
					MyLogger.error(this.getClass().getName(), e);
					throw e;
			}
				return bcxx;
	}
	/**
	 *
	 * @FunctionName: getBetweenDate
	 * @Description: 查询两个日期中间的月份
	 * @param btime
	 * @param etime
	 * @return
	 * @return String
	 * @author ztz
	 * @date 2015年1月6日 上午10:59:13
	 */
	public String getBetweenDate(String btime,String etime){
		String[] arg1 = btime.split("-");
        String[] arg2 = etime.split("-");
        int year1 = Integer.valueOf(arg1[0]);
        int year2 = Integer.valueOf(arg2[0]);
        int month1 = Integer.valueOf(arg1[1]);
        int month2 = Integer.valueOf(arg2[1]);
        String month = "";
        for (int i = year1; i <= year2; i++) {
            int monthCount = 12;
            int monthStart = 1;
            if (i == year1) {
                monthStart = month1;
                if(year1==year2){
                	monthCount = month2-monthStart+1;
                }else{
                	monthCount = 12-monthStart+1;
                }

            } else if (i == year2) {
                monthCount = month2;
            }
            for(int j = 0; j < monthCount; j++){
                int temp = monthStart+j;
                if(temp >=10){
                    month += i+"-"+(monthStart+j)+",";
                }else{
                    month += i+"-0"+(monthStart+j)+",";
                }
            }
        }
        return month;
	}
	/**
	 *
	 * @FunctionName: insertRCSD
	 * @Description: 插入日程设定
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月6日 上午10:59:25
	 */
	public String insertRCSD(Pojo3030130 beanIn) throws Exception {
		String result = "";
		try {
			db.openConnection();
			String strUUId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     BCRC ");
			strbuf.append(" ( ");
			strbuf.append("     BCRC_BCRCID	, ");//班次日程ID
			strbuf.append("     BCRC_BCID	, ");//班次ID
			strbuf.append("     BCRC_NY	, ");//年月
			strbuf.append("     BCRC_CJR	, ");//创建人
			strbuf.append("     BCRC_CJSJ	, ");//创建时间
			strbuf.append("     BCRC_GXR	, ");//更新人
			strbuf.append("     BCRC_GXSJ	");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strUUId+"', ");
			strbuf.append("     '"+beanIn.getBCRC_BCID()+"', ");
			strbuf.append("     '"+beanIn.getBCRC_NY()+"', ");
			strbuf.append("     '"+beanIn.getBCRC_CJR()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanIn.getBCRC_GXR()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS') ");//更新时间
			strbuf.append(" ) ");

			int i = db.executeSQL(strbuf);
			if(i>0){
				result = strUUId;
			}

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 *
	 * @FunctionName: updateRCSD
	 * @Description: 修改日程设定
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月6日 上午10:59:40
	 */
	public int updateRCSD(Pojo3030130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("        BCRC ");
			strbuf.append(" SET ");
			if(beanIn.getSFYK().equals("1")){
				strbuf.append("        BCRC_DAY").append(beanIn.getGZR()).append("='").append(beanIn.getSDJC()).append("', ");//时段简称
			}else if(beanIn.getSFYK().equals("2")){
				strbuf.append("        BCRC_DAY").append(beanIn.getGZR()).append("='', ");//时段简称
			}

			strbuf.append("        BCRC_GXR='").append(beanIn.getBCRC_GXR()).append("', ");//修改人
			strbuf.append("        BCRC_GXSJ=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("        BCRC_BCRCID = '").append(beanIn.getBCRC_BCRCID()).append("' ");//班次日程ID
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 *
	 * @FunctionName: updateMuitRCSD
	 * @Description: 修改整月日程设定
	 * @param beanIn
	 * @param dataDay
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月6日 上午10:59:53
	 */
	public int updateMuitRCSD(Pojo3030130 beanIn,Object[] dataDay) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("        BCRC ");
			strbuf.append(" SET ");
			for(int i=1;i<=31;i++){
				if(MyStringUtils.isNotBlank(dataDay[i].toString().replace("null", ""))){
					String strDay = MyStringUtils.leftPad(i+"", 2, "0");//补0
					strbuf.append(" BCRC_DAY"+strDay).append("='").append(dataDay[i].toString()).append("', ");//工作类型
				}else{
					String strDay = MyStringUtils.leftPad(i+"", 2, "0");//补0
					strbuf.append(" BCRC_DAY"+strDay).append("='', ");//工作类型
				}
			}
			strbuf.append(" BCRC_GXR='").append(beanIn.getBCRC_GXR()).append("', ");//修改人
			strbuf.append(" BCRC_GXSJ=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("        BCRC_BCRCID = '").append(beanIn.getBCRC_BCRCID()).append("' ");//班次日程ID

			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 *
	 * @FunctionName: getRCSDListByID
	 * @Description: 根据ID获取日常列表
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return Pojo3030131
	 * @author czl
	 * @date 2017-08-07
	 */
	public Pojo3030131 getRCSDListByID(Pojo3030131 beanIn) throws Exception {
		Pojo3030131 result = null;

		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BCRC_BCRCID, ");//班次日程ID
			strbuf.append("     A.BCRC_DAY01, ");//01日
			strbuf.append("     A.BCRC_DAY02, ");//02日
			strbuf.append("     A.BCRC_DAY03, ");//03日
			strbuf.append("     A.BCRC_DAY04, ");//04日
			strbuf.append("     A.BCRC_DAY05, ");//05日
			strbuf.append("     A.BCRC_DAY06, ");//06日
			strbuf.append("     A.BCRC_DAY07, ");//07日
			strbuf.append("     A.BCRC_DAY08, ");//08日
			strbuf.append("     A.BCRC_DAY09, ");//09日
			strbuf.append("     A.BCRC_DAY10, ");//10日
			strbuf.append("     A.BCRC_DAY11, ");//11日
			strbuf.append("     A.BCRC_DAY12, ");//12日
			strbuf.append("     A.BCRC_DAY13, ");//13日
			strbuf.append("     A.BCRC_DAY14, ");//14日
			strbuf.append("     A.BCRC_DAY15, ");//15日
			strbuf.append("     A.BCRC_DAY16, ");//16日
			strbuf.append("     A.BCRC_DAY17, ");//17日
			strbuf.append("     A.BCRC_DAY18, ");//18日
			strbuf.append("     A.BCRC_DAY19, ");//19日
			strbuf.append("     A.BCRC_DAY20, ");//20日
			strbuf.append("     A.BCRC_DAY21, ");//21日
			strbuf.append("     A.BCRC_DAY22, ");//22日
			strbuf.append("     A.BCRC_DAY23, ");//23日
			strbuf.append("     A.BCRC_DAY24, ");//24日
			strbuf.append("     A.BCRC_DAY25, ");//25日
			strbuf.append("     A.BCRC_DAY26, ");//26日
			strbuf.append("     A.BCRC_DAY27, ");//27日
			strbuf.append("     A.BCRC_DAY28, ");//28日
			strbuf.append("     A.BCRC_DAY29, ");//29日
			strbuf.append("     A.BCRC_DAY30, ");//30日
			strbuf.append("     A.BCRC_DAY31,  ");//31日
			strbuf.append("     F01.SKSD_SDMC AS SDJC01, ");//01日
			strbuf.append("     F02.SKSD_SDMC AS SDJC02, ");//02日
			strbuf.append("     F03.SKSD_SDMC AS SDJC03, ");//03日
			strbuf.append("     F04.SKSD_SDMC AS SDJC04, ");//04日
			strbuf.append("     F05.SKSD_SDMC AS SDJC05, ");//05日
			strbuf.append("     F06.SKSD_SDMC AS SDJC06, ");//06日
			strbuf.append("     F07.SKSD_SDMC AS SDJC07, ");//07日
			strbuf.append("     F08.SKSD_SDMC AS SDJC08, ");//08日
			strbuf.append("     F09.SKSD_SDMC AS SDJC09, ");//09日
			strbuf.append("     F10.SKSD_SDMC AS SDJC10, ");//10日
			strbuf.append("     F11.SKSD_SDMC AS SDJC11, ");//11日
			strbuf.append("     F12.SKSD_SDMC AS SDJC12, ");//12日
			strbuf.append("     F13.SKSD_SDMC AS SDJC13, ");//13日
			strbuf.append("     F14.SKSD_SDMC AS SDJC14, ");//14日
			strbuf.append("     F15.SKSD_SDMC AS SDJC15, ");//15日
			strbuf.append("     F16.SKSD_SDMC AS SDJC16, ");//16日
			strbuf.append("     F17.SKSD_SDMC AS SDJC17, ");//17日
			strbuf.append("     F18.SKSD_SDMC AS SDJC18, ");//18日
			strbuf.append("     F19.SKSD_SDMC AS SDJC19, ");//19日
			strbuf.append("     F20.SKSD_SDMC AS SDJC20, ");//20日
			strbuf.append("     F21.SKSD_SDMC AS SDJC21, ");//21日
			strbuf.append("     F22.SKSD_SDMC AS SDJC22, ");//22日
			strbuf.append("     F23.SKSD_SDMC AS SDJC23, ");//23日
			strbuf.append("     F24.SKSD_SDMC AS SDJC24, ");//24日
			strbuf.append("     F25.SKSD_SDMC AS SDJC25, ");//25日
			strbuf.append("     F26.SKSD_SDMC AS SDJC26, ");//26日
			strbuf.append("     F27.SKSD_SDMC AS SDJC27, ");//27日
			strbuf.append("     F28.SKSD_SDMC AS SDJC28, ");//28日
			strbuf.append("     F29.SKSD_SDMC AS SDJC29, ");//29日
			strbuf.append("     F30.SKSD_SDMC AS SDJC30, ");//30日
			strbuf.append("     F31.SKSD_SDMC AS SDJC31  ");//31日

			strbuf.append("  FROM BCRC A ");

			strbuf.append("  LEFT JOIN SKSD F01 ON A.BCRC_DAY01  =F01.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F02 ON A.BCRC_DAY02  =F02.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F03 ON A.BCRC_DAY03  =F03.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F04 ON A.BCRC_DAY04  =F04.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F05 ON A.BCRC_DAY05  =F05.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F06 ON A.BCRC_DAY06  =F06.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F07 ON A.BCRC_DAY07  =F07.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F08 ON A.BCRC_DAY08  =F08.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F09 ON A.BCRC_DAY09  =F09.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F10 ON A.BCRC_DAY10  =F10.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F11 ON A.BCRC_DAY11  =F11.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F12 ON A.BCRC_DAY12  =F12.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F13 ON A.BCRC_DAY13  =F13.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F14 ON A.BCRC_DAY14  =F14.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F15 ON A.BCRC_DAY15  =F15.SKSD_SDID ");

			strbuf.append("  LEFT JOIN SKSD F16 ON A.BCRC_DAY16  =F16.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F17 ON A.BCRC_DAY17  =F17.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F18 ON A.BCRC_DAY18  =F18.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F19 ON A.BCRC_DAY19  =F19.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F20 ON A.BCRC_DAY20  =F20.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F21 ON A.BCRC_DAY21  =F21.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F22 ON A.BCRC_DAY22  =F22.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F23 ON A.BCRC_DAY23  =F23.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F24 ON A.BCRC_DAY24  =F24.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F25 ON A.BCRC_DAY25  =F25.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F26 ON A.BCRC_DAY26  =F26.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F27 ON A.BCRC_DAY27  =F27.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F28 ON A.BCRC_DAY28  =F28.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F29 ON A.BCRC_DAY29  =F29.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F30 ON A.BCRC_DAY30  =F30.SKSD_SDID ");
			strbuf.append("  LEFT JOIN SKSD F31 ON A.BCRC_DAY31  =F31.SKSD_SDID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append("   AND A.BCRC_BCRCID  = '").append(beanIn.getBCRC_BCRCID()).append("'");
			ResultSetHandler<Pojo3030131> rs = new BeanHandler<Pojo3030131>(
					Pojo3030131.class);
			result = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}