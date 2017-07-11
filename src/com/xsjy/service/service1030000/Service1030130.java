package com.xsjy.service.service1030000;

import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030130;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service1030130 extends BaseService {

	private DBManager db = null;

	public Service1030130() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getNewsList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-18
	 */
	public int getNewsList_TotalCount(Pojo1030130 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXXX_XXID)");
			sql.append("   FROM XXXX A WHERE A.XXXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getBEGIN())) {
				sql.append(" AND to_date(A.XXXX_FBSJ,'YYYY-MM-DD') >=").append("to_date('").append(beanIn.getBEGIN()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getEND())) {
				sql.append(" AND to_date(A.XXXX_FBSJ,'YYYY-MM-DD') <=").append("to_date('").append(beanIn.getEND()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getFBR())) {
				sql.append("   AND A.XXXX_FBR IN (SELECT YHXX_UUID");
				sql.append("   FROM YHXX ");
				sql.append("   WHERE YHXX_YHMC LIKE '%").append(beanIn.getFBR()).append("%')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getJSR())) {
				sql.append(" AND A.XXXX_XXID IN (SELECT XXMX_XXID");
				sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID IN");
				sql.append("   (SELECT YHXX_UUID FROM YHXX ");
				sql.append("   WHERE YHXX_YHMC LIKE '%").append(beanIn.getJSR()).append("%'))");	
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
	 * @FunctionName: getNewsList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1030130>
	 * @author czl
	 * @date 2014-12-18
	 */
	public List<Pojo1030130> getNewsList_PageData(Pojo1030130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030130> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XXXX_XXID,");// 消息ID
			sql.append("        A.XXXX_FBZT,");// 发布主题
			sql.append("        B.YHXX_YHMC AS XXXX_FBR,");// 发布人
			sql.append("        CASE WHEN length(A.XXXX_FBNR)>25 THEN SUBSTR(A.XXXX_FBNR,0,25)||'...' ELSE A.XXXX_FBNR END AS FBNR,");// 发布内容缩写
			sql.append("        A.XXXX_FBNR,");// 发布内容
			sql.append("        A.XXXX_FBSJ,");// 发布时间
			sql.append("        A.XXXX_YXSJ");// 有效时间
			sql.append("   FROM XXXX A,YHXX B WHERE A.XXXX_FBR =B.YHXX_UUID AND  A.XXXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getBEGIN())) {
				sql.append(" AND to_date(A.XXXX_FBSJ,'YYYY-MM-DD') >=").append("to_date('").append(beanIn.getBEGIN()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getEND())) {
				sql.append(" AND to_date(A.XXXX_FBSJ,'YYYY-MM-DD') <=").append("to_date('").append(beanIn.getEND()).append("','YYYY-MM-DD')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getFBR())) {
				sql.append("   AND A.XXXX_FBR IN (SELECT YHXX_UUID");
				sql.append("   FROM YHXX ");
				sql.append("   WHERE YHXX_YHMC LIKE '%").append(beanIn.getFBR()).append("%')");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getJSR())) {
				sql.append(" AND A.XXXX_XXID IN (SELECT XXMX_XXID");
				sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID IN");
				sql.append("   (SELECT YHXX_UUID FROM YHXX ");
				sql.append("   WHERE YHXX_YHMC LIKE '%").append(beanIn.getJSR()).append("%'))");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.XXXX_FBSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1030130>> rs = new BeanListHandler<Pojo1030130>(
					Pojo1030130.class);
			result = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: getRenyuanList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-18
	 */
	public int getRenyuanList_TotalCount(Pojo1030130 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXMX_MXID)");
			sql.append("  FROM XXMX");
			sql.append("  WHERE XXMX_SCBZ = '0'");
			if(MyStringUtils.isNotBlank(beanIn.getXXID())) {
				sql.append(" AND XXMX_XXID = '").append(beanIn.getXXID()).append("'");	
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
	 * @FunctionName: getRenyuanList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1030130>
	 * @author czl
	 * @date 2014-12-18
	 */
	public List<Pojo1030130> getRenyuanList_PageData(Pojo1030130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030130> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT XXMX_JSR AS XM,");// 接收人
			sql.append("        XXMX_LXFS AS LXFS");// 联系方式
			sql.append("         FROM XXMX");
			sql.append("         WHERE XXMX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getXXID())) {
				sql.append(" AND XXMX_XXID = '").append(beanIn.getXXID()).append("'");	
			}
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1030130>> rs = new BeanListHandler<Pojo1030130>(
					Pojo1030130.class);
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
