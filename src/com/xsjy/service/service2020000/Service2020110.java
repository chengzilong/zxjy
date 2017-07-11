package com.xsjy.service.service2020000;

import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020110;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service2020110 extends BaseService {

	private DBManager db = null;

	public Service2020110() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getMycourseList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-13
	 */
	public int getMycourseList_TotalCount(Pojo2020110 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(BCXX_BCID)");
			sql.append("   FROM BCXX A WHERE A.BCXX_SCBZ = '0'");
			sql.append("   AND A.BCXX_BCID IN (SELECT BCXS_BCID");
			sql.append("   FROM BCXS  WHERE BCXS_SCBZ = '0'");		
			sql.append("   AND BCXS_XSID = (SELECT XSXX_XSID");
			sql.append("   FROM XSXX  WHERE XSXX_SCBZ = '0' AND XSXX_XSBM ='").append(beanIn.getBCXX_CJR()).append("'))");		
			if (MyStringUtils.isNotBlank(beanIn.getBEGIN())) {
				sql.append(" AND to_date(A.BCXX_KSSJ,'YYYY-MM-DD') >=").append("to_date('").append(beanIn.getBEGIN()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getEND())) {
				sql.append(" AND to_date(A.BCXX_JSSJ,'YYYY-MM-DD') <=").append("to_date('").append(beanIn.getEND()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCMC())) {
				sql.append("   AND A.BCXX_BCMC LIKE '%").append(beanIn.getBCXX_BCMC()).append("%'");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_KCID())&&!("000").equals(beanIn.getBCXX_KCID())) {
				sql.append("   AND A.BCXX_KCID = '").append(beanIn.getBCXX_KCID()).append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCZT())&&!("000").equals(beanIn.getBCXX_BCZT())) {
				sql.append("   AND A.BCXX_BCZT = '").append(beanIn.getBCXX_BCZT()).append("'");
			}
			result = db.queryForInteger(sql);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getMycourseList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo2020110>
	 * @author czl
	 * @date 2015-01-13
	 */
	public List<Pojo2020110> getMycourseList_PageData(Pojo2020110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2020110> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.BCXX_BCID,");//班次ID
			sql.append("   A.BCXX_BCMC,");//班次名称
			sql.append("   A.BCXX_KCID,");//课程ID
			sql.append("   B.KCXX_KCMC AS KCMC,");//课程名称
			sql.append("   A.BCXX_KSSJ,");//开始时间
			sql.append("   A.BCXX_JSSJ,");//结束时间
			sql.append("   A.BCXX_SKDD,");//上课地点
			sql.append("   A.BCXX_BCZT,");//班次状态
			sql.append("   CASE WHEN A.BCXX_BCZT='0' THEN '未开课' WHEN A.BCXX_BCZT='1' THEN '已开课' ELSE '已结束' END AS BCZT,");//班次状态
			sql.append("   A.BCXX_FY");//费用
			sql.append("   FROM BCXX A,KCXX B WHERE A.BCXX_SCBZ = '0' AND A.BCXX_KCID = B.KCXX_KCID");
			sql.append("   AND A.BCXX_BCID IN (SELECT BCXS_BCID");
			sql.append("   FROM BCXS  WHERE BCXS_SCBZ = '0'");		
			sql.append("   AND BCXS_XSID = (SELECT XSXX_XSID");
			sql.append("   FROM XSXX  WHERE XSXX_SCBZ = '0' AND XSXX_XSBM ='").append(beanIn.getBCXX_CJR()).append("'))");		
			if (MyStringUtils.isNotBlank(beanIn.getBEGIN())) {
				sql.append(" AND to_date(A.BCXX_KSSJ,'YYYY-MM-DD') >=").append("to_date('").append(beanIn.getBEGIN()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getEND())) {
				sql.append(" AND to_date(A.BCXX_JSSJ,'YYYY-MM-DD') <=").append("to_date('").append(beanIn.getEND()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCMC())) {
				sql.append("   AND A.BCXX_BCMC LIKE '%").append(beanIn.getBCXX_BCMC()).append("%'");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_KCID())&&!("000").equals(beanIn.getBCXX_KCID())) {
				sql.append("   AND A.BCXX_KCID = '").append(beanIn.getBCXX_KCID()).append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCZT())&&!("000").equals(beanIn.getBCXX_BCZT())) {
				sql.append("   AND A.BCXX_BCZT = '").append(beanIn.getBCXX_BCZT()).append("'");
			}
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2020110>> rs = new BeanListHandler<Pojo2020110>(
					Pojo2020110.class);
			result = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
}
