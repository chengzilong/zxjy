package com.xsjy.service.service1090000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1090000.Pojo1090210;

public class Service1090210 extends BaseService {

	private DBManager db;
	
	public Service1090210() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-26
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getAreaList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-26
	 */
	public int getAreaList_TotalCount(Pojo1090210 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XZQY_QYID)");
			sql.append("   FROM XZQY A ");
			sql.append("  WHERE 1 = 1  AND  XZQY_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(beanIn.getXZQY_QYMC())) {	
				sql.append("  AND A.XZQY_QYMC LIKE '%").append(beanIn.getXZQY_QYMC())
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
	 * @FunctionName: getAreaList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1090210>
	 * @author czl
	 * @date 2014-12-26
	 */
	public List<Pojo1090210> getAreaList_PageData(Pojo1090210 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1090210> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XZQY_QYID,");//区域ID
			sql.append("        A.XZQY_QYMC,");// 区域名称
			sql.append("        A.XZQY_QYJB,");// 区域级别
			sql.append("        A.XZQY_QYJB||'级'AS QYJB,");// 区域级别
			sql.append("        A.XZQY_SJID,");// 上级ID
			sql.append("        B.XZQY_QYMC AS SJQY");// 上级ID
			sql.append("   FROM XZQY A LEFT JOIN XZQY B ON A.XZQY_SJID = B.XZQY_QYID");
			sql.append("  WHERE   A.XZQY_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getXZQY_QYMC())) {	
				sql.append("  AND A.XZQY_QYMC LIKE '%").append(beanIn.getXZQY_QYMC())
					.append("%'");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.XZQY_QYID");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1090210>> rs = new BeanListHandler<Pojo1090210>(
					Pojo1090210.class);
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
	 * @FunctionName: checkAreaexist
	 * @Description: 验证区域编码是否存在
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-23
	 */
	public int checkAreaexist(String strQYBM) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XZQY_QYID)");
			sql.append("   FROM XZQY A ");
			sql.append("  WHERE 1 = 1 AND XZQY_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(strQYBM)) {	
				sql.append("  AND A.XZQY_QYID = '").append(strQYBM)
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
	 * @FunctionName: insertArea
	 * @Description: 新增区域
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-26
	 */
	public int insertArea(Pojo1090210 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();		
			strbuf.append(" INSERT INTO ");
			strbuf.append("     XZQY ");
			strbuf.append(" ( ");
			strbuf.append("     XZQY_QYID, ");// 区域ID
			strbuf.append("     XZQY_QYMC, ");//区域名称
			strbuf.append("     XZQY_QYJB, ");// 区域级别
			strbuf.append("     XZQY_SJID, ");// 上级ID
			strbuf.append("     XZQY_CJR, ");// 创建人
			strbuf.append("     XZQY_CJSJ, ");// 创建时间
			strbuf.append("     XZQY_GXR, ");// 更新人
			strbuf.append("     XZQY_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + beanIn.getXZQY_QYID() + "', ");
			strbuf.append("     '" + beanIn.getXZQY_QYMC() + "', ");
			strbuf.append("     '" + beanIn.getXZQY_QYJB() + "', ");
			strbuf.append("     '" + beanIn.getXZQY_SJID() + "', ");
			strbuf.append("     '" + beanIn.getXZQY_CJR() + "', ");
			strbuf.append("     '" + sysdate + "', ");
			strbuf.append("     '" + beanIn.getXZQY_GXR() + "', ");
			strbuf.append("     '" + sysdate + "' ");
			strbuf.append(" ) ");
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: updateArea
	 * @Description: 修改区域
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-26
	 */
	public int updateArea(Pojo1090210 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XZQY ");
			strbuf.append(" SET ");
			strbuf.append("     XZQY_QYID='").append(beanIn.getXZQY_QYID()).append("',");// 区域ID
			strbuf.append("     XZQY_QYMC='").append(beanIn.getXZQY_QYMC()).append("',");// 区域名称
			strbuf.append("     XZQY_QYJB='").append(beanIn.getXZQY_QYJB()).append("',");// 区域级别
			strbuf.append("     XZQY_SJID='").append(beanIn.getXZQY_SJID()).append("',");// 上级ID
			strbuf.append("     XZQY_GXR='").append(beanIn.getXZQY_GXR()).append("',");// 修改人
			strbuf.append("     XZQY_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND XZQY_SCBZ = '0'");
			strbuf.append("  AND   XZQY_QYID='").append(beanIn.getYBM()).append("'");// 区域ID
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: deleteArea
	 * @Description: 删除区域
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-26
	 */
	public int deleteArea(Pojo1090210 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     XZQY ");
			strbuf.append(" WHERE 1 = 1 AND XZQY_QYID='").append(beanIn.getXZQY_QYID())
					.append("'");
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
}