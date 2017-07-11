package com.xsjy.service.service2010000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010120;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010121;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010122;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service2010120 extends BaseService {

	private DBManager db = null;

	public Service2010120() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-01-08
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
	 * @FunctionName: getGRJF
	 * @Description: 获得个人积分
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo2010122
	 * @author czl
	 * @date 2015-01-15
	 */
	public Pojo2010122 getGRJF(String uuid) throws Exception {
		Pojo2010122 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.JJXX_JJZF ");//总积分
			sql.append("     FROM ");
			sql.append("     JJXX A ");
			sql.append("     WHERE A.JJXX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(uuid)) {
				sql.append(" AND A.JJXX_RYXM ='").append(uuid).append("'");	
						
			}
			ResultSetHandler<Pojo2010122> rsh = new BeanHandler<Pojo2010122>(
					Pojo2010122.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getHDJF
	 * @Description: 获得回答积分
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo2010121
	 * @author czl
	 * @date 2015-01-15
	 */
	public Pojo2010121 getHDJF(String wtid) throws Exception {
		Pojo2010121 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     SUM(HDXX_HDJF) AS HDZF ");//回答总分
			sql.append("     FROM ");
			sql.append("     HDXX A ");
			sql.append("     WHERE A.HDXX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(wtid)) {
				sql.append(" AND A.HDXX_WTID ='").append(wtid).append("'");	
						
			}
			ResultSetHandler<Pojo2010121> rsh = new BeanHandler<Pojo2010121>(
					Pojo2010121.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getSYJF
	 * @Description: 获得剩余积分
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo2010121
	 * @author czl
	 * @date 2015-01-16
	 */
	public Pojo2010121 getSYJF(String wtid,String hdid) throws Exception {
		Pojo2010121 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     SUM(HDXX_HDJF) AS HDZF ");//回答总分
			sql.append("     FROM ");
			sql.append("     HDXX A ");
			sql.append("     WHERE A.HDXX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(wtid)) {
				sql.append(" AND A.HDXX_WTID ='").append(wtid).append("'");	
						
			}
			if (MyStringUtils.isNotBlank(hdid)) {
				sql.append(" AND A.HDXX_HDID !='").append(hdid).append("'");	
						
			}
			ResultSetHandler<Pojo2010121> rsh = new BeanHandler<Pojo2010121>(
					Pojo2010121.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getQuestionList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-01-08
	 */
	public int getQuestionList_TotalCount(Pojo2010120 beanIn,String uuid) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(WTXX_WTID)");
			sql.append("   FROM WTXX A ");
			sql.append("  WHERE  A.WTXX_SCBZ = '0' ");
			sql.append("  AND A.WTXX_TWR = '").append(uuid).append("'");
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
	 * @return List<Pojo2010120>
	 * @author czl
	 * @date 2014-01-08
	 */
	public List<Pojo2010120> getQuestionList_PageData(Pojo2010120 beanIn, int page,
			int limit, String sort, String uuid) throws Exception {
		List<Pojo2010120> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.WTXX_WTID,");//问题ID
			sql.append("        A.WTXX_KCID,");//课程ID
			sql.append("        A.WTXX_YXSJ,");//有效时间
			sql.append("        B.KCXX_KCMC AS KCMC,");// 课程名称
			sql.append("        A.WTXX_TWNR,");// 提问内容
			sql.append("        A.WTXX_TWSJ,");// 提问时间
			sql.append("        C.YXSJ_YXSJ AS YXSJ,");// 有效时间
			sql.append("        A.WTXX_TWJF,");// 提问积分
			sql.append("        (SELECT COUNT(E.HDXX_HDID)  FROM HDXX E    WHERE A.WTXX_WTID  = E.HDXX_WTID(+)) AS HDSL,");//回答数量
			sql.append("        CASE WHEN (SELECT COUNT(E.HDXX_HDID)  FROM HDXX E    WHERE A.WTXX_WTID  = E.HDXX_WTID(+)) = 0 AND A.WTXX_TWZT = '0' THEN '未回答' WHEN (SELECT COUNT(E.HDXX_HDID)  FROM HDXX E    WHERE A.WTXX_WTID  = E.HDXX_WTID(+)) > 0  AND A.WTXX_TWZT = '0' THEN '已回答' WHEN (SELECT COUNT(E.HDXX_HDID)  FROM HDXX E    WHERE A.WTXX_WTID  = E.HDXX_WTID(+)) >= 0  AND A.WTXX_TWZT = '1' THEN '已结束' END AS WTXX_TWZT");
			sql.append("   FROM WTXX A,KCXX B,YXSJ C");
			sql.append("  WHERE   A.WTXX_SCBZ = '0'");
			sql.append("  AND A.WTXX_KCID = B.KCXX_KCID(+)");
			sql.append("  AND A.WTXX_YXSJ = C.YXSJ_YXID(+)");
			sql.append("  AND A.WTXX_TWR = '").append(uuid).append("'");	
			if (MyStringUtils.isNotBlank(beanIn.getWTXX_KCID())&&!("000").equals(beanIn.getWTXX_KCID())) {	
				sql.append("  AND A.WTXX_KCID = '").append(beanIn.getWTXX_KCID())
					.append("'");	
			}	
			sql.append(" ORDER BY ");
			sql.append("        A.WTXX_TWZT,A.WTXX_TWSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010120>> rs = new BeanListHandler<Pojo2010120>(
					Pojo2010120.class);
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
	 * @FunctionName: insertQuestion
	 * @Description: 新增问题
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-01-08
	 */
	public int insertQuestion(Pojo2010120 beanIn,String uuid) throws Exception {
		int result = 0;
		int resulttw = 0;
		int resultjf = 0;
		int resultmx = 0;
		String sysdate = getSystemdate();
		String date = getDate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();		
			String wtid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     WTXX ");
			strbuf.append(" ( ");
			strbuf.append("     WTXX_WTID, ");// 问题ID
			strbuf.append("     WTXX_KCID, ");// 课程ID
			strbuf.append("     WTXX_TWR, ");// 提问人
			strbuf.append("     WTXX_TWNR, ");// 提问内容
			strbuf.append("     WTXX_TWSJ, ");// 提问时间
			strbuf.append("     WTXX_TWJF, ");// 提问积分
			strbuf.append("     WTXX_YXSJ, ");// 有效时间
			strbuf.append("     WTXX_CJR, ");// 创建人
			strbuf.append("     WTXX_CJSJ, ");// 创建时间
			strbuf.append("     WTXX_GXR, ");// 更新人
			strbuf.append("     WTXX_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + wtid + "', ");
			strbuf.append("     '" + beanIn.getWTXX_KCID() + "', ");
			strbuf.append("     '" + uuid + "', ");
			strbuf.append("     '" + beanIn.getWTXX_TWNR() + "', ");
			strbuf.append("     '" + date + "', ");
			strbuf.append("     '" + beanIn.getWTXX_TWJF() + "', ");
			strbuf.append("     '" + beanIn.getWTXX_YXSJ() + "', ");
			strbuf.append("     '" + beanIn.getWTXX_CJR() + "', ");
			strbuf.append("     '" + sysdate + "', ");
			strbuf.append("     '" + beanIn.getWTXX_GXR() + "', ");
			strbuf.append("     '" + sysdate + "' ");
			strbuf.append(" ) ");
			resulttw = db.executeSQL(strbuf);
			//更新积分信息表
			StringBuffer strbufJF = new StringBuffer();
			strbufJF.append(" UPDATE ");
			strbufJF.append("     JJXX ");
			strbufJF.append(" SET ");
			strbufJF.append("     JJXX_JJZF=(JJXX_JJZF - ").append(beanIn.getWTXX_TWJF()).append("),");// 积分总分
			strbufJF.append("     JJXX_GXR='").append(beanIn.getWTXX_GXR()).append("',");// 更新人
			strbufJF.append("     JJXX_GXSJ='" + sysdate + "'");// 更新时间
			strbufJF.append("  WHERE   JJXX_RYXM='"+uuid+"'");//人员ID
			resultjf = db.executeSQL(strbufJF);
			//插入积分明细表
			String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" INSERT INTO ");
			strbufMX.append("     JJMX ");
			strbufMX.append(" ( ");
			strbufMX.append("     JJMX_JJMXID, ");// 积分明细ID
			strbufMX.append("     JJMX_JJID, ");// 积分ID
			strbufMX.append("     JJMX_JJLX, ");// 积分类型（1-签到积分 2-回帖积分 3-代课积分 4-提出问题）
			strbufMX.append("     JJMX_JJFS, ");// 积分数
			strbufMX.append("     JJMX_JJSJ, ");// 获得时间
			strbufMX.append("     JJMX_CJR, ");// 创建人
			strbufMX.append("     JJMX_CJSJ, ");// 创建时间
			strbufMX.append("     JJMX_GXR, ");// 更新人
			strbufMX.append("     JJMX_GXSJ ");// 修改时间
			strbufMX.append(" ) ");
			strbufMX.append(" VALUES ");
			strbufMX.append(" ( ");
			strbufMX.append("     '" + mxid + "', ");
			strbufMX.append("     (SELECT JJXX_JJID FROM JJXX WHERE JJXX_RYXM = '"+uuid+"'), ");
			strbufMX.append("     '4', ");
			strbufMX.append("     '-" + beanIn.getWTXX_TWJF() + "', ");
			strbufMX.append("     '" + date + "', ");
			strbufMX.append("     '" + beanIn.getWTXX_CJR() + "', ");
			strbufMX.append("     '" + sysdate + "', ");
			strbufMX.append("     '" + beanIn.getWTXX_GXR() + "', ");
			strbufMX.append("     '" + sysdate + "' ");
			strbufMX.append(" ) ");
			resultmx = db.executeSQL(strbufMX);
			if(resulttw>0&&resultjf>0&&resultmx>0){
				db.commit();
				result = 1;
			}else{
				db.rollback();
				result = 0;
			}
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
	 * @FunctionName: updateQuestion
	 * @Description: 修改问题
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-01-08
	 */
	public int updateQuestion(Pojo2010120 beanIn,String uuid) throws Exception {
		int result = 0;
		int resulttw = 0;
		int resultjf = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     WTXX ");
			strbuf.append(" SET ");
			strbuf.append("     WTXX_KCID='").append(beanIn.getWTXX_KCID()).append("',");// 课程ID
			strbuf.append("     WTXX_TWNR='").append(beanIn.getWTXX_TWNR()).append("',");// 提问内容
			strbuf.append("     WTXX_TWJF='").append(beanIn.getWTXX_TWJF()).append("',");// 提问积分
			strbuf.append("     WTXX_YXSJ='").append(beanIn.getWTXX_YXSJ()).append("',");// 有效时间
			strbuf.append("     WTXX_GXR='").append(beanIn.getWTXX_GXR()).append("',");// 更新人
			strbuf.append("     WTXX_GXSJ='" + sysdate + "'");// 更新时间
			strbuf.append(" WHERE 1 = 1 AND WTXX_SCBZ = '0'");
			strbuf.append("  AND   WTXX_WTID='").append(beanIn.getWTXX_WTID()).append("'");//问题ID
			resulttw = db.executeSQL(strbuf);
			//更新积分信息表
			StringBuffer strbufJF = new StringBuffer();
			strbufJF.append(" UPDATE ");
			strbufJF.append("     JJXX ");
			strbufJF.append(" SET ");
			strbufJF.append("     JJXX_JJZF=(JJXX_JJZF + (").append(Integer.parseInt(beanIn.getTWJF())-Integer.parseInt(beanIn.getWTXX_TWJF())).append(")),");// 积分总分
			strbufJF.append("     JJXX_GXR='").append(beanIn.getWTXX_GXR()).append("',");// 更新人
			strbufJF.append("     JJXX_GXSJ='" + sysdate + "'");// 更新时间
			strbufJF.append("  WHERE   JJXX_RYXM='"+uuid+"'");//人员ID
			resultjf = db.executeSQL(strbufJF);
			if(resulttw>0&&resultjf>0){
				db.commit();
				result = 1;
			}else{
				db.rollback();
				result = 0;
			}
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
	 * @FunctionName: deleteQuestion
	 * @Description: 删除问题
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-01-08
	 */
	public int deleteQuestion(Pojo2010120 beanIn,String uuid) throws Exception {
		int result = 0;
		int resulttw = 0;
		int resultjf = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     WTXX ");
			strbuf.append(" WHERE 1 = 1 AND WTXX_WTID='").append(beanIn.getWTXX_WTID())
					.append("'");
			resulttw = db.executeSQL(strbuf);
			//更新积分信息表
			StringBuffer strbufJF = new StringBuffer();
			strbufJF.append(" UPDATE ");
			strbufJF.append("     JJXX ");
			strbufJF.append(" SET ");
			strbufJF.append("     JJXX_JJZF=(JJXX_JJZF + ").append(beanIn.getWTXX_TWJF()).append("),");// 积分总分
			strbufJF.append("     JJXX_GXR='").append(beanIn.getWTXX_GXR()).append("',");// 更新人
			strbufJF.append("     JJXX_GXSJ='" + sysdate + "'");// 更新时间
			strbufJF.append("  WHERE   JJXX_RYXM='"+uuid+"'");//人员ID
			resultjf = db.executeSQL(strbufJF);
			if(resulttw>0&&resultjf>0){
				db.commit();
				result = 1;
			}else{
				db.rollback();
				result = 0;
			}
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
	 * @FunctionName: setWTXXend
	 * @Description: 结束问题
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-15
	 */
	public int setWTXXend(Pojo2010120 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     WTXX ");
			strbuf.append(" SET ");
			strbuf.append("     WTXX_TWZT='1',");// 提问状态（0-正常 1-问题结束）
			strbuf.append("     WTXX_GXR='").append(beanIn.getWTXX_GXR()).append("',");// 修改人
			strbuf.append("     WTXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND WTXX_SCBZ = '0'");
			strbuf.append("  AND   WTXX_WTID='").append(beanIn.getWTXX_WTID()).append("'");// 问题ID
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
	 * @date 2014-01-09
	 */
	public int getAnswerList_TotalCount(String wtid) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(HDXX_HDID)");
			sql.append("   FROM HDXX A ");
			sql.append("  WHERE  A.HDXX_SCBZ = '0' ");
			sql.append("  AND A.HDXX_WTID = '").append(wtid).append("'");
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
	 * @return List<Pojo2010121>
	 * @author czl
	 * @date 2014-01-09
	 */
	public List<Pojo2010121> getAnswerList_PageData(String wtid, int page,
			int limit, String sort) throws Exception {
		List<Pojo2010121> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.HDXX_HDID,");//回答ID
			sql.append("        B.YHXX_YHMC AS HDR,");//回答人
			sql.append("        A.HDXX_HDR,");//回答人ID
			sql.append("        A.HDXX_HDNR,");//回答内容
			sql.append("        A.HDXX_HDSJ,");// 回答时间
			sql.append("        A.HDXX_HDJF");// 回答得分
			sql.append("   FROM HDXX A,YHXX B");
			sql.append("  WHERE   A.HDXX_SCBZ = '0'");
			sql.append("  AND A.HDXX_HDR = B.YHXX_UUID(+)");
			sql.append("  AND A.HDXX_WTID = '").append(wtid).append("'");	
		
			sql.append(" ORDER BY ");
			sql.append("        A.HDXX_HDSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010121>> rs = new BeanListHandler<Pojo2010121>(
					Pojo2010121.class);
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
	 * @FunctionName: setHDXXdefen
	 * @Description: 打分
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-15
	 */
	public int setHDXXdefen(Pojo2010121 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     HDXX ");
			strbuf.append(" SET ");
			strbuf.append("     HDXX_HDJF='"+beanIn.getHDDF()+"',");// 回答得分
			strbuf.append("     HDXX_GXR='").append(beanIn.getHDXX_GXR()).append("',");// 修改人
			strbuf.append("     HDXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND HDXX_SCBZ = '0'");
			strbuf.append("  AND   HDXX_HDID='").append(beanIn.getHDXX_HDID()).append("'");// 回答ID
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
	 * @FunctionName: updateOwnjf
	 * @Description: 修改个人积分
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-16
	 */
	public int updateOwnjf(String grjf,String yhid,String uuid) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		String date = getDate();
		try {
			db.openConnection();	
			//更新积分信息表
			StringBuffer strbufJF = new StringBuffer();
			strbufJF.append(" UPDATE ");
			strbufJF.append("     JJXX ");
			strbufJF.append(" SET ");
			strbufJF.append("     JJXX_JJZF=(JJXX_JJZF + ").append(grjf).append("),");// 积分总分
			strbufJF.append("     JJXX_GXR='").append(yhid).append("',");// 更新人
			strbufJF.append("     JJXX_GXSJ='" + sysdate + "'");// 更新时间
			strbufJF.append("  WHERE   JJXX_RYXM='").append(uuid).append("'");//人员ID
			result = db.executeSQL(strbufJF);
			if(!grjf.equals("0")){
				//插入积分明细表
				String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
				StringBuffer strbufMX = new StringBuffer();
				strbufMX.append(" INSERT INTO ");
				strbufMX.append("     JJMX ");
				strbufMX.append(" ( ");
				strbufMX.append("     JJMX_JJMXID, ");// 积分明细ID
				strbufMX.append("     JJMX_JJID, ");// 积分ID
				strbufMX.append("     JJMX_JJLX, ");// 积分类型（1-签到积分 2-回帖积分 3-代课积分 4-提出问题 5-提问返还）
				strbufMX.append("     JJMX_JJFS, ");// 积分数
				strbufMX.append("     JJMX_JJSJ, ");// 获得时间
				strbufMX.append("     JJMX_CJR, ");// 创建人
				strbufMX.append("     JJMX_CJSJ, ");// 创建时间
				strbufMX.append("     JJMX_GXR, ");// 更新人
				strbufMX.append("     JJMX_GXSJ ");// 修改时间
				strbufMX.append(" ) ");
				strbufMX.append(" VALUES ");
				strbufMX.append(" ( ");
				strbufMX.append("     '" + mxid + "', ");
				strbufMX.append("     (SELECT JJXX_JJID FROM JJXX WHERE JJXX_RYXM = "+uuid+"), ");
				strbufMX.append("     '5', ");
				strbufMX.append("     '" + grjf + "', ");
				strbufMX.append("     '" + date + "', ");
				strbufMX.append("     '" + yhid + "', ");
				strbufMX.append("     '" + sysdate + "', ");
				strbufMX.append("     '" + yhid + "', ");
				strbufMX.append("     '" + sysdate + "' ");
				strbufMX.append(" ) ");
				db.executeSQL(strbufMX);
			}
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
	 * @FunctionName: updateHdjf
	 * @Description: 设置回答得分及明细-更新
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-16
	 */
	public int updateHdjf(String hdr,String hdjf,String jjid,String yhid) throws Exception {
		int result = 0;
		int resultjf = 0;
		int resultmx = 0;
		String sysdate = getSystemdate();
		String date = getDate();
		try {
			db.openConnection();
			db.beginTran();
			//更新积分信息表
			StringBuffer strbufJF = new StringBuffer();
			strbufJF.append(" UPDATE ");
			strbufJF.append("     JJXX ");
			strbufJF.append(" SET ");
			strbufJF.append("     JJXX_JJZF=(JJXX_JJZF + ").append(hdjf).append("),");// 积分总分
			strbufJF.append("     JJXX_GXR='").append(yhid).append("',");// 更新人
			strbufJF.append("     JJXX_GXSJ='" + sysdate + "'");// 更新时间
			strbufJF.append("  WHERE   JJXX_RYXM='").append(hdr).append("'");//人员ID
			resultjf = db.executeSQL(strbufJF);
			//插入积分明细表
			String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" INSERT INTO ");
			strbufMX.append("     JJMX ");
			strbufMX.append(" ( ");
			strbufMX.append("     JJMX_JJMXID, ");// 积分明细ID
			strbufMX.append("     JJMX_JJID, ");// 积分ID
			strbufMX.append("     JJMX_JJLX, ");// 积分类型（1-登陆积分 2-回帖积分 3-代课积分）
			strbufMX.append("     JJMX_JJFS, ");// 积分数
			strbufMX.append("     JJMX_JJSJ, ");// 获得时间
			strbufMX.append("     JJMX_CJR, ");// 创建人
			strbufMX.append("     JJMX_CJSJ, ");// 创建时间
			strbufMX.append("     JJMX_GXR, ");// 更新人
			strbufMX.append("     JJMX_GXSJ ");// 修改时间
			strbufMX.append(" ) ");
			strbufMX.append(" VALUES ");
			strbufMX.append(" ( ");
			strbufMX.append("     '" + mxid + "', ");
			strbufMX.append("     '" + jjid + "', ");
			strbufMX.append("     '2', ");
			strbufMX.append("     '" + hdjf + "', ");
			strbufMX.append("     '" + date + "', ");
			strbufMX.append("     '" + yhid + "', ");
			strbufMX.append("     '" + sysdate + "', ");
			strbufMX.append("     '" + yhid + "', ");
			strbufMX.append("     '" + sysdate + "' ");
			strbufMX.append(" ) ");
			resultmx = db.executeSQL(strbufMX);
			if(resultjf>0&&resultmx>0){
				db.commit();
				result = 1;
			}else{
				db.rollback();
				result = 0;
			}
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
	 * @FunctionName: insertHdjf
	 * @Description: 设置回答得分及明细-插入
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-16
	 */
	public int insertHdjf(String hdr,String hdjf,String yhid) throws Exception {
		int result = 0;
		int resultjf = 0;
		int resultmx = 0;
		String sysdate = getSystemdate();
		String date = getDate();
		try {
			db.openConnection();
			db.beginTran();
			//插入积分信息表
			String jjid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbufJF = new StringBuffer();
			strbufJF.append(" INSERT INTO ");
			strbufJF.append("     JJXX ");
			strbufJF.append(" ( ");
			strbufJF.append("     JJXX_JJID, ");// 积分ID
			strbufJF.append("     JJXX_RYXM, ");// 人员ID
			strbufJF.append("     JJXX_JJZF, ");// 积分数
			strbufJF.append("     JJXX_RYQF, ");// 人员区分
			strbufJF.append("     JJXX_CJR, ");// 创建人
			strbufJF.append("     JJXX_CJSJ, ");// 创建时间
			strbufJF.append("     JJXX_GXR, ");// 更新人
			strbufJF.append("     JJXX_GXSJ ");// 修改时间
			strbufJF.append(" ) ");
			strbufJF.append(" VALUES ");
			strbufJF.append(" ( ");
			strbufJF.append("     '" + jjid + "', ");
			strbufJF.append("     '" + hdr + "', ");
			strbufJF.append("     '" + hdjf + "', ");
			strbufJF.append("     '0', ");
			strbufJF.append("     '" + yhid + "', ");
			strbufJF.append("     '" + sysdate + "', ");
			strbufJF.append("     '" + yhid + "', ");
			strbufJF.append("     '" + sysdate + "' ");
			strbufJF.append(" ) ");
			resultjf = db.executeSQL(strbufJF);
			//插入积分明细表
			String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" INSERT INTO ");
			strbufMX.append("     JJMX ");
			strbufMX.append(" ( ");
			strbufMX.append("     JJMX_JJMXID, ");// 积分明细ID
			strbufMX.append("     JJMX_JJID, ");// 积分ID
			strbufMX.append("     JJMX_JJLX, ");// 积分类型（1-登陆积分 2-回帖积分 3-代课积分）
			strbufMX.append("     JJMX_JJFS, ");// 积分数
			strbufMX.append("     JJMX_JJSJ, ");// 获得时间
			strbufMX.append("     JJMX_CJR, ");// 创建人
			strbufMX.append("     JJMX_CJSJ, ");// 创建时间
			strbufMX.append("     JJMX_GXR, ");// 更新人
			strbufMX.append("     JJMX_GXSJ ");// 修改时间
			strbufMX.append(" ) ");
			strbufMX.append(" VALUES ");
			strbufMX.append(" ( ");
			strbufMX.append("     '" + mxid + "', ");
			strbufMX.append("     '" + jjid + "', ");
			strbufMX.append("     '2', ");
			strbufMX.append("     '" + hdjf + "', ");
			strbufMX.append("     '" + date + "', ");
			strbufMX.append("     '" + yhid + "', ");
			strbufMX.append("     '" + sysdate + "', ");
			strbufMX.append("     '" + yhid + "', ");
			strbufMX.append("     '" + sysdate + "' ");
			strbufMX.append(" ) ");
			resultmx = db.executeSQL(strbufMX);
			if(resultjf>0&&resultmx>0){
				db.commit();
				result = 1;
			}else{
				db.rollback();
				result = 0;
			}
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
