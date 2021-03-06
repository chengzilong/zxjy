package com.xsjy.service.service2010000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010160;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010161;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service2010160 extends BaseService {

	private DBManager db = null;

	public Service2010160() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-01-14
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	public String getDate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getQuestionList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-01-14
	 */
	public int getQuestionList_TotalCount(Pojo2010160 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(WTXX_WTID)");
			sql.append("   FROM WTXX A ");
			sql.append("  WHERE  A.WTXX_SCBZ = '0' AND A.WTXX_TWZT = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getWTXX_KCID())&&!("000").equals(beanIn.getWTXX_KCID())) {	
				sql.append("  AND A.WTXX_KCID = '").append(beanIn.getWTXX_KCID())
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
	 * @FunctionName: getQuestionList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo2010160>
	 * @author czl
	 * @date 2014-01-14
	 */
	public List<Pojo2010160> getQuestionList_PageData(Pojo2010160 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2010160> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.WTXX_WTID,");//问题ID
			sql.append("        A.WTXX_KCID,");//课程ID
			sql.append("        A.WTXX_YXSJ,");//有效时间
			sql.append("        A.WTXX_TWJF,");//提问积分
			sql.append("        B.KCXX_KCMC AS KCMC,");// 课程名称
			sql.append("        D.YHXX_YHMC AS TWR,");// 提问人
			sql.append("        A.WTXX_TWNR,");// 提问内容
			sql.append("        A.WTXX_TWSJ,");// 提问时间
			sql.append("        C.YXSJ_YXSJ AS YXSJ,");// 有效时间
			sql.append("        (SELECT COUNT(E.HDXX_HDID)  FROM HDXX E    WHERE A.WTXX_WTID  = E.HDXX_WTID(+) AND E.HDXX_HDR ='").append(beanIn.getWTXX_CJR()).append("') AS HDSL,");//回答数量
			sql.append("        CASE WHEN (SELECT COUNT(E.HDXX_HDID)  FROM HDXX E    WHERE A.WTXX_WTID  = E.HDXX_WTID(+) AND E.HDXX_HDR='"+beanIn.getWTXX_CJR()+"' ) = '0' THEN '未回答' ELSE '已回答' END AS WTXX_TWZT");
			sql.append("   FROM WTXX A,KCXX B,YXSJ C,YHXX D");
			sql.append("  WHERE   A.WTXX_SCBZ = '0' AND A.WTXX_TWZT = '0'");
			sql.append("  AND A.WTXX_KCID = B.KCXX_KCID(+)");
			sql.append("  AND A.WTXX_YXSJ = C.YXSJ_YXID(+)");
			sql.append("  AND A.WTXX_TWR = D.YHXX_UUID(+)");
			if (MyStringUtils.isNotBlank(beanIn.getWTXX_KCID())&&!("000").equals(beanIn.getWTXX_KCID())) {	
				sql.append("  AND A.WTXX_KCID = '").append(beanIn.getWTXX_KCID())
					.append("'");	
			}	
			sql.append(" ORDER BY ");
			sql.append("        A.WTXX_TWSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010160>> rs = new BeanListHandler<Pojo2010160>(
					Pojo2010160.class);
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
	 * @FunctionName: insertAnswer
	 * @Description: 新增回答
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-14
	 */
	public int insertAnswer(Pojo2010161 beanIn,String uuid) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		String date = getDate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();		
			String hdid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     HDXX ");
			strbuf.append(" ( ");
			strbuf.append("     HDXX_HDID, ");// 回答ID
			strbuf.append("     HDXX_WTID, ");// 问题ID
			strbuf.append("     HDXX_HDR, ");// 回答人
			strbuf.append("     HDXX_HDNR, ");// 回答内容
			strbuf.append("     HDXX_HDSJ, ");// 回答时间
			strbuf.append("     HDXX_CJR, ");// 创建人
			strbuf.append("     HDXX_CJSJ, ");// 创建时间
			strbuf.append("     HDXX_GXR, ");// 更新人
			strbuf.append("     HDXX_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + hdid + "', ");
			strbuf.append("     '" + beanIn.getHDXX_WTID() + "', ");
			strbuf.append("     '" + uuid + "', ");
			strbuf.append("     '" + beanIn.getHDXX_HDNR() + "', ");
			strbuf.append("     '" + date + "', ");
			strbuf.append("     '" + beanIn.getHDXX_CJR() + "', ");
			strbuf.append("     '" + sysdate + "', ");
			strbuf.append("     '" + beanIn.getHDXX_GXR() + "', ");
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
	 * @FunctionName: updateAnswer
	 * @Description: 修改答案
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-14
	 */
	public int updateAnswer(Pojo2010161 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     HDXX ");
			strbuf.append(" SET ");
			strbuf.append("     HDXX_HDNR='").append(beanIn.getHDXX_HDNR()).append("',");// 回答内容
			strbuf.append("     HDXX_GXR='").append(beanIn.getHDXX_GXR()).append("',");// 更新人
			strbuf.append("     HDXX_GXSJ='" + sysdate + "'");// 更新时间
			strbuf.append(" WHERE 1 = 1 AND HDXX_SCBZ = '0'");
			strbuf.append("  AND   HDXX_HDID='").append(beanIn.getHDXX_HDID()).append("'");//问题ID
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
	 * @FunctionName: deleteAnswer
	 * @Description: 删除答案
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2016-01-14
	 */
	public int deleteAnswer(Pojo2010161 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     HDXX ");
			strbuf.append(" WHERE 1 = 1 AND HDXX_HDID='").append(beanIn.getHDXX_HDID())
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
	/**
	 * @FunctionName: getAnswerList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-09
	 */
	public int getAnswerList_TotalCount(String wtid,String yhid) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(HDXX_HDID)");
			sql.append("   FROM HDXX A ");
			sql.append("  WHERE  A.HDXX_SCBZ = '0' ");
			sql.append("  AND A.HDXX_WTID = '").append(wtid).append("'");
			sql.append("  AND A.HDXX_HDR = '").append(yhid).append("'");
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
	 * @FunctionName: getAnswerList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo2010161>
	 * @author czl
	 * @date 2015-01-14
	 */
	public List<Pojo2010161> getAnswerList_PageData(String wtid, String uuid, int page,
			int limit, String sort) throws Exception {
		List<Pojo2010161> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.HDXX_HDID,");//回答ID
			sql.append("        A.HDXX_HDNR,");//回答内容
			sql.append("        A.HDXX_HDSJ,");// 回答时间
			sql.append("        A.HDXX_HDJF,");// 回答积分
			sql.append("        B.YHXX_YHMC AS HDR");// 回答人
			sql.append("   FROM HDXX A,YHXX B");
			sql.append("  WHERE   A.HDXX_SCBZ = '0' AND A.HDXX_HDR = B.YHXX_UUID");
			sql.append("  AND A.HDXX_WTID = '").append(wtid).append("'");	
			sql.append("  AND A.HDXX_HDR = '").append(uuid).append("'");	
		
			sql.append(" ORDER BY ");
			sql.append("        A.HDXX_HDSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010161>> rs = new BeanListHandler<Pojo2010161>(
					Pojo2010161.class);
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
