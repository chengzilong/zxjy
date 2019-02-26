package com.xsjy.service.service1110000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110140;

public class Service1110140 extends BaseService {

	private DBManager db = null;

	public Service1110140() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-01-05
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now );
		return date;
	}
	/**
	 * @FunctionName: getAccountList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-28
	 */
	public int getAccountList_TotalCount(Pojo1110140 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(GRZH_ZHID)");
			sql.append("   FROM GRZH A ");
			sql.append("  WHERE 1 = 1  AND  GRZH_SCBZ = '0' ");
			sql.append("  AND A.GRZH_YHID = '").append(beanIn.getGRZH_CJR())
					.append("'");
			if (MyStringUtils.isNotBlank(beanIn.getGRZH_YHMC())) {
				sql.append("  AND A.GRZH_YHMC LIKE '%").append(beanIn.getGRZH_YHMC())
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
	 * @FunctionName: getAccountList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1110140>
	 * @author czl
	 * @date 2017-07-28
	 */
	public List<Pojo1110140> getAccountList_PageData(Pojo1110140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1110140> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.GRZH_ZHID,");//账户ID
			sql.append("        A.GRZH_YHMC,");// 银行名称
			sql.append("        A.GRZH_YHKH,");// 银行卡号
			sql.append("        LEFT(A.GRZH_CJSJ,10) AS GRZH_CJSJ");// 创建时间
			sql.append("   FROM GRZH A");
			sql.append("  WHERE   A.GRZH_SCBZ = '0'");
			sql.append("  AND A.GRZH_YHID = '").append(beanIn.getGRZH_CJR()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getGRZH_YHMC())) {
				sql.append("  AND A.GRZH_YHMC LIKE '%").append(beanIn.getGRZH_YHMC())
					.append("%'");
			}
			sql.append(" ORDER BY ");
			sql.append("        A.GRZH_ZHID");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1110140>> rs = new BeanListHandler<Pojo1110140>(
					Pojo1110140.class);
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
	 * @FunctionName: insertAccount
	 * @Description: 新增账户
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-28
	 */
	public int insertAccount(Pojo1110140 beanIn,String jsid,String uuid) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			String zhid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     GRZH ");
			strbuf.append(" ( ");
			strbuf.append("     GRZH_ZHID, ");// 账户ID
			strbuf.append("     GRZH_YHID, ");// 用户ID
			strbuf.append("     GRZH_YHMC, ");// 银行名称
			strbuf.append("     GRZH_YHKH, ");// 银行卡号
			strbuf.append("     GRZH_ZHQF, ");// 账户区分
			strbuf.append("     GRZH_CJR, ");// 创建人
			strbuf.append("     GRZH_CJSJ, ");// 创建时间
			strbuf.append("     GRZH_GXR, ");// 更新人
			strbuf.append("     GRZH_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + zhid + "', ");
			strbuf.append("     '" + uuid + "', ");
			strbuf.append("     '" + beanIn.getGRZH_YHMC() + "', ");
			strbuf.append("     '" + beanIn.getGRZH_YHKH() + "', ");
			if(jsid.equals("106")){
				strbuf.append("     '2', ");
			}else if(jsid.equals("104")||jsid.equals("105")){
				strbuf.append("     '1', ");
			}else{
				strbuf.append("     '3', ");
			}
			strbuf.append("     '" + beanIn.getGRZH_CJR() + "', ");
			strbuf.append("     NOW(), ");
			strbuf.append("     '" + beanIn.getGRZH_GXR() + "', ");
			strbuf.append("     NOW() ");
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
	 * @FunctionName: updateAccount
	 * @Description: 修改账户
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-28
	 */
	public int updateAccount(Pojo1110140 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     GRZH ");
			strbuf.append(" SET ");
			strbuf.append("     GRZH_YHMC='").append(beanIn.getGRZH_YHMC()).append("',");// 银行名称
			strbuf.append("     GRZH_YHKH='").append(beanIn.getGRZH_YHKH()).append("',");// 银行卡号
			strbuf.append("     GRZH_GXR='").append(beanIn.getGRZH_GXR()).append("',");// 修改人
			strbuf.append("     GRZH_GXSJ=NOW()");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND GRZH_SCBZ = '0'");
			strbuf.append("  AND   GRZH_ZHID='").append(beanIn.getGRZH_ZHID()).append("'");//账户ID
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
	 * @FunctionName: deleteAccount
	 * @Description: 删除账户
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-28
	 */
	public int deleteAccount(Pojo1110140 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     GRZH ");
			strbuf.append(" WHERE 1 = 1 AND GRZH_ZHID='").append(beanIn.getGRZH_ZHID())
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
