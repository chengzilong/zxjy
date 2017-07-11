package com.xsjy.service.service2020000;

import java.util.ArrayList;
import java.util.List;



import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_BCXX;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020120;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020121;

public class Service2020120 extends BaseService {
	private DBManager db = null;

	public Service2020120() {
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
	 * @date 2015-01-13
	 */
	public int getRCSDList_TotalCount(Pojo2020120 beanIn) throws Exception {
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
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-13
	 */
	public <T> List<Pojo2020120> getRCSDList_PageData(Pojo2020120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2020120> result = null;
		
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
				
				List<Pojo2020120> PageData = new ArrayList<Pojo2020120>();
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
					strbuf.append(" SELECT A.*,TO_DATE(A.BCRC_NY, 'YYYY-MM') AS NY FROM(SELECT ");
					strbuf.append("     B.BCRC_BCRCID, ");//班次日程ID
					strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
					strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
					strbuf.append("     D.KCFY_SDID AS SDID, ");//时段ID
					strbuf.append("     E.SKSD_SDMC AS SDMC, ");//时段名称
					strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
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
					strbuf.append("  FROM BCXX A,KCXX C,BCRC B,KCFY D,SKSD E");
					strbuf.append("      ,SKSD F01,SKSD F02,SKSD F03,SKSD F04,SKSD F05,SKSD F06,SKSD F07 ");
					strbuf.append("      ,SKSD F08,SKSD F09,SKSD F10,SKSD F11,SKSD F12,SKSD F13,SKSD F14 ");
					strbuf.append("      ,SKSD F15,SKSD F16,SKSD F17,SKSD F18,SKSD F19,SKSD F20,SKSD F21 ");
					strbuf.append("      ,SKSD F22,SKSD F23,SKSD F24,SKSD F25,SKSD F26,SKSD F27,SKSD F28 ");
					strbuf.append("      ,SKSD F29,SKSD F30,SKSD F31");
					strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
					strbuf.append("   AND A.BCXX_BCID = B.BCRC_BCID(+)");
					strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
					strbuf.append("   AND D.KCFY_SDID = E.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY01 = F01.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY02 = F02.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY03 = F03.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY04 = F04.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY05 = F05.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY06 = F06.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY07 = F07.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY08 = F08.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY09 = F09.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY10 = F10.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY11 = F11.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY12 = F12.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY13 = F13.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY14 = F14.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY15 = F15.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY16 = F16.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY17 = F17.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY18 = F18.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY19 = F19.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY20 = F20.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY21 = F21.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY22 = F22.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY23 = F23.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY24 = F24.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY25 = F25.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY26 = F26.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY27 = F27.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY28 = F28.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY29 = F29.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY30 = F30.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY31 = F31.SKSD_SDID(+)");	
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
						strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
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
						strbuf.append("  FROM BCXX A,KCFY D,KCXX C,SKSD E,(SELECT * FROM BCRC WHERE BCRC_NY='"+between[i+1]+"') B");
						strbuf.append("      ,SKSD F01,SKSD F02,SKSD F03,SKSD F04,SKSD F05,SKSD F06,SKSD F07 ");
						strbuf.append("      ,SKSD F08,SKSD F09,SKSD F10,SKSD F11,SKSD F12,SKSD F13,SKSD F14 ");
						strbuf.append("      ,SKSD F15,SKSD F16,SKSD F17,SKSD F18,SKSD F19,SKSD F20,SKSD F21 ");
						strbuf.append("      ,SKSD F22,SKSD F23,SKSD F24,SKSD F25,SKSD F26,SKSD F27,SKSD F28 ");
						strbuf.append("      ,SKSD F29,SKSD F30,SKSD F31");
						strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
						strbuf.append("   AND A.BCXX_BCID = B.BCRC_BCID(+)");
						strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
						strbuf.append("   AND D.KCFY_SDID = E.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY01 = F01.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY02 = F02.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY03 = F03.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY04 = F04.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY05 = F05.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY06 = F06.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY07 = F07.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY08 = F08.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY09 = F09.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY10 = F10.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY11 = F11.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY12 = F12.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY13 = F13.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY14 = F14.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY15 = F15.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY16 = F16.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY17 = F17.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY18 = F18.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY19 = F19.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY20 = F20.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY21 = F21.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY22 = F22.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY23 = F23.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY24 = F24.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY25 = F25.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY26 = F26.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY27 = F27.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY28 = F28.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY29 = F29.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY30 = F30.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY31 = F31.SKSD_SDID(+)");	
						if (MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())) {	
							strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getBCRC_BCID())
								.append("'");	
						}
						
				    }
				    strbuf.append("   ) A ORDER BY NY");	
				}else{
					strbuf.append(" SELECT A.*,TO_DATE(A.BCRC_NY, 'YYYY-MM') AS NY FROM(SELECT ");
					strbuf.append("     B.BCRC_BCRCID, ");//班次日程ID
					strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
					strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
					strbuf.append("     D.KCFY_SDID AS SDID, ");//时段ID
					strbuf.append("     E.SKSD_SDMC AS SDMC, ");//时段名称
					strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
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
					strbuf.append("  FROM BCXX A,KCXX C,BCRC B,KCFY D,SKSD E");
					strbuf.append("      ,SKSD F01,SKSD F02,SKSD F03,SKSD F04,SKSD F05,SKSD F06,SKSD F07 ");
					strbuf.append("      ,SKSD F08,SKSD F09,SKSD F10,SKSD F11,SKSD F12,SKSD F13,SKSD F14 ");
					strbuf.append("      ,SKSD F15,SKSD F16,SKSD F17,SKSD F18,SKSD F19,SKSD F20,SKSD F21 ");
					strbuf.append("      ,SKSD F22,SKSD F23,SKSD F24,SKSD F25,SKSD F26,SKSD F27,SKSD F28 ");
					strbuf.append("      ,SKSD F29,SKSD F30,SKSD F31");
					strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
					strbuf.append("   AND A.BCXX_BCID = B.BCRC_BCID(+)");
					strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
					strbuf.append("   AND D.KCFY_SDID = E.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY01 = F01.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY02 = F02.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY03 = F03.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY04 = F04.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY05 = F05.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY06 = F06.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY07 = F07.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY08 = F08.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY09 = F09.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY10 = F10.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY11 = F11.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY12 = F12.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY13 = F13.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY14 = F14.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY15 = F15.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY16 = F16.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY17 = F17.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY18 = F18.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY19 = F19.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY20 = F20.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY21 = F21.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY22 = F22.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY23 = F23.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY24 = F24.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY25 = F25.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY26 = F26.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY27 = F27.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY28 = F28.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY29 = F29.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY30 = F30.SKSD_SDID(+)");
					strbuf.append("   AND B.BCRC_DAY31 = F31.SKSD_SDID(+)");	
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
						strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
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
						strbuf.append("  FROM BCXX A,KCFY D,KCXX C,SKSD E,(SELECT * FROM BCRC WHERE BCRC_NY='"+newbetween[i]+"') B");
						strbuf.append("      ,SKSD F01,SKSD F02,SKSD F03,SKSD F04,SKSD F05,SKSD F06,SKSD F07 ");
						strbuf.append("      ,SKSD F08,SKSD F09,SKSD F10,SKSD F11,SKSD F12,SKSD F13,SKSD F14 ");
						strbuf.append("      ,SKSD F15,SKSD F16,SKSD F17,SKSD F18,SKSD F19,SKSD F20,SKSD F21 ");
						strbuf.append("      ,SKSD F22,SKSD F23,SKSD F24,SKSD F25,SKSD F26,SKSD F27,SKSD F28 ");
						strbuf.append("      ,SKSD F29,SKSD F30,SKSD F31");
						strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
						strbuf.append("   AND A.BCXX_BCID = B.BCRC_BCID(+)");
						strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
						strbuf.append("   AND D.KCFY_SDID = E.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY01 = F01.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY02 = F02.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY03 = F03.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY04 = F04.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY05 = F05.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY06 = F06.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY07 = F07.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY08 = F08.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY09 = F09.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY10 = F10.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY11 = F11.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY12 = F12.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY13 = F13.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY14 = F14.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY15 = F15.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY16 = F16.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY17 = F17.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY18 = F18.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY19 = F19.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY20 = F20.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY21 = F21.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY22 = F22.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY23 = F23.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY24 = F24.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY25 = F25.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY26 = F26.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY27 = F27.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY28 = F28.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY29 = F29.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY30 = F30.SKSD_SDID(+)");
						strbuf.append("   AND B.BCRC_DAY31 = F31.SKSD_SDID(+)");	
						if (MyStringUtils.isNotBlank(beanIn.getBCRC_BCID())) {	
							strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getBCRC_BCID())
								.append("'");	
						}
						
				    }
				    strbuf.append("   ) A ORDER BY NY");	
				}
				StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
						sort);
				ResultSetHandler<List<Pojo2020120>> rs = new BeanListHandler<Pojo2020120>(
						Pojo2020120.class);
				
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
	//查询年月
	public List<Pojo2020120> getNYList(String bcid) throws Exception {
		List<Pojo2020120> result = null;
		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         BCRC_NY");
			strbuf.append(" FROM ");
			strbuf.append("         BCRC ");
			strbuf.append(" WHERE ");
			strbuf.append("         BCRC_BCID = '"+bcid+"' ");
			ResultSetHandler<List<Pojo2020120>> rs = new BeanListHandler<Pojo2020120>(
					Pojo2020120.class);			
			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
		}
		return result;
	}
	//查询班次日期跨度
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
	//查询两个日期中间的月份
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
	public Pojo2020121 getRCSDListByID(Pojo2020121 beanIn) throws Exception {
		Pojo2020121 result = null;
		
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
			strbuf.append("      ,SKSD F01,SKSD F02,SKSD F03,SKSD F04,SKSD F05,SKSD F06,SKSD F07 ");
			strbuf.append("      ,SKSD F08,SKSD F09,SKSD F10,SKSD F11,SKSD F12,SKSD F13,SKSD F14 ");
			strbuf.append("      ,SKSD F15,SKSD F16,SKSD F17,SKSD F18,SKSD F19,SKSD F20,SKSD F21 ");
			strbuf.append("      ,SKSD F22,SKSD F23,SKSD F24,SKSD F25,SKSD F26,SKSD F27,SKSD F28 ");
			strbuf.append("      ,SKSD F29,SKSD F30,SKSD F31");
			strbuf.append(" WHERE 1=1 ");
			
			strbuf.append("   AND A.BCRC_DAY01  =F01.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY02  =F02.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY03  =F03.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY04  =F04.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY05  =F05.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY06  =F06.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY07  =F07.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY08  =F08.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY09  =F09.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY10  =F10.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY11  =F11.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY12  =F12.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY13  =F13.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY14  =F14.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY15  =F15.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY16  =F16.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY17  =F17.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY18  =F18.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY19  =F19.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY20  =F20.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY21  =F21.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY22  =F22.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY23  =F23.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY24  =F24.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY25  =F25.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY26  =F26.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY27  =F27.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY28  =F28.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY29  =F29.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY30  =F30.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY31  =F31.SKSD_SDID(+)");
			
			strbuf.append("   AND A.BCRC_BCRCID  = '").append(beanIn.getBCRC_BCRCID()).append("'");
			ResultSetHandler<Pojo2020121> rs = new BeanHandler<Pojo2020121>(
					Pojo2020121.class);
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