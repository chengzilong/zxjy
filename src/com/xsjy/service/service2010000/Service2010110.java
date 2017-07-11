package com.xsjy.service.service2010000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010110;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service2010110 extends BaseService {

	private DBManager db = null;

	public Service2010110() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-01-12
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
	 * @date 2015-01-12
	 */
	public int getNewsList_TotalCount(Pojo2010110 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXXX_XXID)");
			sql.append("   FROM XXXX A WHERE A.XXXX_SCBZ = '0'");
			sql.append("   AND A.XXXX_XXID IN (SELECT XXMX_XXID");
			sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID ='").append(beanIn.getXXXX_CJR()).append("')");		
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
	 * @return List<Pojo2010110>
	 * @author czl
	 * @date 2015-01-12
	 */
	public List<Pojo2010110> getNewsList_PageData(Pojo2010110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2010110> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XXXX_XXID,");// 消息ID
			sql.append("        A.XXXX_FBZT,");// 发布主题
			sql.append("        B.YHXX_YHMC AS XXXX_FBR,");// 发布人
			sql.append("        CASE WHEN length(A.XXXX_FBNR)>25 THEN SUBSTR(A.XXXX_FBNR,0,25)||'...' ELSE A.XXXX_FBNR END AS FBNR,");// 发布内容缩写
			sql.append("        A.XXXX_FBNR,");// 发布内容
			sql.append("        A.XXXX_FBSJ,");// 发布时间
			sql.append("        A.XXXX_YXSJ,");// 有效时间
			sql.append("        C.XXMX_MXID AS MXID,");// 消息明细ID
			sql.append("        C.XXMX_CKZT,");// 查看状态
			sql.append("        CASE WHEN C.XXMX_CKZT ='0' THEN '未查看' ELSE '已查看' END AS CKZT");// 查看状态
			sql.append("   FROM XXXX A,YHXX B,XXMX C WHERE A.XXXX_FBR =B.YHXX_UUID AND A.XXXX_XXID = C.XXMX_XXID AND  A.XXXX_SCBZ = '0' ");
			sql.append("   AND A.XXXX_XXID IN (SELECT XXMX_XXID");
			sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID ='").append(beanIn.getXXXX_CJR()).append("')");		
			sql.append("   AND C.XXMX_MXID IN(SELECT XXMX_MXID");
			sql.append("   FROM XXMX  WHERE XXMX_SCBZ = '0' AND XXMX_JSRID ='").append(beanIn.getXXXX_CJR()).append("')");		
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
			sql.append(" ORDER BY ");
			sql.append("        C.XXMX_CKZT ASC,A.XXXX_FBSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010110>> rs = new BeanListHandler<Pojo2010110>(
					Pojo2010110.class);
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
	 * @date 2015-01-12
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
