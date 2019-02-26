package com.xsjy.service.service3010000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_3010000.Pojo3010120;

public class Service3010120 extends BaseService {

	private DBManager db = null;

	public Service3010120() {
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
	 * @FunctionName: getNewsList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-01
	 */
	public int getNewsList_TotalCount(Pojo3010120 beanIn,String uuid) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXXX_XXID)");
			sql.append("   FROM XXXX A WHERE A.XXXX_SCBZ = '0'");
			sql.append("   AND A.XXXX_XXID IN (SELECT XXMX_XXID");
			sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID ='").append(uuid).append("')");
			if (MyStringUtils.isNotBlank(beanIn.getBEGIN())) {
				sql.append(" AND STR_TO_DATE(A.XXXX_FBSJ,'%Y-%m-%d') >=").append("STR_TO_DATE('").append(beanIn.getBEGIN()).append("','%Y-%m-%d')");
			}
			if (MyStringUtils.isNotBlank(beanIn.getEND())) {
				sql.append(" AND STR_TO_DATE(A.XXXX_FBSJ,'%Y-%m-%d') <=").append("STR_TO_DATE('").append(beanIn.getEND()).append("','%Y-%m-%d')");
			}
			if (MyStringUtils.isNotBlank(beanIn.getFBR())) {
				sql.append("   AND A.XXXX_FBR IN (SELECT YHXX_UUID");
				sql.append("   FROM YHXX ");
				sql.append("   WHERE YHXX_YHMC LIKE '%").append(beanIn.getFBR()).append("%')");
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
	 * @return List<Pojo3010120>
	 * @author czl
	 * @date 2017-08-01
	 */
	public List<Pojo3010120> getNewsList_PageData(Pojo3010120 beanIn, int page,
			int limit, String sort, String uuid) throws Exception {
		List<Pojo3010120> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XXXX_XXID,");// 消息ID
			sql.append("        A.XXXX_FBZT,");// 发布主题
			sql.append("        B.YHXX_YHMC AS XXXX_FBR,");// 发布人
			sql.append("        A.XXXX_FBNR AS FBNR,");// 发布内容
			sql.append("        A.XXXX_FBNR,");// 发布内容
			sql.append("        A.XXXX_FBSJ,");// 发布时间
			sql.append("        A.XXXX_YXSJ,");// 有效时间
			sql.append("        C.XXMX_MXID AS MXID,");// 消息明细ID
			sql.append("        C.XXMX_CKZT,");// 查看状态
			sql.append("        CASE WHEN C.XXMX_CKZT ='0' THEN '未查看' ELSE '已查看' END AS CKZT");// 查看状态
			sql.append("   FROM XXXX A,YHXX B,XXMX C WHERE A.XXXX_FBR =B.YHXX_UUID AND A.XXXX_XXID = C.XXMX_XXID AND  A.XXXX_SCBZ = '0' ");
			sql.append("   AND A.XXXX_XXID IN (SELECT XXMX_XXID");
			sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID ='").append(uuid).append("')");
			sql.append("   AND C.XXMX_MXID IN(SELECT XXMX_MXID");
			sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID ='").append(uuid).append("')");
			if (MyStringUtils.isNotBlank(beanIn.getBEGIN())) {
				sql.append(" AND STR_TO_DATE(A.XXXX_FBSJ,'%Y-%m-%d') >=").append("STR_TO_DATE('").append(beanIn.getBEGIN()).append("','%Y-%m-%d')");
			}
			if (MyStringUtils.isNotBlank(beanIn.getEND())) {
				sql.append(" AND STR_TO_DATE(A.XXXX_FBSJ,'%Y-%m-%d') <=").append("STR_TO_DATE('").append(beanIn.getEND()).append("','%Y-%m-%d')");
			}
			if (MyStringUtils.isNotBlank(beanIn.getFBR())) {
				sql.append("   AND A.XXXX_FBR IN (SELECT YHXX_UUID");
				sql.append("   FROM YHXX ");
				sql.append("   WHERE YHXX_YHMC LIKE '%").append(beanIn.getFBR()).append("%')");
			}
			sql.append(" ORDER BY ");
			sql.append("        C.XXMX_CKZT ASC,A.XXXX_FBSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3010120>> rs = new BeanListHandler<Pojo3010120>(
					Pojo3010120.class);
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
	 * @FunctionName: updateCKZT
	 * @Description: 更新消息查看状态
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-01
	 */
	public int updateCKZT(String strCJR, String mxid) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XXMX ");
			strbuf.append(" SET ");
			strbuf.append("     XXMX_CKZT='1',");// 查看状态
			strbuf.append("     XXMX_GXR='").append(strCJR).append("',");// 更新人
			strbuf.append("     XXMX_GXSJ='" + sysdate + "'");// 更新时间
			strbuf.append(" WHERE 1 = 1 ");
			strbuf.append("  AND   XXMX_MXID='").append(mxid).append("'");// 消息明细ID
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
