package com.xsjy.service.service9010000;

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
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010150;

public class Service9010150 extends BaseService {

	private DBManager db = null;

	public Service9010150() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-10-28
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now );
		return date;
	}
	/**
	 * @FunctionName: getSiteCheck_TotalCount
	 * @Description: 验证站点重复信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int getSiteCheck_TotalCount(Pojo9010150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(PXWD_ZDID)");
			sql.append("   FROM PXWD A ");
			sql.append("  WHERE 1 = 1");
			if (MyStringUtils.isNotBlank(beanIn.getPXWD_ZDID())) {
				sql.append("  AND A.PXWD_ZDID = '").append(beanIn.getPXWD_ZDID())
					.append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getPXWD_ZDMC())) {
				sql.append("  AND A.PXWD_ZDMC = '").append(beanIn.getPXWD_ZDMC())
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
	 * @FunctionName: getSiteList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int getSiteList_TotalCount(Pojo9010150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(PXWD_ZDID)");
			sql.append("   FROM PXWD A ");
			sql.append("  WHERE 1 = 1");
			if (MyStringUtils.isNotBlank(beanIn.getPXWD_ZDMC())) {
				sql.append("  AND A.PXWD_ZDMC like '%").append(beanIn.getPXWD_ZDMC())
					.append("%'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getPXWD_LXR())) {
				sql.append(" AND A.PXWD_LXR LIKE '%").append(beanIn.getPXWD_LXR())
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
	 * @FunctionName: getSiteList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo9010150>
	 * @author czl
	 * @date 2017-07-27
	 */
	public List<Pojo9010150> getSiteList_PageData(Pojo9010150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010150> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.PXWD_ZDID,");// 站点ID
			sql.append("        A.PXWD_ZDMC,");// 站点名称
			sql.append("        A.PXWD_ZDDZ, ");// 站点地址
			sql.append("        A.PXWD_LXR, ");// 联系人
			sql.append("        A.PXWD_LXDH, ");// 联系电话
			sql.append("        A.PXWD_BZXX ");// 备注信息
			sql.append("   FROM PXWD A");
			sql.append("  WHERE 1 = 1 ");
			if (MyStringUtils.isNotBlank(beanIn.getPXWD_ZDMC())) {
				sql.append("  AND A.PXWD_ZDMC like '%").append(beanIn.getPXWD_ZDMC())
					.append("%'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getPXWD_LXR())) {
				sql.append("  AND A.PXWD_LXR like '%").append(beanIn.getPXWD_LXR())
					.append("%'");
			}
			sql.append(" ORDER BY ");
			sql.append("         A.PXWD_ZDID");

			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo9010150>> rs = new BeanListHandler<Pojo9010150>(
					Pojo9010150.class);
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
	 * @FunctionName: insertSite
	 * @Description: 新增站点
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int insertSite(Pojo9010150 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			String zdid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID
			strbuf.append(" INSERT INTO ");
			strbuf.append("     PXWD ");
			strbuf.append(" ( ");
			strbuf.append("     PXWD_ZDID, ");// 站点ID
			strbuf.append("     PXWD_ZDMC, ");// 站点名称
			strbuf.append("     PXWD_ZDDZ, ");// 站点地址
			strbuf.append("     PXWD_LXR, ");// 联系人
			strbuf.append("     PXWD_LXDH, ");// 联系电话
			strbuf.append("     PXWD_BZXX, ");// 备注
			strbuf.append("     PXWD_CJR, ");// 创建人
			strbuf.append("     PXWD_CJSJ, ");// 创建时间
			strbuf.append("     PXWD_GXR, ");// 修改人
			strbuf.append("     PXWD_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + zdid + "', ");
			strbuf.append("     '" + beanIn.getPXWD_ZDMC() + "', ");
			strbuf.append("     '" + beanIn.getPXWD_ZDDZ() + "', ");
			strbuf.append("     '" + beanIn.getPXWD_LXR() + "', ");
			strbuf.append("     '" + beanIn.getPXWD_LXDH() + "', ");
			strbuf.append("     '" + beanIn.getPXWD_BZXX() + "', ");
			strbuf.append("     '" + beanIn.getPXWD_CJR() + "', ");
			strbuf.append("     NOW(), ");
			strbuf.append("     '" + beanIn.getPXWD_GXR() + "', ");
			strbuf.append("     NOW() ");
			strbuf.append(" ) ");
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
	 * @FunctionName: updateSiteRebate
	 * @Description: 修改站点返利信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int updateSite(Pojo9010150 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     PXWD ");
			strbuf.append(" SET ");
			strbuf.append("     PXWD_ZDMC='").append(beanIn.getPXWD_ZDMC()).append("',");// 站点名称
			strbuf.append("     PXWD_ZDDZ='").append(beanIn.getPXWD_ZDDZ()).append("',");// 站点地址
			strbuf.append("     PXWD_LXR='").append(beanIn.getPXWD_LXR()).append("',");// 联系人
			strbuf.append("     PXWD_LXDH='").append(beanIn.getPXWD_LXDH()).append("',");// 联系电话
			strbuf.append("     PXWD_BZXX='").append(beanIn.getPXWD_BZXX()).append("',");// 备注
			strbuf.append("     PXWD_GXR='").append(beanIn.getPXWD_GXR()).append("',");// 修改人
			strbuf.append("     PXWD_GXSJ=NOW()");// 修改时间
			strbuf.append(" WHERE 1 = 1");
			strbuf.append("  AND   PXWD_ZDID='").append(beanIn.getPXWD_ZDID()).append("'");// 站点ID
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
	 * @FunctionName: deleteSite
	 * @Description: 删除站点
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int deleteSite(Pojo9010150 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     PXWD ");
			strbuf.append(" WHERE 1 = 1 AND PXWD_ZDID='").append(beanIn.getPXWD_ZDID())
					.append("'");
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}
