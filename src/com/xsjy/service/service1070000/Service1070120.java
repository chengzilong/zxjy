package com.xsjy.service.service1070000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070120;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070121;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service1070120 extends BaseService {

	private DBManager db = null;

	public Service1070120() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-15
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getIntegralList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-15
	 */
	public int getIntegralList_TotalCount(Pojo1070120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(JJXX_JJID)");
			sql.append("   FROM JJXX A,JSXX B");
			sql.append("  WHERE 1 = 1  AND  JJXX_SCBZ = '0' AND JJXX_RYQF = '0' AND A.JJXX_RYXM = B.JSXX_JSID");
			if (MyStringUtils.isNotBlank(beanIn.getJJXX_RYXM())) {
				sql.append(" AND B.JSXX_JSXM LIKE '%").append(beanIn.getJJXX_RYXM())
					.append("%'");
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
	 * @FunctionName: getIntegralmxList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-15
	 */
	public int getIntegralmxList_TotalCount(Pojo1070121 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(JJMX_JJMXID)");
			sql.append("   FROM JJMX A ");
			sql.append("  WHERE 1 = 1  AND  JJMX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getJJMX_JJID())) {
				sql.append(" AND A.JJMX_JJID = '").append(beanIn.getJJMX_JJID())
					.append("'");
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
	 * @FunctionName: getIntegralList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1070120>
	 * @author czl
	 * @date 2014-12-15
	 */
	public List<Pojo1070120> getIntegralList_PageData(Pojo1070120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1070120> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.JJXX_JJID,");// 积分信息ID
			sql.append("        B.JSXX_JSXM AS JJXX_RYXM,");// 人员姓名
			sql.append("        A.JJXX_JJZF,");// 总积分
			sql.append("        CASE WHEN A.JJXX_RYQF = 0 THEN '教师'  ELSE '学生' END AS JJXX_RYQF");//人员区分
			sql.append("   FROM JJXX A,JSXX B");
			sql.append("  WHERE   A.JJXX_SCBZ = '0' AND JJXX_RYQF = '0' AND A.JJXX_RYXM = B.JSXX_JSID");
			if (MyStringUtils.isNotBlank(beanIn.getJJXX_RYXM())) {
				sql.append(" AND B.JSXX_JSXM LIKE '%").append(beanIn.getJJXX_RYXM())
					.append("%'");
			}
			sql.append(" ORDER BY ");
			sql.append("        A.JJXX_JJZF DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1070120>> rs = new BeanListHandler<Pojo1070120>(
					Pojo1070120.class);
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
	 * @FunctionName: getIntegralmxList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1070121>
	 * @author czl
	 * @date 2014-12-15
	 */
	public List<Pojo1070121> getIntegralmxList_PageData(Pojo1070121 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1070121> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.JJMX_JJFS,");// 积分数
			sql.append("        B.JJLX_JJLX AS JJMX_JJLX,");// 积分类型
			sql.append("        A.JJMX_JJSJ");// 获得时间
			sql.append("   FROM JJMX A,JJLX B");
			sql.append("  WHERE   A.JJMX_SCBZ = '0' AND A.JJMX_JJLX = B.JJLX_JJLXID");
			if (MyStringUtils.isNotBlank(beanIn.getJJMX_JJID())) {
				sql.append(" AND A.JJMX_JJID = '").append(beanIn.getJJMX_JJID())
					.append("'");
			}
			sql.append(" ORDER BY ");
			sql.append("        A.JJMX_JJSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1070121>> rs = new BeanListHandler<Pojo1070121>(
					Pojo1070121.class);
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
